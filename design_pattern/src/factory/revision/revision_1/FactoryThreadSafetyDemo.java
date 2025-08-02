package factory.revision.revision_1;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class FactoryThreadSafetyDemo {

    // ✅ THREAD-SAFE: Stateless Static Factory (like your original Factory_1.java)
    static class StatelessFactory {
        // No instance variables = No shared state = Thread Safe
        public static SimpleProduct createProduct(String type) {
            return switch (type.toLowerCase()) {
                case "basic" -> new SimpleProduct("Basic Product");
                case "premium" -> new SimpleProduct("Premium Product");
                default -> new SimpleProduct("Default Product");
            };
        }
    }

    // ❌ NOT THREAD-SAFE: Stateful Instance Factory
    static class StatefulFactory {
        private int productCount = 0;  // ⚠️ Shared mutable state
        private String lastProductType;  // ⚠️ Shared mutable state

        public SimpleProduct createProduct(String type) {
            productCount++;  // ⚠️ Race condition here
            lastProductType = type;  // ⚠️ Race condition here
            return new SimpleProduct(type + " #" + productCount);
        }

        public int getProductCount() { return productCount; }
        public String getLastProductType() { return lastProductType; }
    }

    // 🔒 THREAD-SAFE: Synchronized Stateful Factory
    static class SynchronizedFactory {
        private int productCount = 0;
        private String lastProductType;

        public synchronized SimpleProduct createProduct(String type) {
            productCount++;
            lastProductType = type;
            return new SimpleProduct(type + " #" + productCount);
        }

        public synchronized int getProductCount() { return productCount; }
        public synchronized String getLastProductType() { return lastProductType; }
    }

    // ✅ THREAD-SAFE: Atomic Operations Factory
    static class AtomicFactory {
        private final AtomicInteger productCount = new AtomicInteger(0);
        private volatile String lastProductType;

        public SimpleProduct createProduct(String type) {
            int count = productCount.incrementAndGet();
            lastProductType = type;  // Single write to volatile field
            return new SimpleProduct(type + " #" + count);
        }

        public int getProductCount() { return productCount.get(); }
        public String getLastProductType() { return lastProductType; }
    }

    // ❌ NOT THREAD-SAFE: Registry-based Factory (like ExtensiblePlayerFactory from your fix)
    static class UnsafeRegistryFactory {
        private static final Map<String, ProductCreator> creators = new HashMap<>();
        
        interface ProductCreator {
            SimpleProduct create(String name);
        }

        static {
            creators.put("BASIC", name -> new SimpleProduct("Basic: " + name));
            creators.put("PREMIUM", name -> new SimpleProduct("Premium: " + name));
        }

        // ⚠️ Not thread-safe - HashMap is not thread-safe
        public static void registerCreator(String type, ProductCreator creator) {
            creators.put(type, creator);  // Race condition on concurrent modification
        }

        public static SimpleProduct createProduct(String type, String name) {
            ProductCreator creator = creators.get(type);  // May see inconsistent state
            return creator != null ? creator.create(name) : new SimpleProduct("Unknown: " + name);
        }
    }

    // ✅ THREAD-SAFE: ConcurrentHashMap Registry Factory
    static class SafeRegistryFactory {
        private static final ConcurrentHashMap<String, ProductCreator> creators = new ConcurrentHashMap<>();
        
        interface ProductCreator {
            SimpleProduct create(String name);
        }

        static {
            creators.put("BASIC", name -> new SimpleProduct("Basic: " + name));
            creators.put("PREMIUM", name -> new SimpleProduct("Premium: " + name));
        }

        // ✅ Thread-safe with ConcurrentHashMap
        public static void registerCreator(String type, ProductCreator creator) {
            creators.put(type, creator);
        }

        public static SimpleProduct createProduct(String type, String name) {
            ProductCreator creator = creators.get(type);
            return creator != null ? creator.create(name) : new SimpleProduct("Unknown: " + name);
        }

        public static java.util.Set<String> getSupportedTypes() {
            return creators.keySet();  // ConcurrentHashMap's keySet is thread-safe
        }
    }

    // 🔒 THREAD-SAFE: Double-Checked Locking Singleton Factory
    static class SingletonFactory {
        private static volatile SingletonFactory instance;
        private final Map<String, SimpleProduct> productCache = new ConcurrentHashMap<>();

        private SingletonFactory() {}

        public static SingletonFactory getInstance() {
            if (instance == null) {
                synchronized (SingletonFactory.class) {
                    if (instance == null) {
                        instance = new SingletonFactory();
                    }
                }
            }
            return instance;
        }

        public SimpleProduct createOrGetProduct(String type) {
            return productCache.computeIfAbsent(type, 
                t -> new SimpleProduct("Cached: " + t + " #" + System.currentTimeMillis()));
        }
    }

    // Simple product class
    static class SimpleProduct {
        private final String name;
        private final long creationTime;

        public SimpleProduct(String name) {
            this.name = name;
            this.creationTime = System.currentTimeMillis();
        }

        @Override
        public String toString() {
            return "SimpleProduct{name='" + name + "', created=" + creationTime + "}";
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Factory Pattern Thread Safety Demo ===\n");

        // Demo 1: Thread-safe stateless factory
        demonstrateStatelessFactory();

        // Demo 2: Thread-unsafe stateful factory
        demonstrateStatefulFactory();

        // Demo 3: Thread-safe synchronized factory
        demonstrateSynchronizedFactory();

        // Demo 4: Thread-safe atomic factory
        demonstrateAtomicFactory();

        // Demo 5: Thread-unsafe registry factory
        demonstrateUnsafeRegistry();

        // Demo 6: Thread-safe registry factory
        demonstrateSafeRegistry();
    }

    static void demonstrateStatelessFactory() throws InterruptedException {
        System.out.println("1. ✅ STATELESS FACTORY (Thread-Safe)");
        
        List<SimpleProduct> products = Collections.synchronizedList(new ArrayList<>());
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(100);

        for (int i = 0; i < 100; i++) {
            final int id = i;
            executor.submit(() -> {
                try {
                    SimpleProduct product = StatelessFactory.createProduct(id % 2 == 0 ? "basic" : "premium");
                    products.add(product);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();
        System.out.println("Created " + products.size() + " products safely");
        System.out.println("✅ Success: No race conditions in stateless factory\n");
    }

    static void demonstrateStatefulFactory() throws InterruptedException {
        System.out.println("2. ❌ STATEFUL FACTORY (Not Thread-Safe)");
        
        StatefulFactory factory = new StatefulFactory();
        List<SimpleProduct> products = Collections.synchronizedList(new ArrayList<>());
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(100);

        for (int i = 0; i < 100; i++) {
            executor.submit(() -> {
                try {
                    SimpleProduct product = factory.createProduct("TestProduct");
                    products.add(product);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();
        
        System.out.println("Created " + products.size() + " products");
        System.out.println("Factory count: " + factory.getProductCount());
        System.out.println("❌ Problem: Count doesn't match due to race conditions!\n");
    }

    static void demonstrateSynchronizedFactory() throws InterruptedException {
        System.out.println("3. 🔒 SYNCHRONIZED FACTORY (Thread-Safe)");
        
        SynchronizedFactory factory = new SynchronizedFactory();
        List<SimpleProduct> products = Collections.synchronizedList(new ArrayList<>());
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(100);

        for (int i = 0; i < 100; i++) {
            executor.submit(() -> {
                try {
                    SimpleProduct product = factory.createProduct("SyncProduct");
                    products.add(product);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();
        
        System.out.println("Created " + products.size() + " products");
        System.out.println("Factory count: " + factory.getProductCount());
        System.out.println("✅ Success: Synchronized access prevents race conditions\n");
    }

    static void demonstrateAtomicFactory() throws InterruptedException {
        System.out.println("4. ⚡ ATOMIC FACTORY (Thread-Safe & Fast)");
        
        AtomicFactory factory = new AtomicFactory();
        List<SimpleProduct> products = Collections.synchronizedList(new ArrayList<>());
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(100);

        for (int i = 0; i < 100; i++) {
            executor.submit(() -> {
                try {
                    SimpleProduct product = factory.createProduct("AtomicProduct");
                    products.add(product);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();
        
        System.out.println("Created " + products.size() + " products");
        System.out.println("Factory count: " + factory.getProductCount());
        System.out.println("✅ Success: Atomic operations ensure thread safety\n");
    }

    static void demonstrateUnsafeRegistry() throws InterruptedException {
        System.out.println("5. ❌ UNSAFE REGISTRY FACTORY");
        
        ExecutorService executor = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(50);

        // Concurrent registration and creation - DANGEROUS!
        for (int i = 0; i < 50; i++) {
            final int id = i;
            executor.submit(() -> {
                try {
                    if (id % 10 == 0) {
                        UnsafeRegistryFactory.registerCreator("TYPE_" + id, 
                            name -> new SimpleProduct("Registered: " + name));
                    }
                    UnsafeRegistryFactory.createProduct("BASIC", "Product" + id);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();
        System.out.println("❌ Unsafe registry completed - may have race conditions\n");
    }

    static void demonstrateSafeRegistry() throws InterruptedException {
        System.out.println("6. ✅ SAFE REGISTRY FACTORY");
        
        ExecutorService executor = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(50);

        for (int i = 0; i < 50; i++) {
            final int id = i;
            executor.submit(() -> {
                try {
                    if (id % 10 == 0) {
                        SafeRegistryFactory.registerCreator("TYPE_" + id, 
                            name -> new SimpleProduct("Safe Registered: " + name));
                    }
                    SafeRegistryFactory.createProduct("BASIC", "Product" + id);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();
        System.out.println("✅ Safe registry completed successfully");
        System.out.println("Supported types: " + SafeRegistryFactory.getSupportedTypes().size());
    }
}
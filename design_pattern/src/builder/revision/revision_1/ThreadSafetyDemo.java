package builder.revision.revision_1;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class ThreadSafetyDemo {
    
    // ❌ NON-THREAD-SAFE: Original Builder Pattern (similar to your Builder_1.java)
    static class UnsafeProduct {
        private String name;
        private long id;
        
        public void setName(String name) { this.name = name; }
        public void setId(long id) { this.id = id; }
        
        @Override
        public String toString() {
            return "UnsafeProduct{name='" + name + "', id=" + id + "}";
        }
    }
    
    static class UnsafeBuilder {
        private UnsafeProduct product;
        
        public UnsafeBuilder() {
            this.product = new UnsafeProduct();
        }
        
        public UnsafeBuilder setName(String name) {
            this.product.setName(name);
            return this;
        }
        
        public UnsafeBuilder setId(long id) {
            this.product.setId(id);
            return this;
        }
        
        public UnsafeProduct build() {
            return this.product;  // ❌ Same instance returned
        }
    }
    
    // ✅ THREAD-SAFE: Improved Builder Pattern (like your Builder_2_fix.java)
    static class SafeProduct {
        private final String name;
        private final long id;
        
        public SafeProduct(String name, long id) {
            this.name = name;
            this.id = id;
        }
        
        public String getName() { return name; }
        public long getId() { return id; }
        
        @Override
        public String toString() {
            return "SafeProduct{name='" + name + "', id=" + id + "}";
        }
    }
    
    static class SafeBuilder {
        private String name;
        private long id;
        
        public SafeBuilder setName(String name) {
            this.name = name;
            return this;
        }
        
        public SafeBuilder setId(long id) {
            this.id = id;
            return this;
        }
        
        public SafeProduct build() {
            return new SafeProduct(name, id);  // ✅ New instance each time
        }
    }
    
    // 🔒 SYNCHRONIZED: Thread-safe shared builder
    static class SynchronizedBuilder {
        private String name;
        private long id;
        
        public synchronized SynchronizedBuilder setName(String name) {
            this.name = name;
            return this;
        }
        
        public synchronized SynchronizedBuilder setId(long id) {
            this.id = id;
            return this;
        }
        
        public synchronized SafeProduct build() {
            return new SafeProduct(name, id);
        }
        
        public synchronized SynchronizedBuilder reset() {
            this.name = null;
            this.id = 0;
            return this;
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Builder Pattern Thread Safety Demo ===\n");
        
        // Demo 1: Non-thread-safe shared builder
        demonstrateUnsafeSharedBuilder();
        
        // Demo 2: Thread-safe with individual builders
        demonstrateSafeIndividualBuilders();
        
        // Demo 3: Thread-safe with synchronized shared builder
        demonstrateSynchronizedSharedBuilder();
    }
    
    static void demonstrateUnsafeSharedBuilder() throws InterruptedException {
        System.out.println("1. ❌ UNSAFE: Shared Builder Instance");
        
        UnsafeBuilder sharedBuilder = new UnsafeBuilder();
        List<UnsafeProduct> products = Collections.synchronizedList(new ArrayList<>());
        
        ExecutorService executor = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(10);
        
        for (int i = 0; i < 10; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    // All threads sharing same builder - RACE CONDITION!
                    UnsafeProduct product = sharedBuilder
                            .setName("Product-" + threadId)
                            .setId(threadId)
                            .build();
                    products.add(product);
                } finally {
                    latch.countDown();
                }
            });
        }
        
        latch.await();
        executor.shutdown();
        
        // Results show interference between threads
        System.out.println("Results (notice the interference):");
        products.forEach(System.out::println);
        System.out.println("Problem: Multiple products have same reference!\n");
    }
    
    static void demonstrateSafeIndividualBuilders() throws InterruptedException {
        System.out.println("2. ✅ SAFE: Individual Builder Instances");
        
        List<SafeProduct> products = Collections.synchronizedList(new ArrayList<>());
        
        ExecutorService executor = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(10);
        
        for (int i = 0; i < 10; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    // Each thread gets its own builder - THREAD SAFE!
                    SafeBuilder builder = new SafeBuilder();
                    SafeProduct product = builder
                            .setName("Product-" + threadId)
                            .setId(threadId)
                            .build();
                    products.add(product);
                } finally {
                    latch.countDown();
                }
            });
        }
        
        latch.await();
        executor.shutdown();
        
        System.out.println("Results (all correct):");
        products.forEach(System.out::println);
        System.out.println("✅ Success: Each product is unique and correct!\n");
    }
    
    static void demonstrateSynchronizedSharedBuilder() throws InterruptedException {
        System.out.println("3. 🔒 SAFE: Synchronized Shared Builder");
        
        SynchronizedBuilder sharedBuilder = new SynchronizedBuilder();
        List<SafeProduct> products = Collections.synchronizedList(new ArrayList<>());
        
        ExecutorService executor = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(10);
        
        for (int i = 0; i < 10; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    // Synchronized methods ensure thread safety
                    SafeProduct product = sharedBuilder
                            .setName("Product-" + threadId)
                            .setId(threadId)
                            .build();
                    sharedBuilder.reset();  // Reset for next use
                    products.add(product);
                } finally {
                    latch.countDown();
                }
            });
        }
        
        latch.await();
        executor.shutdown();
        
        System.out.println("Results (all correct with synchronization):");
        products.forEach(System.out::println);
        System.out.println("✅ Success: Synchronized access prevents race conditions!\n");
    }
}
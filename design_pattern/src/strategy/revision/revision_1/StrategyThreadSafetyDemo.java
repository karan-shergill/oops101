package strategy.revision.revision_1;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class StrategyThreadSafetyDemo {

    // ✅ THREAD-SAFE: Stateless Strategy (like your original implementations)
    interface StatelessPaymentStrategy {
        boolean processPayment(double amount);
        String getPaymentType();
    }

    static class StatelessCreditCard implements StatelessPaymentStrategy {
        private final String cardNumber;  // ✅ Immutable field
        private final String holderName;  // ✅ Immutable field

        public StatelessCreditCard(String cardNumber, String holderName) {
            this.cardNumber = cardNumber;
            this.holderName = holderName;
        }

        @Override
        public boolean processPayment(double amount) {
            // ✅ No shared mutable state - thread safe
            System.out.printf("[%s] Processing $%.2f via %s's credit card%n", 
                Thread.currentThread().getName(), amount, holderName);
            return amount > 0 && amount <= 50000;
        }

        @Override
        public String getPaymentType() {
            return "Stateless Credit Card";
        }
    }

    // ❌ NOT THREAD-SAFE: Stateful Strategy with mutable state
    static class StatefulCreditCard implements StatelessPaymentStrategy {
        private final String cardNumber;
        private final String holderName;
        private double totalSpent = 0.0;  // ⚠️ Mutable shared state
        private int transactionCount = 0;  // ⚠️ Mutable shared state

        public StatefulCreditCard(String cardNumber, String holderName) {
            this.cardNumber = cardNumber;
            this.holderName = holderName;
        }

        @Override
        public boolean processPayment(double amount) {
            // ⚠️ Race condition: Multiple threads can corrupt these values
            totalSpent += amount;         // Not atomic
            transactionCount++;          // Not atomic
            
            System.out.printf("[%s] Processing $%.2f via %s's card (Total: $%.2f, Count: %d)%n", 
                Thread.currentThread().getName(), amount, holderName, totalSpent, transactionCount);
            
            return amount > 0 && amount <= 50000;
        }

        @Override
        public String getPaymentType() {
            return "Stateful Credit Card";
        }

        public double getTotalSpent() { return totalSpent; }
        public int getTransactionCount() { return transactionCount; }
    }

    // ✅ THREAD-SAFE: Stateful Strategy with atomic operations
    static class ThreadSafeCreditCard implements StatelessPaymentStrategy {
        private final String cardNumber;
        private final String holderName;
        private final AtomicLong totalSpentCents = new AtomicLong(0);  // ✅ Atomic operations
        private final AtomicInteger transactionCount = new AtomicInteger(0);  // ✅ Atomic operations

        public ThreadSafeCreditCard(String cardNumber, String holderName) {
            this.cardNumber = cardNumber;
            this.holderName = holderName;
        }

        @Override
        public boolean processPayment(double amount) {
            // ✅ Thread-safe: Atomic operations ensure data integrity
            long amountCents = Math.round(amount * 100);
            long newTotal = totalSpentCents.addAndGet(amountCents);
            int newCount = transactionCount.incrementAndGet();
            
            System.out.printf("[%s] Processing $%.2f via %s's safe card (Total: $%.2f, Count: %d)%n", 
                Thread.currentThread().getName(), amount, holderName, newTotal / 100.0, newCount);
            
            return amount > 0 && amount <= 50000;
        }

        @Override
        public String getPaymentType() {
            return "Thread-Safe Credit Card";
        }

        public double getTotalSpent() { return totalSpentCents.get() / 100.0; }
        public int getTransactionCount() { return transactionCount.get(); }
    }

    // ❌ NOT THREAD-SAFE: Context with strategy switching
    static class UnsafePaymentProcessor {
        private StatelessPaymentStrategy strategy;  // ⚠️ Mutable reference

        public UnsafePaymentProcessor(StatelessPaymentStrategy strategy) {
            this.strategy = strategy;
        }

        // ⚠️ Race condition: Strategy can change between check and use
        public boolean processPayment(double amount) {
            StatelessPaymentStrategy currentStrategy = strategy;  // Read reference
            // Another thread could change strategy here!
            return currentStrategy.processPayment(amount);  // Use potentially stale reference
        }

        // ⚠️ Race condition: Multiple threads can interfere
        public void setStrategy(StatelessPaymentStrategy strategy) {
            this.strategy = strategy;  // Not atomic from reader's perspective
        }

        public StatelessPaymentStrategy getStrategy() {
            return strategy;  // May return inconsistent state
        }
    }

    // ✅ THREAD-SAFE: Synchronized context
    static class SynchronizedPaymentProcessor {
        private StatelessPaymentStrategy strategy;

        public SynchronizedPaymentProcessor(StatelessPaymentStrategy strategy) {
            this.strategy = strategy;
        }

        // ✅ Thread-safe: Synchronized ensures atomic operations
        public synchronized boolean processPayment(double amount) {
            return strategy.processPayment(amount);
        }

        public synchronized void setStrategy(StatelessPaymentStrategy strategy) {
            this.strategy = strategy;
            System.out.printf("[%s] Strategy changed to: %s%n", 
                Thread.currentThread().getName(), strategy.getPaymentType());
        }

        public synchronized StatelessPaymentStrategy getStrategy() {
            return strategy;
        }
    }

    // ✅ THREAD-SAFE: Volatile reference with immutable strategies
    static class VolatilePaymentProcessor {
        private volatile StatelessPaymentStrategy strategy;  // ✅ Volatile ensures visibility

        public VolatilePaymentProcessor(StatelessPaymentStrategy strategy) {
            this.strategy = strategy;
        }

        // ✅ Thread-safe: Volatile read + immutable strategy
        public boolean processPayment(double amount) {
            StatelessPaymentStrategy currentStrategy = strategy;  // Volatile read
            return currentStrategy.processPayment(amount);  // Strategy is immutable
        }

        public void setStrategy(StatelessPaymentStrategy strategy) {
            this.strategy = strategy;  // Volatile write
            System.out.printf("[%s] Strategy changed to: %s%n", 
                Thread.currentThread().getName(), strategy.getPaymentType());
        }

        public StatelessPaymentStrategy getStrategy() {
            return strategy;  // Volatile read
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Strategy Pattern Thread Safety Demo ===\n");

        // Demo 1: Stateless strategies (thread-safe)
        demonstrateStatelessStrategies();

        // Demo 2: Stateful strategies (thread-unsafe)
        demonstrateStatefulStrategies();

        // Demo 3: Thread-safe stateful strategies
        demonstrateThreadSafeStrategies();

        // Demo 4: Unsafe context (strategy switching)
        demonstrateUnsafeContext();

        // Demo 5: Thread-safe context
        demonstrateSafeContext();
    }

    static void demonstrateStatelessStrategies() throws InterruptedException {
        System.out.println("1. ✅ STATELESS STRATEGIES (Thread-Safe)");
        
        StatelessCreditCard sharedStrategy = new StatelessCreditCard("4532123456789012", "John Doe");
        List<Boolean> results = Collections.synchronizedList(new ArrayList<>());
        
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(50);

        for (int i = 0; i < 50; i++) {
            final double amount = 100.0 + (i * 10);
            executor.submit(() -> {
                try {
                    // ✅ Multiple threads can safely use same stateless strategy
                    boolean result = sharedStrategy.processPayment(amount);
                    results.add(result);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();
        
        System.out.printf("Completed %d transactions safely with stateless strategy%n%n", results.size());
    }

    static void demonstrateStatefulStrategies() throws InterruptedException {
        System.out.println("2. ❌ STATEFUL STRATEGIES (Not Thread-Safe)");
        
        StatefulCreditCard sharedStrategy = new StatefulCreditCard("5555444433332222", "Jane Smith");
        List<Boolean> results = Collections.synchronizedList(new ArrayList<>());
        
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(100);

        for (int i = 0; i < 100; i++) {
            executor.submit(() -> {
                try {
                    // ⚠️ Race conditions will corrupt totalSpent and transactionCount
                    boolean result = sharedStrategy.processPayment(100.0);
                    results.add(result);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();
        
        System.out.printf("Expected: Total=$%.2f, Count=%d%n", 10000.0, 100);
        System.out.printf("Actual:   Total=$%.2f, Count=%d%n", 
            sharedStrategy.getTotalSpent(), sharedStrategy.getTransactionCount());
        System.out.println("❌ Values likely corrupted due to race conditions\n");
    }

    static void demonstrateThreadSafeStrategies() throws InterruptedException {
        System.out.println("3. ✅ THREAD-SAFE STATEFUL STRATEGIES");
        
        ThreadSafeCreditCard sharedStrategy = new ThreadSafeCreditCard("4111111111111111", "Bob Wilson");
        List<Boolean> results = Collections.synchronizedList(new ArrayList<>());
        
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(100);

        for (int i = 0; i < 100; i++) {
            executor.submit(() -> {
                try {
                    // ✅ Atomic operations ensure thread safety
                    boolean result = sharedStrategy.processPayment(100.0);
                    results.add(result);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();
        
        System.out.printf("Expected: Total=$%.2f, Count=%d%n", 10000.0, 100);
        System.out.printf("Actual:   Total=$%.2f, Count=%d%n", 
            sharedStrategy.getTotalSpent(), sharedStrategy.getTransactionCount());
        System.out.println("✅ Values are correct with atomic operations\n");
    }

    static void demonstrateUnsafeContext() throws InterruptedException {
        System.out.println("4. ❌ UNSAFE CONTEXT (Strategy Switching)");
        
        UnsafePaymentProcessor processor = new UnsafePaymentProcessor(
            new StatelessCreditCard("1234567890123456", "Alice Brown"));
        
        ExecutorService executor = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(20);

        // Mix of payment processing and strategy switching
        for (int i = 0; i < 20; i++) {
            final int taskId = i;
            executor.submit(() -> {
                try {
                    if (taskId % 5 == 0) {
                        // ⚠️ Strategy switching can interfere with concurrent payments
                        processor.setStrategy(new StatelessCreditCard("9876543210987654", "Charlie Davis"));
                    } else {
                        // ⚠️ Payments might use inconsistent strategy references
                        processor.processPayment(150.0);
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();
        System.out.println("❌ Unsafe context completed - potential inconsistencies\n");
    }

    static void demonstrateSafeContext() throws InterruptedException {
        System.out.println("5. ✅ THREAD-SAFE CONTEXT");
        
        // Test both synchronized and volatile approaches
        System.out.println("5a. Synchronized Context:");
        testSynchronizedContext();
        
        System.out.println("5b. Volatile Context:");
        testVolatileContext();
    }

    static void testSynchronizedContext() throws InterruptedException {
        SynchronizedPaymentProcessor processor = new SynchronizedPaymentProcessor(
            new StatelessCreditCard("1111222233334444", "David Lee"));
        
        ExecutorService executor = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(20);

        for (int i = 0; i < 20; i++) {
            final int taskId = i;
            executor.submit(() -> {
                try {
                    if (taskId % 7 == 0) {
                        // ✅ Synchronized strategy switching
                        processor.setStrategy(new StatelessCreditCard("5555666677778888", "Eva Martinez"));
                    } else {
                        // ✅ Synchronized payment processing
                        processor.processPayment(200.0);
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();
        System.out.println("✅ Synchronized context completed safely\n");
    }

    static void testVolatileContext() throws InterruptedException {
        VolatilePaymentProcessor processor = new VolatilePaymentProcessor(
            new StatelessCreditCard("9999888877776666", "Frank Garcia"));
        
        ExecutorService executor = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(20);

        for (int i = 0; i < 20; i++) {
            final int taskId = i;
            executor.submit(() -> {
                try {
                    if (taskId % 6 == 0) {
                        // ✅ Volatile ensures visibility of strategy changes
                        processor.setStrategy(new StatelessCreditCard("3333444455556666", "Grace Kim"));
                    } else {
                        // ✅ Volatile read ensures we see latest strategy
                        processor.processPayment(300.0);
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();
        System.out.println("✅ Volatile context completed safely\n");
    }
}
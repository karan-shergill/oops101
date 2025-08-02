package observer.revision.revision_1;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ObserverThreadSafetyDemo {

    // ❌ UNSAFE: Observer pattern like your original implementation
    interface UnsafeObserver {
        void update(String message);
        String getName();
    }

    interface UnsafeSubject {
        void addObserver(UnsafeObserver observer);
        void removeObserver(UnsafeObserver observer);
        void notifyObservers();
    }

    static class UnsafeNewsChannel implements UnsafeSubject {
        private final List<UnsafeObserver> observers = new ArrayList<>();  // ⚠️ ArrayList not thread-safe
        private String latestNews;

        public void publishNews(String news) {
            latestNews = news;
            notifyObservers();  // ⚠️ Can cause ConcurrentModificationException
        }

        @Override
        public void addObserver(UnsafeObserver observer) {
            observers.add(observer);  // ⚠️ Not synchronized
        }

        @Override
        public void removeObserver(UnsafeObserver observer) {
            observers.remove(observer);  // ⚠️ Not synchronized
        }

        @Override
        public void notifyObservers() {
            // ⚠️ CRITICAL: Iterator can fail if list is modified during iteration
            for (UnsafeObserver observer : observers) {
                observer.update(latestNews);
            }
        }

        public int getObserverCount() { return observers.size(); }
    }

    static class UnsafeSubscriber implements UnsafeObserver {
        private final String name;
        private int messageCount = 0;  // ⚠️ Not thread-safe

        public UnsafeSubscriber(String name) {
            this.name = name;
        }

        @Override
        public void update(String message) {
            messageCount++;  // ⚠️ Race condition - not atomic
            System.out.printf("[%s] %s received: %s (Count: %d)%n", 
                Thread.currentThread().getName(), name, message, messageCount);
        }

        @Override
        public String getName() { return name; }
        public int getMessageCount() { return messageCount; }
    }

    // ✅ THREAD-SAFE: Synchronized Observer pattern
    static class SynchronizedNewsChannel implements UnsafeSubject {
        private final List<UnsafeObserver> observers = new ArrayList<>();
        private String latestNews;

        public synchronized void publishNews(String news) {
            latestNews = news;
            notifyObservers();
        }

        @Override
        public synchronized void addObserver(UnsafeObserver observer) {
            if (observer != null && !observers.contains(observer)) {
                observers.add(observer);
            }
        }

        @Override
        public synchronized void removeObserver(UnsafeObserver observer) {
            observers.remove(observer);
        }

        @Override
        public synchronized void notifyObservers() {
            // ✅ Synchronized ensures no concurrent modification
            for (UnsafeObserver observer : observers) {
                try {
                    observer.update(latestNews);
                } catch (Exception e) {
                    System.err.println("Error notifying observer: " + e.getMessage());
                }
            }
        }

        public synchronized int getObserverCount() { return observers.size(); }
    }

    // ✅ THREAD-SAFE: CopyOnWriteArrayList approach
    static class CopyOnWriteNewsChannel implements UnsafeSubject {
        private final CopyOnWriteArrayList<UnsafeObserver> observers = new CopyOnWriteArrayList<>();
        private volatile String latestNews;

        public void publishNews(String news) {
            latestNews = news;
            notifyObservers();
        }

        @Override
        public void addObserver(UnsafeObserver observer) {
            if (observer != null && !observers.contains(observer)) {
                observers.add(observer);  // ✅ Thread-safe
            }
        }

        @Override
        public void removeObserver(UnsafeObserver observer) {
            observers.remove(observer);  // ✅ Thread-safe
        }

        @Override
        public void notifyObservers() {
            // ✅ CopyOnWriteArrayList provides safe iteration even during modifications
            for (UnsafeObserver observer : observers) {
                try {
                    observer.update(latestNews);
                } catch (Exception e) {
                    System.err.println("Error notifying observer: " + e.getMessage());
                }
            }
        }

        public int getObserverCount() { return observers.size(); }
    }

    // ✅ THREAD-SAFE: ReadWriteLock approach for better performance
    static class ReadWriteLockNewsChannel implements UnsafeSubject {
        private final List<UnsafeObserver> observers = new ArrayList<>();
        private final ReadWriteLock lock = new ReentrantReadWriteLock();
        private String latestNews;

        public void publishNews(String news) {
            // Use read lock for notification (multiple threads can notify simultaneously)
            lock.readLock().lock();
            try {
                latestNews = news;
                notifyObservers();
            } finally {
                lock.readLock().unlock();
            }
        }

        @Override
        public void addObserver(UnsafeObserver observer) {
            lock.writeLock().lock();  // Exclusive access for modification
            try {
                if (observer != null && !observers.contains(observer)) {
                    observers.add(observer);
                }
            } finally {
                lock.writeLock().unlock();
            }
        }

        @Override
        public void removeObserver(UnsafeObserver observer) {
            lock.writeLock().lock();  // Exclusive access for modification
            try {
                observers.remove(observer);
            } finally {
                lock.writeLock().unlock();
            }
        }

        @Override
        public void notifyObservers() {
            // Called while holding read lock
            List<UnsafeObserver> currentObservers = new ArrayList<>(observers);
            for (UnsafeObserver observer : currentObservers) {
                try {
                    observer.update(latestNews);
                } catch (Exception e) {
                    System.err.println("Error notifying observer: " + e.getMessage());
                }
            }
        }

        public int getObserverCount() {
            lock.readLock().lock();
            try {
                return observers.size();
            } finally {
                lock.readLock().unlock();
            }
        }
    }

    // ✅ THREAD-SAFE: Observer with atomic operations
    static class SafeSubscriber implements UnsafeObserver {
        private final String name;
        private final AtomicInteger messageCount = new AtomicInteger(0);

        public SafeSubscriber(String name) {
            this.name = name;
        }

        @Override
        public void update(String message) {
            int count = messageCount.incrementAndGet();  // ✅ Atomic operation
            System.out.printf("[%s] %s received: %s (Count: %d)%n", 
                Thread.currentThread().getName(), name, message, count);
        }

        @Override
        public String getName() { return name; }
        public int getMessageCount() { return messageCount.get(); }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Observer Pattern Thread Safety Demo ===\n");

        // Demo 1: Unsafe observer pattern
        demonstrateUnsafeObserver();

        // Demo 2: Synchronized observer pattern
        demonstrateSynchronizedObserver();

        // Demo 3: CopyOnWriteArrayList observer pattern
        demonstrateCopyOnWriteObserver();

        // Demo 4: ReadWriteLock observer pattern
        demonstrateReadWriteLockObserver();
    }

    static void demonstrateUnsafeObserver() throws InterruptedException {
        System.out.println("1. ❌ UNSAFE OBSERVER PATTERN");
        
        UnsafeNewsChannel channel = new UnsafeNewsChannel();
        List<UnsafeSubscriber> subscribers = new ArrayList<>();
        
        // Add initial subscribers
        for (int i = 0; i < 5; i++) {
            UnsafeSubscriber subscriber = new UnsafeSubscriber("Subscriber" + i);
            subscribers.add(subscriber);
            channel.addObserver(subscriber);
        }

        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(100);

        // Mix of publishing, adding, and removing observers
        for (int i = 0; i < 100; i++) {
            final int taskId = i;
            executor.submit(() -> {
                try {
                    if (taskId % 10 == 0) {
                        // Add new observer
                        channel.addObserver(new UnsafeSubscriber("DynamicSub" + taskId));
                    } else if (taskId % 15 == 0 && !subscribers.isEmpty()) {
                        // Remove observer
                        channel.removeObserver(subscribers.get(0));
                    } else {
                        // Publish news
                        channel.publishNews("Breaking News #" + taskId);
                    }
                } catch (Exception e) {
                    System.err.println("Exception in unsafe observer: " + e.getClass().getSimpleName());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();
        
        System.out.printf("❌ Unsafe pattern completed - likely saw ConcurrentModificationException%n");
        System.out.printf("Final observer count: %d%n%n", channel.getObserverCount());
    }

    static void demonstrateSynchronizedObserver() throws InterruptedException {
        System.out.println("2. ✅ SYNCHRONIZED OBSERVER PATTERN");
        
        SynchronizedNewsChannel channel = new SynchronizedNewsChannel();
        List<SafeSubscriber> subscribers = new ArrayList<>();
        
        // Add initial subscribers
        for (int i = 0; i < 5; i++) {
            SafeSubscriber subscriber = new SafeSubscriber("SafeSub" + i);
            subscribers.add(subscriber);
            channel.addObserver(subscriber);
        }

        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(50);

        for (int i = 0; i < 50; i++) {
            final int taskId = i;
            executor.submit(() -> {
                try {
                    if (taskId % 8 == 0) {
                        channel.addObserver(new SafeSubscriber("DynamicSafeSub" + taskId));
                    } else if (taskId % 12 == 0 && !subscribers.isEmpty()) {
                        channel.removeObserver(subscribers.get(taskId % subscribers.size()));
                    } else {
                        channel.publishNews("Safe News #" + taskId);
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();
        
        System.out.printf("✅ Synchronized pattern completed safely%n");
        System.out.printf("Final observer count: %d%n%n", channel.getObserverCount());
    }

    static void demonstrateCopyOnWriteObserver() throws InterruptedException {
        System.out.println("3. ✅ COPYONWRITEARRAYLIST OBSERVER PATTERN");
        
        CopyOnWriteNewsChannel channel = new CopyOnWriteNewsChannel();
        
        ExecutorService executor = Executors.newFixedThreadPool(8);
        CountDownLatch latch = new CountDownLatch(40);

        for (int i = 0; i < 40; i++) {
            final int taskId = i;
            executor.submit(() -> {
                try {
                    if (taskId % 6 == 0) {
                        channel.addObserver(new SafeSubscriber("COWSub" + taskId));
                    } else if (taskId % 10 == 0) {
                        // Remove random observer (if any exist)
                        if (channel.getObserverCount() > 0) {
                            // Note: In real implementation, you'd need a way to get observers to remove them
                            // This is just for demonstration
                        }
                    } else {
                        channel.publishNews("COW News #" + taskId);
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();
        
        System.out.printf("✅ CopyOnWriteArrayList pattern completed safely%n");
        System.out.printf("Final observer count: %d%n%n", channel.getObserverCount());
    }

    static void demonstrateReadWriteLockObserver() throws InterruptedException {
        System.out.println("4. ✅ READWRITELOCK OBSERVER PATTERN");
        
        ReadWriteLockNewsChannel channel = new ReadWriteLockNewsChannel();
        
        // Add some initial observers
        for (int i = 0; i < 3; i++) {
            channel.addObserver(new SafeSubscriber("RWLSub" + i));
        }

        ExecutorService executor = Executors.newFixedThreadPool(8);
        CountDownLatch latch = new CountDownLatch(30);

        for (int i = 0; i < 30; i++) {
            final int taskId = i;
            executor.submit(() -> {
                try {
                    if (taskId % 7 == 0) {
                        channel.addObserver(new SafeSubscriber("DynamicRWLSub" + taskId));
                    } else {
                        channel.publishNews("RWL News #" + taskId);
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();
        
        System.out.printf("✅ ReadWriteLock pattern completed safely%n");
        System.out.printf("Final observer count: %d%n", channel.getObserverCount());
    }
}
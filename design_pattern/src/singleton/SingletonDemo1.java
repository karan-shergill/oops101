// UML: https://tinyurl.com/ybafg4eg

package singleton;

class LazySingleton {
    // The single instance, initially null
    private static LazySingleton instance;

    // Private constructor to prevent instantiation
    private LazySingleton() {}

    // Public method to get the instance
    public static LazySingleton getInstance() {
        // Check if instance is null
        if (instance == null) {
            // If null, create a new instance
            instance = new LazySingleton();
        }
        // Return the instance (either newly created or existing)
        return instance;
    }

    public void display() {
        System.out.println("LazySingleton");
    }
}

class ThreadSafeSingleton {
    // The single instance, initially null
    private static ThreadSafeSingleton instance;

    // Private constructor to prevent instantiation
    private ThreadSafeSingleton() {}

    // Public method to get the instance, with synchronized keyword
    public static synchronized ThreadSafeSingleton getInstance() {
        // Check if instance is null
        if (instance == null) {
            // If null, create a new instance
            instance = new ThreadSafeSingleton();
        }
        // Return the instance (either newly created or existing)
        return instance;
    }

    public void display() {
        System.out.println("ThreadSafeSingleton");
    }
}

class DoubleCheckedSingleton {
    // The single instance, initially null, marked as volatile
    private static volatile DoubleCheckedSingleton instance;

    // Private constructor to prevent instantiation
    private DoubleCheckedSingleton() {}

    // Public method to get the instance
    public static DoubleCheckedSingleton getInstance() {
        // First check (not synchronized)
        if (instance == null) {
            // Synchronize on the class object
            synchronized (DoubleCheckedSingleton.class) {
                // Second check (synchronized)
                if (instance == null) {
                    // Create the instance
                    instance = new DoubleCheckedSingleton();
                }
            }
        }
        // Return the instance (either newly created or existing)
        return instance;
    }

    public void display() {
        System.out.println("DoubleCheckedSingleton");
    }
}

class EagerSingleton {
    // The single instance, created immediately
    private static final EagerSingleton instance = new EagerSingleton();

    // Private constructor to prevent instantiation
    private EagerSingleton() {}

    // Public method to get the instance
    public static EagerSingleton getInstance() {
        return instance;
    }

    public void display() {
        System.out.println("EagerSingleton");
    }
}

class SingletonViaStaticInnerClass {
    private SingletonViaStaticInnerClass() {}

    private static class Holder {
        private static final SingletonViaStaticInnerClass instance = new SingletonViaStaticInnerClass();
    }

    public static SingletonViaStaticInnerClass getInstance() {
        return Holder.instance;
    }

    public void display() {
        System.out.println("SingletonViaStaticInnerClass");
    }
}

public class SingletonDemo1 {
    public static void main(String[] args) {
        LazySingleton lazySingleton = LazySingleton.getInstance();
        lazySingleton.display();

        ThreadSafeSingleton threadSafeSingleton = ThreadSafeSingleton.getInstance();
        threadSafeSingleton.display();

        DoubleCheckedSingleton doubleCheckedSingleton = DoubleCheckedSingleton.getInstance();
        doubleCheckedSingleton.display();

        EagerSingleton eagerSingleton = EagerSingleton.getInstance();
        eagerSingleton.display();

        SingletonViaStaticInnerClass singletonViaStaticInnerClass = SingletonViaStaticInnerClass.getInstance();
        singletonViaStaticInnerClass.display();
    }
}
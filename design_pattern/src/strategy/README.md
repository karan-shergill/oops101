# Strategy Design Pattern

The **Strategy Design Pattern** is a behavioral design pattern that allows you to define a family of algorithms, encapsulate each one of them, and make them interchangeable. The strategy pattern enables an algorithm's behavior to be selected at runtime.

1. [Strategy Design Pattern in detail](https://youtu.be/xcMJUtWbFZU)
2. [Strategy Design Pattern explanation](https://youtu.be/u8DttUrXtEw)
3. [Strategy Design Pattern in Java](https://medium.com/@akshatsharma0610/strategy-design-pattern-in-java-6ee96f87d807)
4. [Polymorphism vs Strategy pattern](https://stackoverflow.com/questions/31608902/polymorphism-vs-strategy-pattern)

![strategy_pattern_example.png](../../images/strategy_pattern_example.png)

## Key Concepts

1. **Encapsulation of Algorithms**: The strategy pattern allows you to define different algorithms or strategies in separate classes, each implementing the same interface. This encapsulation allows the client to choose which algorithm to use at runtime without changing the code.
2. **Interchangeability**: Different strategies can be swapped out as needed, without affecting the client code. This promotes flexibility and reusability.
3. **Open/Closed Principle**: The pattern adheres to the open/closed principle, meaning that it is open for extension but closed for modification. New strategies can be added without altering existing code.
4. **Composition over Inheritance**: The strategy pattern uses composition rather than inheritance. It delegates the behavior to different strategy objects rather than using a single class hierarchy to implement different behaviors.

## Structure

1. **Strategy Interface**: An interface common to all supported algorithms. Context uses this interface to call the algorithm defined by a ConcreteStrategy.
2. **Concrete Strategies**: Classes that implement the Strategy interface, encapsulating specific algorithms or behaviors.
3. **Context**: The class that uses a Strategy. It maintains a reference to a Strategy object and delegates the work to the currently selected strategy.

## Benefits

1. **Flexibility**: Easily switch between different algorithms or strategies without altering the client code.
2. **Scalability**: Add new strategies without modifying existing code, adhering to the open/closed principle.
3. **Maintainability**: Separate the algorithm's implementation from the client, making the code easier to maintain and extend.

## When to Use

1. You have multiple algorithms for the same task. 
2. You want to avoid large conditional statements (like if-else or switch-case). 
3. You need to be able to change behavior at runtime. 
4. You want to encapsulate and isolate logic for flexibility and testing.

## Skeleton Template

```java
// 1. Strategy interface
interface Strategy {
    ReturnType execute(InputType input);
}

// 2. Concrete strategies
class ConcreteStrategyA implements Strategy {
    @Override
    public ReturnType execute(InputType input) {
        // algorithm A
    }
}

class ConcreteStrategyB implements Strategy {
    @Override
    public ReturnType execute(InputType input) {
        // algorithm B
    }
}

// 3. Context class
class Context {
    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(Strategy strategy) { // Optional: for runtime change
        this.strategy = strategy;
    }

    public ReturnType performStrategy(InputType input) {
        return strategy.execute(input);
    }
}

// 4. Client code
public class Client {
    public static void main(String[] args) {
        Context context = new Context(new ConcreteStrategyA());
        System.out.println(context.performStrategy(someInput));

        context.setStrategy(new ConcreteStrategyB()); // switching strategy at runtime
        System.out.println(context.performStrategy(someInput));
    }
}

```

## Best Practices
1. Keep strategies stateless - Don't pass input via constructor — pass input via method arguments.
2. Use composition over inheritance - The Context has a strategy (HAS-A relationship).
3. Strategy should only do one thing - Each strategy class should focus on a single variation of the behavior.
4. Make the interface intuitive - Name it around the behavior: PaymentStrategy, CompressionStrategy, SortStrategy.
5. Switch strategy dynamically (if needed) - Via a setStrategy() method in the context, allow changing behavior at runtime.

## Real-world Examples
| Use Case          | Strategy Interface    | Concrete Strategies                             |
| ----------------- | --------------------- | ----------------------------------------------- |
| Compression Tool  | `CompressionStrategy` | `ZipCompression`, `GzipCompression`             |
| Payment Gateway   | `PaymentStrategy`     | `CreditCardPayment`, `PaypalPayment`            |
| Navigation App    | `RouteStrategy`       | `DrivingRoute`, `WalkingRoute`, `CyclingRoute`  |
| Sorting Algorithm | `SortStrategy`        | `QuickSort`, `MergeSort`, `BubbleSort`          |
| Image Filter App  | `FilterStrategy`      | `BlackWhiteFilter`, `SepiaFilter`, `BlurFilter` |

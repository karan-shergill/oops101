# Object Oriented Design Principles

# SOLID Design Principles

Learn more about SOLID design principles from here:

1. [SOLID Principles with Easy Examples](https://youtu.be/XI7zep97c-Y)
2. [Liskov Substitution Principle (LSP) with Solution in Java](https://youtu.be/129QkkXUHeQ)
3. [What are the SOLID principles in Java?](https://www.educative.io/answers/what-are-the-solid-principles-in-java)

### Here’s how you can apply SOLID Design Principles in your day-to-day development:

1. In the **Single Responsibility Principle (SRP)**, each class should be responsible for a single part or functionality of the system.
    1. Ensure that each class or method has one, and only one, responsibility. It should focus on a single part of the functionality
    2. If a class has multiple responsibilities, refactor it into smaller classes, each handling one responsibility.
    3. Name your classes based on the single responsibility they fulfill. This makes it clear what each class does.
2. In the **Open Closed principle (OCP)**, software components should be open for extension but closed for modification.
    1. Write classes that can be extended without modifying existing code. Use inheritance or composition to allow for extensions.
    2. Define interfaces or abstract classes that can be implemented or extended by new classes without altering existing code.
    3. Apply design patterns like Strategy, Template Method, or Decorator to adhere to OCP by allowing behaviors to be extended without changing existing code.
3. In the **Liskov Substitution Principle (LSP)**, objects of a superclass should be replaceable with objects of its subclasses without breaking the system. This rule generally applies to abstraction concepts like inheritance and polymorphism.
    1. Subclasses should be able to replace their base classes without altering the correctness of the program.
    2. When overriding methods in a subclass, ensure that the new method fulfills the expectations set by the base class method.
    3. Subclasses should not override methods with less specific behavior (e.g., making a method that throws fewer exceptions).
    4. If a subclass cannot fully adhere to the LSP, consider using composition rather than inheritance.
4. The **Interface Segregation Principle (ISP)** is a design principle that does not recommend having methods that an interface would not use and require. Therefore, it goes against having fat interfaces in classes and prefers having small interfaces with a group of methods, each serving a particular purpose.
    1. Design interfaces with specific methods relevant to their purpose. Avoid creating large, all-encompassing interfaces.
    2. Split large interfaces into smaller, more focused ones that only contain methods relevant to a particular client.
    3. When introducing new methods to an interface, consider using default methods to avoid breaking existing implementations.
    4. Tailor interfaces to the specific needs of the clients that use them, ensuring they don’t depend on methods they don’t use.
5. The **Dependency Inversion Principle (DIP)**, ensures that the high-level modules are not dependent on low-level modules. In other words, one should depend upon abstraction and not concretion. Classes should depend on interfaces rather than concrete classes.
    1. Ensure that high-level modules do not depend on low-level modules. Both should depend on abstractions (e.g., interfaces).
    2. Apply dependency injection to pass dependencies (e.g., services, repositories) through constructors or setters rather than instantiating them directly within classes.
    3. Leverage frameworks like Spring to manage dependencies, allowing you to decouple classes from their concrete implementations.

# **DRY (Don't Repeat Yourself)**

Avoid duplicating code by extracting common functionality into reusable components.

1. [Mastering the DRY Principle in Java](https://medium.com/@psdevraye/mastering-the-dry-principle-in-java-a-guide-to-cleaner-code-afd889bfca42)
2. [How to obey DRY principle](https://softwareengineering.stackexchange.com/questions/392686/how-to-obey-dry-principle)

### Here’s how you can apply DRY in your day-to-day development:

1. Use Methods for Repeated Logic
2. Leverage Inheritance and Interfaces
3. Create Reusable Utility Classes
4. Parameterize Methods
5. Use Design Patterns
6. Leverage Constants and Enums
7. Write Reusable Code Modules

# **YAGNI (You Aren't Gonna Need It)**

Do not add functionality until it is necessary, to avoid over-engineering.

1. [Exploring the YAGNI Principle in Java](https://medium.com/@psdevraye/mastering-efficiency-exploring-the-yagni-principle-in-java-development-c4b5ad7ea5e7)
2. [What is YAGNI coding rule, and why it helps?](https://youtu.be/2vys1q1dKc4)

### Here’s how you can apply YAGNI in your day-to-day development:

1. Focus on Current Requirements
2. Avoid Premature Optimization
3. Delay Generalization
4. Implement Minimal Solutions
5. Refactor as Needed
6. Postpone Adding Features
7. Limit the Use of Design Patterns

# **KISS (Keep It Simple, Stupid)**

Prefer simplicity and clarity in design over unnecessary complexity.

1. [Understanding the KISS Principle in Java](https://medium.com/@psdevraye/simplicity-at-its-core-understanding-the-kiss-principle-in-java-e84f7dc1fe8e)
2. [KISS Software Design Principle](https://www.baeldung.com/cs/kiss-software-design-principle)
3. [What is the KISS rule in programming?](https://youtu.be/bEG94zyZlX0)

### Here’s how you can apply KISS in your day-to-day development:

1. Write Clear and Concise Code
2. Avoid Over-Engineering
3. Use Descriptive Naming Conventions
4. Break Down Complex Problems
5. Favor Composition Over Inheritance
6. Simplify Control Structures
7. Limit the Use of Design Patterns
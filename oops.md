# Object Oriented Programming (OOPs) Concept

- [Overview](#overview)
- [Class](#class)
- [Objects](#objects)
- [Keywords](#keywords)
- [Inheritance](#inheritance)
- [Polymorphism](#polymorphism)
- [Abstraction](#abstraction)
- [Encapsulation](#encapsulation)
- [Java-Specific OOP Features](#java-specific-oop-features)

## Overview

1. [What is Object-Oriented Programming?](https://www.w3schools.com/java/java_oop.asp)
2. [OOP Concepts with real-world examples](https://jeemariyana.medium.com/oop-concepts-with-real-world-examples-cda1cd277f4f) - Why we are write coding, to solve our real world problem right. Real world can easily be demonstrated by OOPs concepts & principals.
3. [Why do we need to learn Object Oriented Programming?](https://www.enjoyalgorithms.com/blog/why-should-we-learn-oops-concepts-in-programming)
    - Duplicate code is a Bad.
    - Code will always be changed.
    - Features of OOPs
4. [Advantages and Disadvantages of OOP](https://www.scaler.com/topics/oops-advantages/)
    - Advantages: Modularity, Reusability, Encapsulation, Flexibility and Scalability, Code Organization, Code Maintenance, Code Reusability & Better Problem Solving
    - Disadvantages: Steeper learning curve, Increased complexity, Performance overhead, Dependency management & Overuse of inheritance
5. [Real Life Examples of Object Oriented Programming](https://www.c-sharpcorner.com/blogs/real-life-examples-of-object-oriented-programming1)

## Class

1. [What is a class?](https://www.guru99.com/java-oops-class-objects.html) - Class are a blueprint or a set of instructions to build a specific type of object.
2. [Access Modifiers in Java Class?](https://www.baeldung.com/java-access-modifiers) - Access modifiers in Java specifies the accessibility or scope of a field, method, constructor, or class. There are four types of Java access modifiers: Private, Default, Public, Protected.
    - **private -** accessible within the same class
    - **default -** accessible within the same package
    - **protected -** accessible within the same package and subclasses
    - **public -** accessible from anywhere
3. [Java Class Methods?](https://www.w3schools.com/java/java_class_methods.asp)
4. [What are Java Constructors?](https://www.digitalocean.com/community/tutorials/constructor-in-java) - A constructor in Java is a special method that is used to initialise objects. The constructor is called when an object of a class is created. It can be used to set initial values for object attributes. Read more about Default constructor, Parameterised constructor, Constructor Overloading
    - **Default constructor -** initializes objects with no parameters
    - **Parameterized constructor -** initializes objects using specified values
    - **Copy constructor -** creates a new object by copying an existing object's values

## Objects

1. [What is an Object?](https://www.guru99.com/java-oops-class-objects.html) - Object is an instance of a class. An object in OOPS is nothing but a self-contained component which consists of methods and properties to make a particular type of data useful.
2. [What are difference between object and class?](https://www.scaler.com/topics/difference-between-class-and-object/)

## Keywords

1. [Java static keyword](https://www.geekster.in/articles/static-keyword-in-java/) - The static keyword in Java is used for memory management mainly. The static keyword belongs to the class than an instance of the class. Read more about static variable & static methods
2. [Java abstract Keyword](https://www.scaler.com/topics/abstract-keyword-in-java/)
    - The abstract keyword is used to achieve abstraction in Java. It is a non-access modifier which is used to create abstract class and method.
    - The role of an abstract class is to contain abstract methods. However, it may also contain non-abstract methods. The method which is declared with abstract keyword and doesn't have any implementation is known as an abstract method.
3. [Java Final Keyword](https://www.geekster.in/articles/final-keyword-in-java/) - The final keyword in java is used to restrict the user. The java final keyword can be used in many context. Final can be: variable, method, class
4. [Java This Keyword](https://www.geekster.in/articles/this-keyword-in-java/) - In java, this is a reference variable that refers to the current object.
5. [Java new Keyword](https://www.geekster.in/articles/java-new-keyword/) - The Java new keyword is used to create an instance of the class. In other words, it instantiates a class by allocating memory for a new object and returning a reference to that memory. We can also use the new keyword to create the array object.
6. [Java Super Keyword](https://www.geekster.in/articles/super-keyword-in-java/) - The super keyword in Java is a reference variable which is used to refer immediate parent class object.

## Inheritance

1. [What is Inheritance?](https://www.geekster.in/articles/java-inheritance/) - Inheritance in Java is a mechanism in which one object acquires all the properties and behaviours of a parent object. Inheritance represents the IS-A relationship which is also known as a parent-child relationship. Term to understand : Sub Class, Super Class, Reusability.
2. [Why use inheritance at all?](https://stackoverflow.com/questions/3351666/why-use-inheritance-at-all)
3. [Type of Inheritance in Java](https://www.studytonight.com/java/inheritance-in-java.php) - Read : Disadvantage of Interface, Single Inheritance, Multilevel Inheritance and Hierarchical Inheritance. Why Multiple inheritance is not supported in java?
4. [Advantages and disadvantages of inheritance?](https://www.studytrigger.com/article/inheritance-in-java/)
    - Advantages: Code reusability, Improved code organization, Polymorphism, Modularity & Flexibility and extensibility
    - Disadvantages: Tight coupling, Fragile base class problem, Inflexibility & Overuse
5. [Why is there no multiple inheritance in Java, but implementing multiple interfaces is allowed?](https://stackoverflow.com/questions/2515477/why-is-there-no-multiple-inheritance-in-java-but-implementing-multiple-interfac)
6. [What is diamond problem in case of multiple inheritance in java?](https://www.ccbp.in/blog/articles/diamond-problem-in-java)
7. [Inheritance (IS-A relationship)](https://www.scaler.com/topics/what-is-is-a-relationship-in-java/) and [Aggregation (HAS-A relationship)](https://www.ccbp.in/blog/articles/has-a-relationship-in-java) 
    - **Inheritance (IS-A relationship):** A mechanism where a subclass inherits properties and behaviors of a superclass, representing an "is-a" relationship (e.g., a Dog **is-a** Animal).
    - **Aggregation (HAS-A relationship):** A design principle where one class contains a reference to another, representing a "has-a" relationship (e.g., a Car **has-a** Engine).
8. [Association, Aggregation, Composition, Abstraction, Generalization, Realization, Dependency](https://javapapers.com/oops/association-aggregation-composition-abstraction-generalization-realization-dependency/)
9. [In Java Association, Aggregation and Composition Explained](https://javaocean.in/aggregation-and-composition-explained-in-java/)
    - **Association** is a general "uses-a" relationship between two classes
    - **Aggregation** is a weak "has-a" relationship where the child can exist independently of the parent.
    - **Composition** is a strong "has-a" relationship where the child’s lifecycle is strictly bound to the parent.
10. [Association, Composition and Aggregation in Java](https://www.scaler.com/topics/association-composition-and-aggregation-in-java/)
11. [Prefer Composition Over Inheritance](https://freedium.cfd/https://medium.com/better-programming/prefer-composition-over-inheritance-1602d5149ea1)
12. [Why Composition is Preferred Over Inheritance](https://dev.to/leapcell/from-java-to-go-why-composition-is-preferred-over-inheritance-17h4) - **Composition is preferred over inheritance** because it provides **greater flexibility**, allows **better code reuse**, and **avoids tight coupling**, making the system **easier to maintain and extend** without being constrained by a rigid class hierarchy.
13. [Java inheritance vs. composition: How to choose?](https://www.infoworld.com/article/2261980/java-challenger-7-debugging-java-inheritance.html)
14. [The Flaws of Inheritance](https://youtu.be/hxGOiiR9ZKg) 
15. [Composition over Inheritance Explained by Games](https://youtu.be/HNzP1aLAffM) 
16. [Only Use Inheritance If You Want Both of These](https://youtu.be/C3B5IIlt4-0) 

## Polymorphism

1. [What is Polymorphism?](https://www.geekster.in/articles/polymorphism-in-java/) - Polymorphism in Java is a concept by which we can perform a single action in different ways. There are two types of polymorphism in Java: compile-time polymorphism and runtime polymorphism. We can perform polymorphism in java by method overloading and method overriding. Read more about: Upcasting
2. [Java Polymorphism](https://youtu.be/jhDUxynEQRI)
    - **Method Overloading** is defining multiple methods in the same class with the same name but different parameters.
    - **Method Overriding** is redefining a superclass method in a subclass with the same name, return type, and parameters.
3. [What is Upcasting And Downcasting in Java?](https://www.scaler.com/topics/upcasting-and-downcasting-in-java/)
4. [Why and when to use polymorphism?](https://medium.com/swlh/why-and-when-to-use-polymorphism-ffcbf3709509)
5. [Difference Between Compile-time and Runtime Polymorphism in Java](https://www.enjoyalgorithms.com/blog/difference-between-compile-time-and-runtime-polymorphism)
6. [Static vs dynamic binding](https://beginnersbook.com/2013/04/java-static-dynamic-binding/)
7. [The Only Time You Should Use Polymorphism](https://youtu.be/YaSMkzmc_sA)

## Abstraction

1. [What is Abstraction? Reason to use Abstraction?](https://www.ccbp.in/blog/articles/what-is-abstraction-in-java)
2. There are two ways to achieve abstraction in java
    - [Abstract class (0 to 100%)](https://www.scaler.com/topics/java/abstract-class-in-java/) : A class which is declared as abstract is known as an abstract class. It can have abstract and non-abstract methods. It needs to be extended and its method implemented. It cannot be instantiated.
    - [Interface (100%)](https://www.scaler.com/topics/java/interface-in-java/)
3. [Abstract Classes and Methods in Java](https://youtu.be/HvPlEJ3LHgE)
4. [Abstract Classes vs Interfaces](https://youtu.be/Lnqmde9LP74)
    - **Use an abstract class** when you need to share common code (state or behavior) among closely related classes.
    - **Use an interface** when you want to define a contract for unrelated classes or need multiple inheritance.

## Encapsulation

1. [What is Encapsulation?](https://www.scaler.com/topics/java/encapsulation-in-java/) - Encapsulation in Java is a mechanism to wrap up variables(data) and methods(code) together as a single unit. Encapsulation hides variables or some implementation that may be changed so often in a class to prevent outsiders access it directly. They must access it via getter and setter methods.
2. Implement Encapsulation in Java?
    - Use the private access modifier to declare all variables/fields of class as private.
    - Define public getter and setter methods to read and modify/set the values of the above said fields.
3. [If the purpose of Encapsulation and Abstraction is to hide information then what the actual difference between these two is?](https://stackoverflow.com/questions/15176356/difference-between-encapsulation-and-abstraction)

## Java-Specific OOP Features

1. [Object Class in Java](https://www.scaler.com/topics/object-class-in-java/)
    - **equals()** – Compares two objects for logical equality.
    - **hashCode()** – **hashCode()** returns an integer value (hash code) that represents the internal address or state of an object and is used to efficiently locate objects in hash-based collections like HashMap, ensuring that equal objects produce the same hash code.
    - **toString()** – Returns a string representation of the object.
    - **clone()** – Creates and returns a copy (clone) of the object.
2. [Java instanceof Operator](https://www.baeldung.com/java-instanceof) - The instanceof operator in Java is used to check whether an object is an instance of a specific class or implements a specific interface, returning true or false.
3. [Nested Class in Java](https://www.scaler.com/topics/what-is-nested-class-in-java/)

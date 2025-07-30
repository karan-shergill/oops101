// UML Diagram: https://tinyurl.com/2ddrjpdm

package builder;

import java.util.ArrayList;
import java.util.List;

// Immutable Student class that is created using the Builder pattern
class Student {
    private final int rollNumber;
    private final int age;
    private final String name;
    private final String fatherName;
    private final String motherName;
    private final List<String> subjects;

    /*
    Constructor that initializes the Student object using a StudentBuilder.
    This ensures that the object is created with all necessary fields, as set by the builder.
    */
    public Student(StudentBuilder builder){
        this.rollNumber = builder.rollNumber;
        this.age = builder.age;
        this.name = builder.name;
        this.fatherName = builder.fatherName;
        this.motherName = builder.motherName;
        this.subjects = builder.subjects;
    }

    /*
    Method to return a string representation of the Student object.
    This helps in displaying the details of the Student.
    */
    @Override
    public String toString(){
        return "roll number: " + this.rollNumber +
                ", age: " + this.age +
                ", name: " + this.name +
                ", father name: " + this.fatherName +
                ", mother name: " + this.motherName +
                ", subjects: " + String.join(", ", subjects);
    }
}

// Abstract builder class for creating Student objects
abstract class StudentBuilder {
    int rollNumber;
    int age;
    String name;
    String fatherName;
    String motherName;
    List<String> subjects = new ArrayList<>(); // Initialize with an empty list

    /*
    Methods to set properties of the Student object.
    Each method returns the builder instance for method chaining.
    */
    public StudentBuilder setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
        return this;
    }

    public StudentBuilder setAge(int age) {
        this.age = age;
        return this;
    }

    public StudentBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public StudentBuilder setFatherName(String fatherName) {
        this.fatherName = fatherName;
        return this;
    }

    public StudentBuilder setMotherName(String motherName) {
        this.motherName = motherName;
        return this;
    }

    /*
    Abstract method for setting subjects.
    Subclasses must provide their own implementation to set the specific subjects.
    */
    abstract public StudentBuilder setSubjects();

    /*
    Method to build the Student object using the current state of the builder.
    */
    public Student build() {
        return new Student(this);
    }
}

// Concrete builder class for Engineering students
class EngineeringStudentBuilder extends StudentBuilder {
    @Override
    public StudentBuilder setSubjects() {
        // Set default subjects for an engineering student
        List<String> subs = new ArrayList<>();
        subs.add("DSA");
        subs.add("OS");
        subs.add("Computer Architecture");
        this.subjects = subs;
        return this;
    }
}

// Concrete builder class for MBA students
class MBAStudentBuilder extends StudentBuilder {
    @Override
    public StudentBuilder setSubjects() {
        // Set default subjects for an MBA student
        List<String> subs = new ArrayList<>();
        subs.add("Micro Economics");
        subs.add("Business Studies");
        subs.add("Operations Management");
        this.subjects = subs;
        return this;
    }
}

// Director class to manage the construction process of Student objects
class Director {
    private StudentBuilder studentBuilder;

    /*
    Constructor to initialize the Director with a StudentBuilder instance.
    */
    public Director(StudentBuilder studentBuilder){
        this.studentBuilder = studentBuilder;
    }

    /*
    Method to create an EngineeringStudent with predefined attributes.
    */
    public Student createEngineeringStudent(){
        return studentBuilder
                .setRollNumber(10101)
                .setAge(22)
                .setName("Ashish")
                .setSubjects() // Calls the builder's method to set default subjects for engineering students
                .build();
    }

    /*
    Method to create an MBAStudent with predefined attributes.
    */
    public Student createMBAStudent(){
        return studentBuilder
                .setRollNumber(20202)
                .setAge(24)
                .setName("Arjun")
                .setFatherName("Arun") // Optional field
                .setMotherName("Shanti") // Optional field
                .setSubjects() // Calls the builder's method to set default subjects for MBA students
                .build();
    }
}

public class BuilderDemo3 {
    public static void main(String[] args){
        // Create Director instances with specific builders for engineering and MBA students
        Director engineeringDirector = new Director(new EngineeringStudentBuilder());
        Director mbaDirector = new Director(new MBAStudentBuilder());

        // Create different types of Student objects using the Director
        Student engineerStudent = engineeringDirector.createEngineeringStudent();
        Student mbaStudent = mbaDirector.createMBAStudent();

        // Print the details of the created Student objects
        System.out.println(engineerStudent.toString());
        System.out.println(mbaStudent.toString());
    }
}


/*
OUTPUT:

roll number: 10101, age: 22, name: Ashish, father name: null, mother name: null, subjects: DSA, OS, Computer Architecture
roll number: 20202, age: 24, name: Arjun, father name: Arun, mother name: Shanti, subjects: Micro Economics, Business Studies, Operations Management
*/
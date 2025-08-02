package observer.revision.revision_1;

import java.util.ArrayList;
import java.util.List;

// ✅ GOOD: Proper Observer interface - defines the contract
// LEARNING POINT 1: Could use generics for type safety: Observer<T>
// LEARNING POINT 2: Could have multiple update methods for different data types
interface Observer {
    void update(String message);
}

// ✅ GOOD: Proper Subject interface - defines observable contract
// LEARNING POINT 3: Method should be "notifyObservers" (plural)
// LEARNING POINT 4: Could include hasObserver(), getObserverCount() methods
interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObserver(); // Should be "notifyObservers"
}

// ✅ GOOD: Concrete Subject implementation
// LEARNING POINT 5: Fields should be private for encapsulation
// LEARNING POINT 6: Missing input validation and null checks
// LEARNING POINT 7: Could use generics for type safety
class Group implements Subject {
    List<Observer> listOfMembers; // LEARNING POINT 8: Should be private
    String message; // LEARNING POINT 9: Should be private

    public Group() {
        this.listOfMembers = new ArrayList<>();
    }

    // LEARNING POINT 10: Method name "sentMessage" should be "sendMessage"
    public void sentMessage(String message) {
        System.out.println("Got new message on group: " + message);
        this.message = message;
        notifyObserver(); // LEARNING POINT 11: Good - automatic notification
    }

    @Override
    public void addObserver(Observer observer) {
        listOfMembers.add(observer); // LEARNING POINT 12: No null check or duplicate prevention
    }

    @Override
    public void removeObserver(Observer observer) {
        listOfMembers.remove(observer); // LEARNING POINT 13: No validation if observer exists
    }

    @Override
    public void notifyObserver() {
        // LEARNING POINT 14: Good - demonstrates "push" model (data sent to observers)
        for (Observer observer: listOfMembers) {
            observer.update(this.message); // LEARNING POINT 15: Could handle exceptions
        }
    }
}

// LEARNING POINT 16: Abstract class seems unnecessary - no abstract methods
// LEARNING POINT 17: Fields should be private with getters
// LEARNING POINT 18: Could just use a concrete Member class instead
abstract class MemberDetails {
    String name; // LEARNING POINT 19: Should be private
    String email; // LEARNING POINT 20: Should be private

    public MemberDetails(String name, String email) {
        this.name = name;
        this.email = email;
    }
}

// LEARNING POINT 21: Code duplication - Member1, Member2, Member3 are nearly identical
// LEARNING POINT 22: Hard-coded class names in output strings
// LEARNING POINT 23: Could use one Member class with different behaviors
class Member1 extends MemberDetails implements Observer {
    public Member1(String name, String email) {
        super(name, email);
    }

    @Override
    public void update(String message) {
        System.out.println("Member1 receives a new message from the group: " + message);
        // LEARNING POINT 24: Could show different reaction behaviors here
    }
}

// LEARNING POINT 25: Same issues as Member1 - demonstrates pattern but not realistic differences
class Member2 extends MemberDetails implements Observer {
    public Member2(String name, String email) {
        super(name, email);
    }

    @Override
    public void update(String message) {
        System.out.println("Member2 receives a new message from the group: " + message);
    }
}

class Member3 extends MemberDetails implements Observer {
    public Member3(String name, String email) {
        super(name, email);
    }

    @Override
    public void update(String message) {
        System.out.println("Member3 receives a new message from the group: " + message);
    }
}

public class Observer_1 {
    public static void main(String[] args) {
        // ✅ GOOD: Demonstrates basic Observer pattern usage
        Group group1 = new Group();

        // LEARNING POINT 26: Creates different types but they behave identically
        Member1 member1 = new Member1("tom", "tom@gmail.com");
        Member2 member2 = new Member2("jhon", "jhon@gmail.com"); // TYPO: "john"
        Member3 member3 = new Member3("harry", "harry@gmail.com");
        Member3 member4 = new Member3("porter", "porter@gmail.com");

        // ✅ GOOD: Shows adding multiple observers
        group1.addObserver(member1);
        group1.addObserver(member2);
        group1.addObserver(member3);
        group1.addObserver(member4);

        // ✅ GOOD: Demonstrates notification to all observers
        group1.sentMessage("Let's meet today evening at 7PM");

        // ✅ GOOD: Shows removing an observer
        group1.removeObserver(member2);

        // ✅ GOOD: Shows that removed observer doesn't get notified
        group1.sentMessage("UPDATE! meeting time updated from 7PM to 9PM");
        
        // LEARNING POINT 27: Could demonstrate more Observer pattern features:
        // - Multiple subjects
        // - Different types of notifications  
        // - Observer subscribing to multiple subjects
        // - Error handling during notification
    }
}

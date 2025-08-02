package observer.revision.revision_1;

import java.util.ArrayList;
import java.util.List;

// SIMPLE Observer Pattern for Learning Core Concepts

// 1. OBSERVER INTERFACE - Defines what observers must implement
interface MessageObserver {
    void onMessageReceived(String message);
    String getName(); // Added for better identification
}

// 2. SUBJECT INTERFACE - Defines observable behavior
interface MessageSubject {
    void subscribe(MessageObserver observer);
    void unsubscribe(MessageObserver observer);
    void notifyObservers();
}

// 3. CONCRETE SUBJECT - The thing being observed
class ChatGroup implements MessageSubject {
    private List<MessageObserver> members;
    private String latestMessage;
    private String groupName;

    public ChatGroup(String groupName) {
        this.groupName = groupName;
        this.members = new ArrayList<>();
    }

    // Business method that triggers notification
    public void postMessage(String message) {
        System.out.printf("\n[%s] New message posted: \"%s\"%n", groupName, message);
        this.latestMessage = message;
        notifyObservers(); // Automatic notification
    }

    @Override
    public void subscribe(MessageObserver observer) {
        if (observer != null && !members.contains(observer)) {
            members.add(observer);
            System.out.printf("%s joined the group '%s'%n", observer.getName(), groupName);
        }
    }

    @Override
    public void unsubscribe(MessageObserver observer) {
        if (members.remove(observer)) {
            System.out.printf("%s left the group '%s'%n", observer.getName(), groupName);
        }
    }

    @Override
    public void notifyObservers() {
        System.out.printf("Notifying %d members...%n", members.size());
        for (MessageObserver observer : members) {
            observer.onMessageReceived(latestMessage);
        }
    }

    public int getMemberCount() { return members.size(); }
    public String getGroupName() { return groupName; }
}

// 4. CONCRETE OBSERVER - One implementation
class GroupMember implements MessageObserver {
    private String name;
    private String email;
    private boolean isOnline;

    public GroupMember(String name, String email) {
        this.name = name;
        this.email = email;
        this.isOnline = true;
    }

    @Override
    public void onMessageReceived(String message) {
        if (isOnline) {
            System.out.printf("  📱 %s received: \"%s\"%n", name, message);
        } else {
            System.out.printf("  📴 %s is offline (message saved)%n", name);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    // Unique behavior for this observer type
    public void goOffline() {
        this.isOnline = false;
        System.out.printf("%s went offline%n", name);
    }

    public void goOnline() {
        this.isOnline = true;
        System.out.printf("%s came online%n", name);
    }

    public boolean isOnline() { return isOnline; }
    public String getEmail() { return email; }
}

// 5. DIFFERENT OBSERVER TYPE - Shows polymorphic behavior
class BotObserver implements MessageObserver {
    private String botName;
    private String trigger;

    public BotObserver(String botName, String trigger) {
        this.botName = botName;
        this.trigger = trigger;
    }

    @Override
    public void onMessageReceived(String message) {
        if (message.toLowerCase().contains(trigger.toLowerCase())) {
            System.out.printf("  🤖 %s (Bot) auto-responded to keyword '%s'%n", botName, trigger);
        } else {
            System.out.printf("  🤖 %s (Bot) is listening...%n", botName);
        }
    }

    @Override
    public String getName() {
        return botName + " (Bot)";
    }

    public String getTrigger() { return trigger; }
}

// 6. DEMONSTRATION
public class Observer_1_simple {
    public static void main(String[] args) {
        System.out.println("=== Simple Observer Pattern Demo ===");

        // Create the subject (observable)
        ChatGroup studyGroup = new ChatGroup("Java Study Group");

        // Create different types of observers
        GroupMember alice = new GroupMember("Alice", "alice@email.com");
        GroupMember bob = new GroupMember("Bob", "bob@email.com");
        GroupMember charlie = new GroupMember("Charlie", "charlie@email.com");
        BotObserver helpBot = new BotObserver("HelpBot", "help");

        // Demo 1: Adding observers
        System.out.println("\n1. ADDING OBSERVERS:");
        studyGroup.subscribe(alice);
        studyGroup.subscribe(bob);
        studyGroup.subscribe(charlie);
        studyGroup.subscribe(helpBot);

        // Demo 2: Notification to all observers
        System.out.println("\n2. BROADCASTING MESSAGES:");
        studyGroup.postMessage("Welcome everyone to our study group!");
        studyGroup.postMessage("Today we'll learn about design patterns");

        // Demo 3: Observer state changes
        System.out.println("\n3. OBSERVER STATE CHANGES:");
        bob.goOffline();
        studyGroup.postMessage("Bob missed this message because he's offline");

        // Demo 4: Removing observers
        System.out.println("\n4. REMOVING OBSERVERS:");
        studyGroup.unsubscribe(charlie);
        studyGroup.postMessage("Charlie won't see this message");

        // Demo 5: Bot-specific behavior
        System.out.println("\n5. BOT INTERACTION:");
        studyGroup.postMessage("I need help with Observer pattern");
        studyGroup.postMessage("This message won't trigger the bot");

        // Demo 6: Observer coming back online
        System.out.println("\n6. OBSERVER REJOINING:");
        bob.goOnline();
        studyGroup.postMessage("Bob is back and will see this!");

        // Summary
        System.out.printf("\nGroup '%s' has %d active members%n", 
            studyGroup.getGroupName(), studyGroup.getMemberCount());
    }
}

/* 
LEARNING OUTCOMES FROM THIS EXAMPLE:

1. OBSERVER INTERFACE: Defines contract that all observers must follow
2. SUBJECT INTERFACE: Defines how observables should behave
3. LOOSE COUPLING: Subject doesn't know specific observer types
4. POLYMORPHIC NOTIFICATION: Different observers react differently
5. DYNAMIC SUBSCRIPTION: Observers can join/leave at runtime
6. AUTOMATIC NOTIFICATION: Business actions trigger observer updates
7. MULTIPLE OBSERVER TYPES: Humans and bots can coexist

KEY OBSERVER PATTERN BENEFITS DEMONSTRATED:
- One-to-many dependency between objects
- Automatic notification when subject state changes
- Observers can be added/removed dynamically
- Subject and observers are loosely coupled
- Different observer types can coexist
*/
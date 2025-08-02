package factory.revision.revision_1;

enum PlayerType {
    HUMAN,
    AI;
}

// ISSUE 1: Missing getter methods - cannot access fields after creation
// ISSUE 2: No abstract methods defined - why is this abstract?
abstract class Player {
    private String name;
    private PlayerType playerType;

    public Player(String name, PlayerType playerType) {
        this.name = name;
        this.playerType = playerType;
    }

    @Override
    public String toString() {
        return "name: " + name + "\nplayerType: " + playerType;
    }
}

// ISSUE 3: Redundant parameter - PlayerType already known from class type
// ISSUE 4: No unique behavior - just a pass-through constructor
class Human extends Player {
    public Human(String name, PlayerType playerType) {
        super(name, playerType);
    }
}

// ISSUE 5: Same issues as Human class - no added value
class AI extends Player {
    public AI(String name, PlayerType playerType) {
        super(name, playerType);
    }
}

// CRITICAL ISSUE 6: Factory can return null - dangerous!
// ISSUE 7: No input validation for name parameter
// ISSUE 8: Not extensible - violates Open-Closed Principle
// ISSUE 9: Parameter redundancy - PlayerType passed twice
class PlayerFactory {
    public static Player getPlayerOfType(PlayerType playerType, String name) {
        switch (playerType) {
            case HUMAN -> {
                return new Human(name, PlayerType.HUMAN); // ISSUE 10: Redundant parameter
            }
            case AI -> {
                return new AI(name, PlayerType.AI); // ISSUE 11: Redundant parameter
            }
        }
        return null; // CRITICAL ISSUE 12: Null return can cause NullPointerException
    }
}

public class Factory_1 {
    public static void main(String[] args) {
        Player player1 = PlayerFactory.getPlayerOfType(PlayerType.HUMAN, "Amit Kumar");
        Player player2 = PlayerFactory.getPlayerOfType(PlayerType.AI, "OpenAI");
        System.out.println(player1.toString()); // ISSUE 13: No null check - could throw NPE
        System.out.println(player2.toString()); // ISSUE 14: No error handling
    }
}

package factory.revision.revision_1;

import java.util.Objects;

// FIX 1: Enhanced enum with behavior
enum PlayerTypeFixed {
    HUMAN("Human Player", 100),
    AI("AI Player", 1000);
    
    private final String description;
    private final int defaultSkillLevel;
    
    PlayerTypeFixed(String description, int defaultSkillLevel) {
        this.description = description;
        this.defaultSkillLevel = defaultSkillLevel;
    }
    
    public String getDescription() { return description; }
    public int getDefaultSkillLevel() { return defaultSkillLevel; }
}

// FIX 2: Proper abstract class with abstract methods and getters
abstract class PlayerFixed {
    private final String name;
    private final PlayerTypeFixed playerType;
    protected final int skillLevel;
    
    protected PlayerFixed(String name, PlayerTypeFixed playerType, int skillLevel) {
        this.name = Objects.requireNonNull(name, "Player name cannot be null");
        this.playerType = Objects.requireNonNull(playerType, "Player type cannot be null");
        this.skillLevel = skillLevel;
        
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be empty");
        }
    }
    
    // Getter methods
    public String getName() { return name; }
    public PlayerTypeFixed getPlayerType() { return playerType; }
    public int getSkillLevel() { return skillLevel; }
    
    // Abstract methods that subclasses must implement
    public abstract String makeMove(String gameState);
    public abstract void levelUp();
    public abstract String getSpecialAbility();
    
    @Override
    public String toString() {
        return String.format("%s{name='%s', type=%s, skill=%d, ability='%s'}", 
                getClass().getSimpleName(), name, playerType, skillLevel, getSpecialAbility());
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerFixed that = (PlayerFixed) o;
        return skillLevel == that.skillLevel &&
                Objects.equals(name, that.name) &&
                playerType == that.playerType;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, playerType, skillLevel);
    }
}

// FIX 3: Human class with unique behavior and no redundant parameters
class HumanFixed extends PlayerFixed {
    private int experience;
    
    // Constructor only takes name - PlayerType is implicit
    public HumanFixed(String name) {
        super(name, PlayerTypeFixed.HUMAN, PlayerTypeFixed.HUMAN.getDefaultSkillLevel());
        this.experience = 0;
    }
    
    // Custom constructor for advanced creation
    public HumanFixed(String name, int skillLevel) {
        super(name, PlayerTypeFixed.HUMAN, skillLevel);
        this.experience = skillLevel * 10; // Experience based on skill
    }
    
    @Override
    public String makeMove(String gameState) {
        return "Human player " + getName() + " analyzes situation: " + gameState;
    }
    
    @Override
    public void levelUp() {
        experience += 10;
        // Human players improve gradually
    }
    
    @Override
    public String getSpecialAbility() {
        return "Intuition and Creativity";
    }
    
    public int getExperience() { return experience; }
}

// FIX 4: AI class with unique behavior and characteristics
class AIFixed extends PlayerFixed {
    private final String algorithm;
    private int computationPower;
    
    public AIFixed(String name) {
        super(name, PlayerTypeFixed.AI, PlayerTypeFixed.AI.getDefaultSkillLevel());
        this.algorithm = "Neural Network";
        this.computationPower = 1000;
    }
    
    public AIFixed(String name, String algorithm, int computationPower) {
        super(name, PlayerTypeFixed.AI, computationPower / 10);
        this.algorithm = Objects.requireNonNull(algorithm, "Algorithm cannot be null");
        this.computationPower = computationPower;
    }
    
    @Override
    public String makeMove(String gameState) {
        return "AI " + getName() + " processes " + gameState + " using " + algorithm;
    }
    
    @Override
    public void levelUp() {
        computationPower *= 2; // AI doubles computation power
    }
    
    @Override
    public String getSpecialAbility() {
        return "Lightning-fast Calculations (" + algorithm + ")";
    }
    
    public String getAlgorithm() { return algorithm; }
    public int getComputationPower() { return computationPower; }
}

// FIX 5: Exception for invalid player creation
class InvalidPlayerException extends RuntimeException {
    public InvalidPlayerException(String message) {
        super(message);
    }
    
    public InvalidPlayerException(String message, Throwable cause) {
        super(message, cause);
    }
}

// FIX 6: Extensible factory using enum-based approach
class PlayerFactoryFixed {
    
    // Private constructor to prevent instantiation
    private PlayerFactoryFixed() {
        throw new AssertionError("Utility class should not be instantiated");
    }
    
    // FIX 7: Factory method that never returns null
    public static PlayerFixed createPlayer(PlayerTypeFixed type, String name) {
        Objects.requireNonNull(type, "Player type cannot be null");
        Objects.requireNonNull(name, "Player name cannot be null");
        
        if (name.trim().isEmpty()) {
            throw new InvalidPlayerException("Player name cannot be empty");
        }
        
        return switch (type) {
            case HUMAN -> new HumanFixed(name);
            case AI -> new AIFixed(name);
        };
    }
    
    // FIX 8: Overloaded factory methods for flexibility
    public static PlayerFixed createPlayer(PlayerTypeFixed type, String name, int skillLevel) {
        Objects.requireNonNull(type, "Player type cannot be null");
        
        return switch (type) {
            case HUMAN -> new HumanFixed(name, skillLevel);
            case AI -> new AIFixed(name, "Advanced Neural Network", skillLevel * 100);
        };
    }
    
    // FIX 9: Specialized factory methods
    public static HumanFixed createHumanPlayer(String name) {
        return new HumanFixed(name);
    }
    
    public static AIFixed createAIPlayer(String name, String algorithm) {
        return new AIFixed(name, algorithm, 1500);
    }
    
    // FIX 10: Factory method with validation
    public static PlayerFixed createPlayerSafely(String typeString, String name) {
        if (typeString == null || name == null) {
            throw new InvalidPlayerException("Type and name cannot be null");
        }
        
        try {
            PlayerTypeFixed type = PlayerTypeFixed.valueOf(typeString.toUpperCase());
            return createPlayer(type, name);
        } catch (IllegalArgumentException e) {
            throw new InvalidPlayerException("Invalid player type: " + typeString, e);
        }
    }
}

// FIX 11: Registry-based extensible factory (advanced approach)
interface PlayerCreator {
    PlayerFixed create(String name);
}

class ExtensiblePlayerFactory {
    // FIX: Use ConcurrentHashMap for thread safety
    private static final java.util.concurrent.ConcurrentHashMap<String, PlayerCreator> playerCreators = 
            new java.util.concurrent.ConcurrentHashMap<>();
    
    static {
        // Register default creators
        registerPlayerType("HUMAN", HumanFixed::new);
        registerPlayerType("AI", AIFixed::new);
    }
    
    // ✅ Thread-safe: ConcurrentHashMap allows safe concurrent access
    public static void registerPlayerType(String type, PlayerCreator creator) {
        playerCreators.put(type.toUpperCase(), creator);
    }

    // ✅ Thread-safe: Read operations on ConcurrentHashMap are thread-safe
    public static PlayerFixed createPlayer(String type, String name) {
        PlayerCreator creator = playerCreators.get(type.toUpperCase());
        if (creator == null) {
            throw new InvalidPlayerException("Unknown player type: " + type);
        }
        return creator.create(name);
    }
    
    public static java.util.Set<String> getSupportedTypes() {
        return java.util.Collections.unmodifiableSet(playerCreators.keySet());
    }
}

public class Factory_1_fix {
    public static void main(String[] args) {
        System.out.println("=== Factory Pattern - Fixed Implementation ===\n");
        
        try {
            // FIX 12: Safe factory usage with proper error handling
            PlayerFixed player1 = PlayerFactoryFixed.createPlayer(PlayerTypeFixed.HUMAN, "Amit Kumar");
            PlayerFixed player2 = PlayerFactoryFixed.createPlayer(PlayerTypeFixed.AI, "OpenAI");
            
            System.out.println("=== Basic Players ===");
            System.out.println(player1.toString());
            System.out.println(player2.toString());
            
            // Demonstrate unique behaviors
            System.out.println("\n=== Player Actions ===");
            System.out.println(player1.makeMove("Chess board with king in check"));
            System.out.println(player2.makeMove("Chess board with king in check"));
            
            // FIX 13: Demonstrate specialized factory methods
            System.out.println("\n=== Specialized Creation ===");
            HumanFixed advancedHuman = PlayerFactoryFixed.createHumanPlayer("Chess Master");
            AIFixed customAI = PlayerFactoryFixed.createAIPlayer("DeepBlue", "Minimax Algorithm");
            
            System.out.println(advancedHuman.toString());
            System.out.println(customAI.toString());
            
            // FIX 14: Demonstrate extensible factory
            System.out.println("\n=== Extensible Factory ===");
            System.out.println("Supported types: " + ExtensiblePlayerFactory.getSupportedTypes());
            
            PlayerFixed player3 = ExtensiblePlayerFactory.createPlayer("HUMAN", "John Doe");
            PlayerFixed player4 = ExtensiblePlayerFactory.createPlayer("AI", "AlphaGo");
            
            System.out.println(player3.toString());
            System.out.println(player4.toString());
            
            // FIX 15: Demonstrate validation and error handling
            System.out.println("\n=== Error Handling Demonstration ===");
            
            try {
                PlayerFactoryFixed.createPlayer(PlayerTypeFixed.HUMAN, ""); // Empty name
            } catch (InvalidPlayerException e) {
                System.out.println("Caught expected error: " + e.getMessage());
            }
            
            try {
                PlayerFactoryFixed.createPlayerSafely("INVALID_TYPE", "Test Player");
            } catch (InvalidPlayerException e) {
                System.out.println("Caught expected error: " + e.getMessage());
            }
            
            // FIX 16: Demonstrate level up functionality
            System.out.println("\n=== Player Development ===");
            System.out.println("Before level up: " + player1.getSkillLevel());
            player1.levelUp();
            System.out.println("After level up: " + ((HumanFixed)player1).getExperience());
            
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
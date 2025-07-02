package factory;

interface RoomType {
    int getRoomRate();
    String getRoomType();
}

class Economy implements RoomType {
    public int getRoomRate() { return 999; }
    public String getRoomType() { return "Economy"; }
}

class Deluxe implements RoomType {
    public int getRoomRate() { return 1999; }
    public String getRoomType() { return "Deluxe"; }
}

class Luxury implements RoomType {
    public int getRoomRate() { return 2999; }
    public String getRoomType() { return "Luxury"; }
}

// ✅ Factory class (stateless and focused)
class RoomFactory {
    public static RoomType createRoom(String type) {
        return switch (type) {
            case "Economy" -> new Economy();
            case "Deluxe" -> new Deluxe();
            case "Luxury" -> new Luxury();
            default -> throw new IllegalArgumentException("Invalid room type: " + type);
        };
    }
}

public class FactoryDemo1 {
    public static void main(String[] args) {
        RoomType room = RoomFactory.createRoom("Deluxe");
        System.out.println("Created: " + room.getRoomType() + " with cost: " + room.getRoomRate());

        room = RoomFactory.createRoom("Luxury");
        System.out.println("Created: " + room.getRoomType() + " with cost: " + room.getRoomRate());

        room = RoomFactory.createRoom("Economy");
        System.out.println("Created: " + room.getRoomType() + " with cost: " + room.getRoomRate());
    }
}

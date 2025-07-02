package factory;

abstract class Vehicle {
    int capacity;
    String name;
    public abstract int getCapacity();
    public abstract String getName();
}

class Bike extends Vehicle {
    public Bike(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public String getName() {
        return this.name;
    }
}

class Auto extends Vehicle {
    public Auto(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public String getName() {
        return this.name;
    }
}

class Car extends Vehicle {
    public Car(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public String getName() {
        return this.name;
    }
}

enum VehicleType {
    BIKE, AUTO, CAR;
}

class VehicleFactory {
    public static Vehicle getVehicle(VehicleType vehicleType, String name, int capacity) {
        switch (vehicleType) {
            case BIKE -> {
                return new Bike(name, capacity);
            }
            case AUTO ->  {
                return new Auto(name, capacity);
            }
            case CAR ->  {
                return new Car(name, capacity);
            }
        }
        return null;
    }
}

public class FactoryDemo2 {
    public static void main(String[] args) {
        Vehicle pulser = VehicleFactory.getVehicle(VehicleType.BIKE, "Pulser", 2);
        System.out.println(pulser.getName() + " has capacity of " + pulser.getCapacity());

        Vehicle maruti = VehicleFactory.getVehicle(VehicleType.CAR, "Maruti 800", 4);
        System.out.println(maruti.getName() + " has capacity of " + maruti.getCapacity());

        Vehicle auto = VehicleFactory.getVehicle(VehicleType.AUTO, "Tata Sumo", 3);
        System.out.println(auto.getName() + " has capacity of " + auto.getCapacity());
    }
}

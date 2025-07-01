// UML: https://tinyurl.com/22p65cb7

package strategy;

// Strategy interface defining the contract for different driving behaviors
interface DriveStrategy {
    public void drive();
}

// Concrete Strategy implementing normal driving behavior
class NormalDriveStrategy implements DriveStrategy {
    @Override
    public void drive() {
        System.out.println("Driving mode updated to NORMAL");
    }
}

// Concrete Strategy implementing sports driving behavior
class SportsDriveStrategy implements DriveStrategy {
    @Override
    public void drive() {
        System.out.println("Driving mode updated to SPORTS");
    }
}

// Concrete Strategy implementing off-road driving behavior
class OffRoadDriveStrategy implements DriveStrategy {
    @Override
    public void drive() {
        System.out.println("Driving mode updated to OFF-ROAD");
    }
}

// Context class that uses a DriveStrategy to execute the drive behavior
class Car {
    // Reference to a DriveStrategy
    DriveStrategy driveStrategy;

    // Constructor to set the DriveStrategy
    Car(DriveStrategy driveStrategy) {
        this.driveStrategy = driveStrategy;
    }

    // Method to execute the drive behavior using the current DriveStrategy
    public void drive() {
        driveStrategy.drive();
    }
}

// A specific type of Car that can have different driving behaviors
class BMW extends Car {
    // Constructor to set the DriveStrategy for BMW
    BMW(DriveStrategy driveStrategy) {
        super(driveStrategy);
    }
}

// Another specific type of Car that can have different driving behaviors
class Audi extends Car {
    // Constructor to set the DriveStrategy for Audi
    Audi(DriveStrategy driveStrategy) {
        super(driveStrategy);
    }
}

// Main class to demonstrate the Strategy Design Pattern
public class StrategyDemo1 {
    public static void main(String[] args) {

        // Create a BMW car with NormalDriveStrategy and execute drive
        BMW bmw = new BMW(new NormalDriveStrategy());
        bmw.drive();

        // Change BMW car to use SportsDriveStrategy and execute drive
        bmw = new BMW(new SportsDriveStrategy());
        bmw.drive();

        // Change BMW car to use OffRoadDriveStrategy and execute drive
        bmw = new BMW(new OffRoadDriveStrategy());
        bmw.drive();

        // Create an Audi car with OffRoadDriveStrategy and execute drive
        Audi audi = new Audi(new OffRoadDriveStrategy());
        audi.drive();
    }
}

/*
OUTPUT:
Driving mode updated to NORMAL
Driving mode updated to SPORTS
Driving mode updated to OFF-ROAD
Driving mode updated to OFF-ROAD
*/
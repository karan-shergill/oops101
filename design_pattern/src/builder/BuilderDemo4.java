package builder;

import java.util.Date;

abstract class Product {
    int skuId;
    Date createDate;
    String name;
    
    @Override
    public String toString() {
        return "Product{" +
                "skuId=" + skuId +
                ", createDate=" + createDate +
                ", name='" + name + '\'' +
                '}';
    }
}

class Grocery extends Product {
    Date expirationDate;
    boolean refrigerate;
    
    @Override
    public String toString() {
        return "Grocery{" +
                "skuId=" + skuId +
                ", createDate=" + createDate +
                ", name='" + name + '\'' +
                ", expirationDate=" + expirationDate +
                ", refrigerate=" + refrigerate +
                '}';
    }
}

class Electronics extends Product {
    int warrantyInMonths;
    boolean hasInbuildBattery;
    
    @Override
    public String toString() {
        return "Electronics{" +
                "skuId=" + skuId +
                ", createDate=" + createDate +
                ", name='" + name + '\'' +
                ", warrantyInMonths=" + warrantyInMonths +
                ", hasInbuildBattery=" + hasInbuildBattery +
                '}';
    }
}

class Furniture extends Product {
    String material;
    int averageAge;
    
    @Override
    public String toString() {
        return "Furniture{" +
                "skuId=" + skuId +
                ", createDate=" + createDate +
                ", name='" + name + '\'' +
                ", material='" + material + '\'' +
                ", averageAge=" + averageAge +
                '}';
    }
}

// Builder interface for Product
interface ProductBuilder {
    ProductBuilder setSkuId(int skuId);
    ProductBuilder setCreateDate(Date createDate);
    ProductBuilder setName(String name);
    Product build();
}

// Concrete Builder for Grocery
class GroceryBuilder implements ProductBuilder {
    private Grocery grocery;
    
    public GroceryBuilder() {
        this.grocery = new Grocery();
    }
    
    @Override
    public GroceryBuilder setSkuId(int skuId) {
        this.grocery.skuId = skuId;
        return this;
    }
    
    @Override
    public GroceryBuilder setCreateDate(Date createDate) {
        this.grocery.createDate = createDate;
        return this;
    }
    
    @Override
    public GroceryBuilder setName(String name) {
        this.grocery.name = name;
        return this;
    }
    
    public GroceryBuilder setExpirationDate(Date expirationDate) {
        this.grocery.expirationDate = expirationDate;
        return this;
    }
    
    public GroceryBuilder setRefrigerate(boolean refrigerate) {
        this.grocery.refrigerate = refrigerate;
        return this;
    }
    
    @Override
    public Grocery build() {
        return this.grocery;
    }
}

// Concrete Builder for Electronics
class ElectronicsBuilder implements ProductBuilder {
    private Electronics electronics;
    
    public ElectronicsBuilder() {
        this.electronics = new Electronics();
    }
    
    @Override
    public ElectronicsBuilder setSkuId(int skuId) {
        this.electronics.skuId = skuId;
        return this;
    }
    
    @Override
    public ElectronicsBuilder setCreateDate(Date createDate) {
        this.electronics.createDate = createDate;
        return this;
    }
    
    @Override
    public ElectronicsBuilder setName(String name) {
        this.electronics.name = name;
        return this;
    }
    
    public ElectronicsBuilder setWarrantyInMonths(int warrantyInMonths) {
        this.electronics.warrantyInMonths = warrantyInMonths;
        return this;
    }
    
    public ElectronicsBuilder setHasInbuildBattery(boolean hasInbuildBattery) {
        this.electronics.hasInbuildBattery = hasInbuildBattery;
        return this;
    }
    
    @Override
    public Electronics build() {
        return this.electronics;
    }
}

// Concrete Builder for Furniture
class FurnitureBuilder implements ProductBuilder {
    private Furniture furniture;
    
    public FurnitureBuilder() {
        this.furniture = new Furniture();
    }
    
    @Override
    public FurnitureBuilder setSkuId(int skuId) {
        this.furniture.skuId = skuId;
        return this;
    }
    
    @Override
    public FurnitureBuilder setCreateDate(Date createDate) {
        this.furniture.createDate = createDate;
        return this;
    }
    
    @Override
    public FurnitureBuilder setName(String name) {
        this.furniture.name = name;
        return this;
    }
    
    public FurnitureBuilder setMaterial(String material) {
        this.furniture.material = material;
        return this;
    }
    
    public FurnitureBuilder setAverageAge(int averageAge) {
        this.furniture.averageAge = averageAge;
        return this;
    }
    
    @Override
    public Furniture build() {
        return this.furniture;
    }
}

// Director class to orchestrate the building process
class ProductDirector {
    public Grocery createGroceryProduct(String name, int skuId, Date expirationDate, boolean refrigerate) {
        return new GroceryBuilder()
                .setSkuId(skuId)
                .setName(name)
                .setCreateDate(new Date())
                .setExpirationDate(expirationDate)
                .setRefrigerate(refrigerate)
                .build();
    }
    
    public Electronics createElectronicsProduct(String name, int skuId, int warranty, boolean hasBattery) {
        return new ElectronicsBuilder()
                .setSkuId(skuId)
                .setName(name)
                .setCreateDate(new Date())
                .setWarrantyInMonths(warranty)
                .setHasInbuildBattery(hasBattery)
                .build();
    }
    
    public Furniture createFurnitureProduct(String name, int skuId, String material, int averageAge) {
        return new FurnitureBuilder()
                .setSkuId(skuId)
                .setName(name)
                .setCreateDate(new Date())
                .setMaterial(material)
                .setAverageAge(averageAge)
                .build();
    }
}

public class BuilderDemo4 {
    public static void main(String[] args) {
        System.out.println("=== Builder Design Pattern Demo ===\n");
        
        // Using builders directly (Method Chaining)
        System.out.println("1. Using Builders Directly:");
        
        // Create a grocery product
        Grocery milk = new GroceryBuilder()
                .setSkuId(101)
                .setName("Organic Milk")
                .setCreateDate(new Date())
                .setExpirationDate(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 7 days from now
                .setRefrigerate(true)
                .build();
        
        // Create an electronics product
        Electronics laptop = new ElectronicsBuilder()
                .setSkuId(201)
                .setName("Gaming Laptop")
                .setCreateDate(new Date())
                .setWarrantyInMonths(24)
                .setHasInbuildBattery(true)
                .build();
        
        // Create a furniture product
        Furniture chair = new FurnitureBuilder()
                .setSkuId(301)
                .setName("Office Chair")
                .setCreateDate(new Date())
                .setMaterial("Leather")
                .setAverageAge(0)
                .build();
        
        System.out.println("Grocery: " + milk);
        System.out.println("Electronics: " + laptop);
        System.out.println("Furniture: " + chair);
        
        // Using Director for more complex construction
        System.out.println("\n2. Using Director for Orchestrated Construction:");
        
        ProductDirector director = new ProductDirector();
        
        Grocery bread = director.createGroceryProduct(
                "Whole Wheat Bread", 
                102, 
                new Date(System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000), // 3 days from now
                false
        );
        
        Electronics smartphone = director.createElectronicsProduct(
                "Smartphone", 
                202, 
                12, 
                true
        );
        
        Furniture table = director.createFurnitureProduct(
                "Dining Table", 
                302, 
                "Oak Wood", 
                0
        );
        
        System.out.println("Grocery (via Director): " + bread);
        System.out.println("Electronics (via Director): " + smartphone);
        System.out.println("Furniture (via Director): " + table);
        
        // Demonstrating partial construction
        System.out.println("\n3. Demonstrating Partial Construction:");
        
        Electronics partialElectronics = new ElectronicsBuilder()
                .setSkuId(203)
                .setName("Tablet")
                .setWarrantyInMonths(18)
                // Notice: not setting createDate or hasInbuildBattery
                .build();
        
        System.out.println("Partial Electronics: " + partialElectronics);
    }
}


/*
OUTPUT:

=== Builder Design Pattern Demo ===

1. Using Builders Directly:
Grocery: Grocery{skuId=101, createDate=Wed Jul 30 21:35:14 IST 2025, name='Organic Milk', expirationDate=Wed Aug 06 21:35:14 IST 2025, refrigerate=true}
Electronics: Electronics{skuId=201, createDate=Wed Jul 30 21:35:14 IST 2025, name='Gaming Laptop', warrantyInMonths=24, hasInbuildBattery=true}
Furniture: Furniture{skuId=301, createDate=Wed Jul 30 21:35:14 IST 2025, name='Office Chair', material='Leather', averageAge=0}

2. Using Director for Orchestrated Construction:
Grocery (via Director): Grocery{skuId=102, createDate=Wed Jul 30 21:35:14 IST 2025, name='Whole Wheat Bread', expirationDate=Sat Aug 02 21:35:14 IST 2025, refrigerate=false}
Electronics (via Director): Electronics{skuId=202, createDate=Wed Jul 30 21:35:14 IST 2025, name='Smartphone', warrantyInMonths=12, hasInbuildBattery=true}
Furniture (via Director): Furniture{skuId=302, createDate=Wed Jul 30 21:35:14 IST 2025, name='Dining Table', material='Oak Wood', averageAge=0}

3. Demonstrating Partial Construction:
Partial Electronics: Electronics{skuId=203, createDate=null, name='Tablet', warrantyInMonths=18, hasInbuildBattery=false}
*/
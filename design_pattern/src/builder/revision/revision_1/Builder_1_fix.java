package builder.revision.revision_1;

import java.util.Date;
import java.util.Objects;

// FIX 1: Product11 with proper access level (package-private to avoid file conflict)
// FIX 2: Added getter methods for all fields
// FIX 3: Implemented proper toString() method
// FIX 4: Made the product immutable after construction (final fields, no setters)
class Product11 {
    private final String sku;
    private final long id;
    private final String name;
    private final Date expiration;
    private final boolean needRefrigeration;

    // Package-private constructor - only builder can create instances
    Product11(String sku, long id, String name, Date expiration, boolean needRefrigeration) {
        this.sku = sku;
        this.id = id;
        this.name = name;
        // Defensive copy for mutable Date object
        this.expiration = expiration != null ? new Date(expiration.getTime()) : null;
        this.needRefrigeration = needRefrigeration;
    }

    // Getter methods
    public String getSku() {
        return sku;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getExpiration() {
        // Return defensive copy to maintain immutability
        return expiration != null ? new Date(expiration.getTime()) : null;
    }

    public boolean isNeedRefrigeration() {
        return needRefrigeration;
    }

    @Override
    public String toString() {
        return "Product11{" +
                "sku='" + sku + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", expiration=" + expiration +
                ", needRefrigeration=" + needRefrigeration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product11 product11 = (Product11) o;
        return id == product11.id &&
                needRefrigeration == product11.needRefrigeration &&
                Objects.equals(sku, product11.sku) &&
                Objects.equals(name, product11.name) &&
                Objects.equals(expiration, product11.expiration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sku, id, name, expiration, needRefrigeration);
    }
}

// FIX 5: Improved builder with validation and proper object creation
class ProductBuilder11 {
    private String sku;
    private long id;
    private String name;
    private Date expiration;
    private boolean needRefrigeration;

    public ProductBuilder11() {
        // Initialize with default values
        this.id = 0;
        this.needRefrigeration = false;
    }

    public ProductBuilder11 setSku(String sku) {
        this.sku = sku;
        return this;
    }

    public ProductBuilder11 setId(long id) {
        this.id = id;
        return this;
    }

    public ProductBuilder11 setName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder11 setExpiration(Date expiration) {
        this.expiration = expiration;
        return this;
    }

    public ProductBuilder11 setNeedRefrigeration(boolean needRefrigeration) {
        this.needRefrigeration = needRefrigeration;
        return this;
    }

    // FIX 6: Added validation method
    private void validate() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalStateException("Product name is required");
        }
        if (id <= 0) {
            throw new IllegalStateException("Product ID must be positive");
        }
    }

    // FIX 7: Build method creates new instance every time
    // FIX 8: Builder can be reused after build()
    public Product11 build() {
        validate(); // Ensure required fields are set
        
        Product11 product = new Product11(sku, id, name, expiration, needRefrigeration);
        
        // Optional: Reset builder for reuse (uncomment if needed)
        // reset();
        
        return product;
    }

    // Optional reset method for builder reuse
    public ProductBuilder11 reset() {
        this.sku = null;
        this.id = 0;
        this.name = null;
        this.expiration = null;
        this.needRefrigeration = false;
        return this;
    }
}

public class Builder_1_fix {
    public static void main(String[] args) {
        System.out.println("DP Builder_1_fix - Corrected Implementation");

        // Creating a product with the fixed builder
        Product11 p1 = new ProductBuilder11()
                .setId(1)
                .setName("Milk")
                .setSku("MLK001")
                .setExpiration(new Date())
                .setNeedRefrigeration(true)
                .build();

        System.out.println("Product 1: " + p1.toString());

        // Demonstrating builder reusability
        ProductBuilder11 builder = new ProductBuilder11();
        
        Product11 p2 = builder
                .setId(2)
                .setName("Bread")
                .setSku("BRD001")
                .setNeedRefrigeration(false)
                .build();

        Product11 p3 = builder
                .reset()  // Reset for reuse
                .setId(3)
                .setName("Cheese")
//                .setSku("CHS001")
//                .setExpiration(new Date())
//                .setNeedRefrigeration(true)
                .build();

        System.out.println("Product 2: " + p2.toString());
        System.out.println("Product 3: " + p3.toString());

        // Demonstrating immutability - these are different objects
        System.out.println("p1 == p2: " + (p1 == p2)); // false
        System.out.println("p2 == p3: " + (p2 == p3)); // false

        // Demonstrating validation
        try {
            new ProductBuilder11()
                    .setName("") // Invalid empty name
                    .build();
        } catch (IllegalStateException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }
}
package builder.revision.revision_1;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

// FIX 1: Made Product2 a concrete base class with proper toString and getters
// FIX 2: Added immutable fields with final modifiers
// FIX 3: Constructor-based initialization for immutability
abstract class Product22 {
    private final String sku;
    private final long id;
    private final String name;

    // Protected constructor for inheritance
    protected Product22(String sku, long id, String name) {
        this.sku = sku;
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product22 product2 = (Product22) o;
        return id == product2.id &&
                Objects.equals(sku, product2.sku) &&
                Objects.equals(name, product2.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sku, id, name);
    }

    // Abstract method for child classes to implement specific toString behavior
    public abstract String getProductDetails();

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "sku='" + sku + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                getProductDetails() +
                '}';
    }
}

// FIX 4: Immutable Milk class with proper constructor and getters
class Milk extends Product22 {
    private final LocalDate expiration;
    private final boolean needRefrigeration;

    // Package-private constructor for builder access
    Milk(String sku, long id, String name, LocalDate expiration, boolean needRefrigeration) {
        super(sku, id, name);
        this.expiration = expiration;
        this.needRefrigeration = needRefrigeration;
    }

    public LocalDate getExpiration() {
        return expiration;
    }

    public boolean isNeedRefrigeration() {
        return needRefrigeration;
    }

    @Override
    public String getProductDetails() {
        return ", expiration=" + expiration +
                ", needRefrigeration=" + needRefrigeration;
    }
}

// FIX 5: Immutable Masala class with proper constructor and getters
class Masala extends Product22 {
    private final Date bestUpTo;
    private final String origin;

    // Package-private constructor for builder access
    Masala(String sku, long id, String name, Date bestUpTo, String origin) {
        super(sku, id, name);
        // Defensive copy for mutable Date
        this.bestUpTo = bestUpTo != null ? new Date(bestUpTo.getTime()) : null;
        this.origin = origin;
    }

    public Date getBestUpTo() {
        // Return defensive copy
        return bestUpTo != null ? new Date(bestUpTo.getTime()) : null;
    }

    public String getOrigin() {
        return origin;
    }

    @Override
    public String getProductDetails() {
        return ", bestUpTo=" + bestUpTo +
                ", origin='" + origin + '\'';
    }
}

// FIX 6: Generic builder interface to reduce code duplication
interface ProductBuilder<T extends Product22> {
    T build();
    ProductBuilder<T> setSku(String sku);
    ProductBuilder<T> setId(long id);
    ProductBuilder<T> setName(String name);
}

// FIX 7: Improved MilkBuilder with validation and proper object creation
class MilkBuilder implements ProductBuilder<Milk> {
    private String sku;
    private long id;
    private String name;
    private LocalDate expiration;
    private boolean needRefrigeration;

    public MilkBuilder() {
        // Initialize with default values
        this.id = 0;
        this.needRefrigeration = false;
    }

    @Override
    public MilkBuilder setSku(String sku) {
        this.sku = sku;
        return this;
    }

    @Override
    public MilkBuilder setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public MilkBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public MilkBuilder setExpiration(LocalDate expiration) {
        this.expiration = expiration;
        return this;
    }

    public MilkBuilder setNeedRefrigeration(boolean needRefrigeration) {
        this.needRefrigeration = needRefrigeration;
        return this;
    }

    // Validation method
    private void validate() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalStateException("Milk name is required");
        }
        if (id <= 0) {
            throw new IllegalStateException("Milk ID must be positive");
        }
        if (expiration == null) {
            throw new IllegalStateException("Milk expiration date is required");
        }
        if (expiration.isBefore(LocalDate.now())) {
            throw new IllegalStateException("Milk expiration date cannot be in the past");
        }
    }

    @Override
    public Milk build() {
        validate();
        return new Milk(sku, id, name, expiration, needRefrigeration);
    }

    // Reset method for builder reuse
    public MilkBuilder reset() {
        this.sku = null;
        this.id = 0;
        this.name = null;
        this.expiration = null;
        this.needRefrigeration = false;
        return this;
    }
}

// FIX 8: Improved MasalaBuilder with validation and proper object creation
class MasalaBuilder implements ProductBuilder<Masala> {
    private String sku;
    private long id;
    private String name;
    private Date bestUpTo;
    private String origin;

    public MasalaBuilder() {
        // Initialize with default values
        this.id = 0;
    }

    @Override
    public MasalaBuilder setSku(String sku) {
        this.sku = sku;
        return this;
    }

    @Override
    public MasalaBuilder setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public MasalaBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public MasalaBuilder setBestUpTo(Date bestUpTo) {
        this.bestUpTo = bestUpTo;
        return this;
    }

    public MasalaBuilder setOrigin(String origin) {
        this.origin = origin;
        return this;
    }

    // Validation method
    private void validate() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalStateException("Masala name is required");
        }
        if (id <= 0) {
            throw new IllegalStateException("Masala ID must be positive");
        }
        if (origin == null || origin.trim().isEmpty()) {
            throw new IllegalStateException("Masala origin is required");
        }
    }

    @Override
    public Masala build() {
        validate();
        return new Masala(sku, id, name, bestUpTo, origin);
    }

    // Reset method for builder reuse
    public MasalaBuilder reset() {
        this.sku = null;
        this.id = 0;
        this.name = null;
        this.bestUpTo = null;
        this.origin = null;
        return this;
    }
}

public class Builder_2_fix {
    public static void main(String[] args) {
        System.out.println("DP Builder_2_fix - Corrected Implementation");

        // Creating Milk product with proper builder
        Milk amul = new MilkBuilder()
                .setSku("A001")
                .setId(12345L)
                .setName("Amul Full Toned")
                .setExpiration(LocalDate.now().plusDays(7))
                .setNeedRefrigeration(true)
                .build();
        System.out.println("Milk Product: " + amul.toString());

        // Creating Masala product with proper builder
        Masala catchMasala = new MasalaBuilder()
                .setSku("B0008")
                .setId(67890L)
                .setName("Catch Masala")
                .setOrigin("Rajasthan")
                .setBestUpTo(new Date(System.currentTimeMillis() + 365L * 24 * 60 * 60 * 1000)) // 1 year from now
                .build();
        System.out.println("Masala Product: " + catchMasala.toString());

        // Demonstrating builder reusability
        MilkBuilder milkBuilder = new MilkBuilder();
        
        Milk milk2 = milkBuilder
                .setId(54321L)
                .setName("Mother Dairy")
                .setSku("MD001")
                .setExpiration(LocalDate.now().plusDays(5))
                .setNeedRefrigeration(true)
                .build();

        Milk milk3 = milkBuilder
                .reset()
                .setId(98765L)
                .setName("Nestle A+")
                .setSku("NES001")
                .setExpiration(LocalDate.now().plusDays(10))
                .setNeedRefrigeration(true)
                .build();

        System.out.println("Milk 2: " + milk2.toString());
        System.out.println("Milk 3: " + milk3.toString());

        // Demonstrating immutability
        System.out.println("amul == milk2: " + (amul == milk2)); // false
        System.out.println("milk2 == milk3: " + (milk2 == milk3)); // false

        // Demonstrating validation
        try {
            new MilkBuilder()
                    .setName("") // Invalid empty name
                    .build();
        } catch (IllegalStateException e) {
            System.out.println("Milk validation error: " + e.getMessage());
        }

        try {
            new MasalaBuilder()
                    .setId(123L)
                    .setName("Test Masala")
                    .setOrigin("") // Invalid empty origin
                    .build();
        } catch (IllegalStateException e) {
            System.out.println("Masala validation error: " + e.getMessage());
        }

        // Demonstrating getter methods
        System.out.println("Amul expiration: " + amul.getExpiration());
        System.out.println("Catch Masala origin: " + catchMasala.getOrigin());
        System.out.println("Amul needs refrigeration: " + amul.isNeedRefrigeration());
    }
}
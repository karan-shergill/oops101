package builder.revision.revision_1;

import java.time.LocalDate;
import java.util.Date;

// ISSUE 1: Abstract class Product2 has no abstract methods - should be concrete or have abstract methods
// ISSUE 2: Missing getter methods - cannot access values after construction
// ISSUE 3: No toString() override - will print unhelpful object reference
// ISSUE 4: Fields are mutable after construction
abstract class Product2 {
    private String sku;
    private long id;
    private String name;

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}

// ISSUE 5: Missing toString() implementation
// ISSUE 6: Missing getter methods for Milk-specific fields
// ISSUE 7: Fields remain mutable after construction
class Milk1 extends Product2 {
    private LocalDate expiration;
    private boolean needRefrigeration;

    public void setExpiration(LocalDate expiration) {
        this.expiration = expiration;
    }

    public void setNeedRefrigeration(boolean needRefrigeration) {
        this.needRefrigeration = needRefrigeration;
    }
}

// ISSUE 8: Missing toString() implementation
// ISSUE 9: Missing getter methods for Masala-specific fields
// ISSUE 10: Fields remain mutable after construction
class Masala1 extends Product2 {
    private Date bestUpTo;
    private String origin;

    public void setBestUpTo(Date bestUpTo) {
        this.bestUpTo = bestUpTo;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}

// CRITICAL ISSUE 11: Same object reference problem - build() returns same instance
// ISSUE 12: No validation of required fields
// ISSUE 13: Builder cannot be reused
// ISSUE 14: Code duplication with MasalaBuilder
class MilkBuilder1 {
    Milk1 milk;

    public MilkBuilder1() {
        this.milk = new Milk1();
    }

    public MilkBuilder1 setSku (String sku) {
        this.milk.setSku(sku);
        return this;
    }

    public MilkBuilder1 setId (long id) {
        this.milk.setId(id);
        return this;
    }

    public MilkBuilder1 setName (String name) {
        this.milk.setName(name);
        return this;
    }

    public MilkBuilder1 setExpiration (LocalDate expiration) {
        this.milk.setExpiration(expiration);
        return this;
    }

    public MilkBuilder1 setNeedRefrigeration (boolean needRefrigeration) {
        this.milk.setNeedRefrigeration(needRefrigeration);
        return this;
    }

    // CRITICAL ISSUE 15: Returns same object reference - milk can be modified after build()
    public Milk1 build() {
        return milk; // Should return new instance each time
    }
}

// CRITICAL ISSUE 16: Same object reference problem as MilkBuilder
// ISSUE 17: Code duplication - similar structure to MilkBuilder
// ISSUE 18: No validation or builder reusability
class MasalaBuilder1 {
    Masala1 masala;

    public MasalaBuilder1() {
        this.masala = new Masala1();
    }

    public MasalaBuilder1 setSku (String sku) {
        this.masala.setSku(sku);
        return this;
    }

    public MasalaBuilder1 setId (long id) {
        this.masala.setId(id);
        return this;
    }

    public MasalaBuilder1 setName (String name) {
        this.masala.setName(name);
        return this;
    }

    public MasalaBuilder1 setBestUpTo (Date bestUpTo) {
        this.masala.setBestUpTo(bestUpTo);
        return this;
    }

    public MasalaBuilder1 setOrigin (String origin) {
        this.masala.setOrigin(origin);
        return this;
    }

    // CRITICAL ISSUE 19: Same object reference problem as MilkBuilder
    public Masala1 build() {
        return masala; // Should return new instance each time
    }
}

public class Builder_2 {
    public static void main(String[] args) {
        System.out.println("DP Builder_2");

        Milk1 amul = new MilkBuilder1()
                .setSku("A001")
                .setId(12345L)
                .setName("Amul Full Toned")
                .setExpiration(LocalDate.now().plusDays(7))
                .setNeedRefrigeration(true)
                .build();
        System.out.println(amul.toString()); // ISSUE 20: Will print object reference, not meaningful data

        Masala1 catchMasala = new MasalaBuilder1()
                .setSku("B0008")
                .setName("Catch Masala")
                .setOrigin("Rajasthan")
                .build();
        System.out.println(catchMasala.toString()); // ISSUE 21: Will print object reference, not meaningful data
    }
}

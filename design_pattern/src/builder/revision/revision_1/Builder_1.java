package builder.revision.revision_1;

import java.util.Date;

// ISSUE 1: Product1 should be public for better accessibility
// ISSUE 2: Missing getter methods - cannot access values after construction
// ISSUE 3: No toString() override - will print unhelpful object reference
class Product1 {
    private String sku;
    private long id;
    private String name;
    private Date expiration;
    private boolean needRefrigeration;

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public void setNeedRefrigeration(boolean needRefrigeration) {
        this.needRefrigeration = needRefrigeration;
    }
}

 class ProductBuilder1 {
    private Product1 product;

     public ProductBuilder1() {
         this.product = new Product1();
     }
     
     // ISSUE 4: No validation - builder doesn't ensure required fields are set
     // ISSUE 5: Builder reusability problem - same product instance is reused

     public ProductBuilder1 setSku(String sku) {
         this.product.setSku(sku);
         return this;
     }

     public ProductBuilder1 setId(long id) {
         this.product.setId(id);
         return this;
     }

     public ProductBuilder1 setExpiration(Date expiration) {
         this.product.setExpiration(expiration);
         return this;
     }

     public ProductBuilder1 setName(String name) {
         this.product.setName(name);
         return this;
     }

     public ProductBuilder1 setNeedRefrigeration(boolean needRefrigeration) {
         this.product.setNeedRefrigeration(needRefrigeration);
         return this;
     }

     // CRITICAL ISSUE 6: Returns same object reference - product can be modified after build()
     // CRITICAL ISSUE 7: Builder cannot be reused - all builds return same object instance
     public Product1 build() {
         return this.product; // Should return a new instance or defensive copy
     }
 }

public class Builder_1 {
    public static void main(String[] args) {
        System.out.println("DP Builder_1");

        Product1 p1 = new ProductBuilder1()
                .setName("Milk")
                .setExpiration(new Date())
                .setNeedRefrigeration(true)
                .build();

        System.out.println(p1.toString()); // ISSUE 8: Will print object reference, not meaningful data
    }
}

package strategy.revision.revision_1;

// ✅ GOOD: Proper Strategy interface - defines algorithm family
// LEARNING POINT 1: Could include more methods like validate(), getPaymentType()
interface PaymentStrategy {
    boolean pay(int amount);
}

// ✅ GOOD: Concrete Strategy implementation
// LEARNING POINT 2: Missing input validation, getter methods, toString()
// LEARNING POINT 3: Always returns true - no realistic failure scenarios
class CreditCard implements PaymentStrategy {
    private String cardNo;
    private String name;

    public CreditCard(String cardNo, String name) {
        this.cardNo = cardNo;
        this.name = name;
    }

    @Override
    public boolean pay(int amount) {
        System.out.println("Amount " + amount + " payed via credit card"); // TYPO: "payed" -> "paid"
        return true; // LEARNING POINT 4: Should simulate real validation logic
    }
}

// LEARNING POINT 5: Same issues as CreditCard - pattern is consistent but basic
class UPI implements PaymentStrategy {
    private String  upiId;

    public UPI(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public boolean pay(int amount) {
        System.out.println("Amount " + amount + " payed via UPI"); // Same typo: "payed" -> "paid"
        return true; // LEARNING POINT 6: Each strategy should have unique logic
    }
}

// LEARNING POINT 7: Good - demonstrates Strategy pattern extensibility
class Crypto implements PaymentStrategy {
    private String transactionID;

    public Crypto(String transactionID) {
        this.transactionID = transactionID;
    }

    @Override
    public boolean pay(int amount) {
        System.out.println("Amount " + amount + " payed via Crypto"); // Same typo
        return true; // LEARNING POINT 8: Could simulate different crypto behaviors
    }
}

// ✅ GOOD: Context class that uses Strategy pattern
// LEARNING POINT 9: Could be named "PaymentProcessor" for clarity
// LEARNING POINT 10: Missing strategy switching, validation, private fields
class OrderPlacement {
    PaymentStrategy paymentStrategy; // LEARNING POINT 11: Should be private

    public OrderPlacement(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy; // LEARNING POINT 12: No null validation
    }

    public boolean pay(int amount) {
        return paymentStrategy.pay(amount); // LEARNING POINT 13: Direct delegation - good!
    }
    // LEARNING POINT 14: Missing setPaymentStrategy() for runtime strategy switching
}

public class Strategy_1 {
    public static void main(String[] args) {
        // ✅ GOOD: Demonstrates strategy pattern usage
        // LEARNING POINT 15: Could show runtime strategy switching in same context
        OrderPlacement orderPlacement1 = new OrderPlacement(new CreditCard("2313131313213", "123"));
        orderPlacement1.pay(10000);

        OrderPlacement orderPlacement2 = new OrderPlacement(new UPI("wdwdhw23@ybl"));
        orderPlacement2.pay(1000);

        OrderPlacement orderPlacement3 = new OrderPlacement(new Crypto("qeqwe.1231.qwqwe.1231"));
        orderPlacement3.pay(1231231);
        
        // LEARNING POINT 16: Missing demonstration of error handling, validation
        // LEARNING POINT 17: Could show polymorphic behavior with array/list
    }
}

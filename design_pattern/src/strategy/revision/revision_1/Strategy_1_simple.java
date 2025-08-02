package strategy.revision.revision_1;

// SIMPLIFIED Strategy Pattern for Learning Basic Concepts

// 1. STRATEGY INTERFACE - Defines the algorithm family
interface PaymentMethod {
    boolean processPayment(double amount);
    String getPaymentType();
    double calculateFee(double amount);
}

// 2. CONCRETE STRATEGIES - Different algorithm implementations
class CreditCardPayment implements PaymentMethod {
    private String cardNumber;
    private String holderName;

    public CreditCardPayment(String cardNumber, String holderName) {
        this.cardNumber = cardNumber;
        this.holderName = holderName;
    }

    @Override
    public boolean processPayment(double amount) {
        if (amount <= 0) return false;
        
        System.out.printf("Processing $%.2f via Credit Card ending in %s%n", 
            amount, cardNumber.substring(cardNumber.length() - 4));
        
        // Simulate processing time
        try { Thread.sleep(100); } catch (InterruptedException e) {}
        
        return amount <= 50000; // Credit card limit
    }

    @Override
    public String getPaymentType() {
        return "Credit Card";
    }

    @Override
    public double calculateFee(double amount) {
        return amount * 0.025; // 2.5% fee
    }
}

class UPIPayment implements PaymentMethod {
    private String upiId;

    public UPIPayment(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public boolean processPayment(double amount) {
        if (amount <= 0) return false;
        
        System.out.printf("Processing $%.2f via UPI ID: %s%n", amount, upiId);
        
        // Simulate processing time
        try { Thread.sleep(50); } catch (InterruptedException e) {}
        
        return amount <= 5000; // UPI daily limit
    }

    @Override
    public String getPaymentType() {
        return "UPI";
    }

    @Override
    public double calculateFee(double amount) {
        return amount <= 100 ? 0 : amount * 0.01; // Free under $100, then 1%
    }
}

class CryptoPayment implements PaymentMethod {
    private String walletAddress;

    public CryptoPayment(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    @Override
    public boolean processPayment(double amount) {
        if (amount < 10) return false; // Minimum $10 for crypto
        
        System.out.printf("Processing $%.2f via Crypto wallet: %s...%s%n", 
            amount, 
            walletAddress.substring(0, 4), 
            walletAddress.substring(walletAddress.length() - 4));
        
        // Simulate blockchain confirmation time
        try { Thread.sleep(200); } catch (InterruptedException e) {}
        
        return true; // No upper limit for crypto
    }

    @Override
    public String getPaymentType() {
        return "Cryptocurrency";
    }

    @Override
    public double calculateFee(double amount) {
        return 5.0; // Fixed $5 network fee
    }
}

// 3. CONTEXT CLASS - Uses the strategy
class PaymentGateway {
    private PaymentMethod paymentMethod;

    // Constructor injection
    public PaymentGateway(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Runtime strategy switching
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        System.out.println("Payment method changed to: " + paymentMethod.getPaymentType());
    }

    // Main business method that uses the strategy
    public boolean makePayment(double amount) {
        System.out.println("\n--- Payment Processing ---");
        System.out.println("Method: " + paymentMethod.getPaymentType());
        System.out.println("Amount: $" + amount);
        
        double fee = paymentMethod.calculateFee(amount);
        double total = amount + fee;
        System.out.printf("Fee: $%.2f | Total: $%.2f%n", fee, total);
        
        boolean success = paymentMethod.processPayment(amount);
        System.out.println("Status: " + (success ? "SUCCESS" : "FAILED"));
        
        return success;
    }

    public PaymentMethod getCurrentPaymentMethod() {
        return paymentMethod;
    }
}

// 4. DEMONSTRATION
public class Strategy_1_simple {
    public static void main(String[] args) {
        System.out.println("=== Simple Strategy Pattern Demo ===");

        // Create different payment strategies
        PaymentMethod creditCard = new CreditCardPayment("4532123456789012", "John Doe");
        PaymentMethod upi = new UPIPayment("john@upi");
        PaymentMethod crypto = new CryptoPayment("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa");

        // Create context with initial strategy
        PaymentGateway gateway = new PaymentGateway(creditCard);

        // Demo 1: Using different strategies
        System.out.println("\n1. USING DIFFERENT PAYMENT STRATEGIES:");
        gateway.makePayment(100.0);

        gateway.setPaymentMethod(upi);
        gateway.makePayment(100.0);

        gateway.setPaymentMethod(crypto);
        gateway.makePayment(100.0);

        // Demo 2: Testing limits and edge cases
        System.out.println("\n2. TESTING PAYMENT LIMITS:");
        
        gateway.setPaymentMethod(upi);
        gateway.makePayment(6000.0); // Exceeds UPI limit

        gateway.setPaymentMethod(crypto);
        gateway.makePayment(5.0); // Below crypto minimum

        // Demo 3: Fee comparison
        System.out.println("\n3. FEE COMPARISON FOR $500:");
        PaymentMethod[] methods = {creditCard, upi, crypto};
        double testAmount = 500.0;

        for (PaymentMethod method : methods) {
            double fee = method.calculateFee(testAmount);
            System.out.printf("%s: Fee = $%.2f%n", method.getPaymentType(), fee);
        }

        // Demo 4: Polymorphic usage
        System.out.println("\n4. POLYMORPHIC PAYMENT PROCESSING:");
        demonstratePolymorphism();
    }

    // Shows polymorphic behavior - core Strategy pattern benefit
    private static void demonstratePolymorphism() {
        PaymentMethod[] paymentMethods = {
            new CreditCardPayment("5555444433332222", "Jane Smith"),
            new UPIPayment("jane@phonepe"),
            new CryptoPayment("3J98t1WpEZ73CNmQviecrnyiWrnqRhWNLy")
        };

        PaymentGateway gateway = new PaymentGateway(paymentMethods[0]);
        
        // Same method call, different behavior based on strategy
        for (PaymentMethod method : paymentMethods) {
            gateway.setPaymentMethod(method);
            gateway.makePayment(250.0);
        }
    }
}

/* 
LEARNING OUTCOMES FROM THIS EXAMPLE:

1. STRATEGY INTERFACE: Defines common methods all strategies must implement
2. CONCRETE STRATEGIES: Each has different behavior for same interface
3. CONTEXT CLASS: Delegates to current strategy, allows runtime switching
4. POLYMORPHISM: Same method call, different behavior based on strategy
5. RUNTIME FLEXIBILITY: Can change algorithms without changing client code
6. OPEN-CLOSED PRINCIPLE: Easy to add new payment methods without modifying existing code

KEY STRATEGY PATTERN BENEFITS DEMONSTRATED:
- Eliminates conditional statements (no if/else for payment types)
- Each algorithm is encapsulated in its own class
- Algorithms are interchangeable at runtime
- Easy to test each algorithm independently
- Easy to add new algorithms without changing existing code
*/
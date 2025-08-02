package strategy.revision.revision_1;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

// LEARNING ENHANCEMENT 1: More comprehensive Strategy interface
interface PaymentStrategyFixed {
    PaymentResult processPayment(BigDecimal amount);
    boolean validate();
    String getPaymentType();
    BigDecimal getTransactionFee(BigDecimal amount);
    boolean supportsAmount(BigDecimal amount);
}

// LEARNING ENHANCEMENT 2: Result object instead of just boolean
class PaymentResult {
    private final boolean successful;
    private final String transactionId;
    private final String message;
    private final LocalDateTime timestamp;
    private final BigDecimal finalAmount;

    public PaymentResult(boolean successful, String transactionId, String message, BigDecimal finalAmount) {
        this.successful = successful;
        this.transactionId = transactionId;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.finalAmount = finalAmount;
    }

    // Getters
    public boolean isSuccessful() { return successful; }
    public String getTransactionId() { return transactionId; }
    public String getMessage() { return message; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public BigDecimal getFinalAmount() { return finalAmount; }

    @Override
    public String toString() {
        return String.format("PaymentResult{successful=%s, transactionId='%s', message='%s', amount=%s, time=%s}",
                successful, transactionId, message, finalAmount, timestamp);
    }
}

// LEARNING ENHANCEMENT 3: Abstract base class for common behavior
abstract class BasePaymentStrategy implements PaymentStrategyFixed {
    protected final Random random = new Random(); // For simulating real-world variability
    
    protected String generateTransactionId() {
        return getPaymentType().toUpperCase() + "_" + System.currentTimeMillis() + "_" + random.nextInt(1000);
    }
    
    protected boolean simulateNetworkCall() {
        // Simulate network latency and occasional failures
        try {
            Thread.sleep(random.nextInt(100) + 50); // 50-150ms delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return random.nextDouble() > 0.05; // 95% success rate
    }
}

// LEARNING ENHANCEMENT 4: Rich CreditCard implementation with validation
class CreditCardFixed extends BasePaymentStrategy {
    private final String cardNumber;
    private final String holderName;
    private final String expiryDate;
    private final String cvv;

    public CreditCardFixed(String cardNumber, String holderName, String expiryDate, String cvv) {
        this.cardNumber = Objects.requireNonNull(cardNumber, "Card number cannot be null");
        this.holderName = Objects.requireNonNull(holderName, "Holder name cannot be null");
        this.expiryDate = Objects.requireNonNull(expiryDate, "Expiry date cannot be null");
        this.cvv = Objects.requireNonNull(cvv, "CVV cannot be null");
    }

    @Override
    public PaymentResult processPayment(BigDecimal amount) {
        if (!validate()) {
            return new PaymentResult(false, null, "Invalid credit card details", amount);
        }
        
        if (!supportsAmount(amount)) {
            return new PaymentResult(false, null, "Amount exceeds credit card limit", amount);
        }

        BigDecimal fee = getTransactionFee(amount);
        BigDecimal totalAmount = amount.add(fee);
        
        if (simulateNetworkCall()) {
            String transactionId = generateTransactionId();
            return new PaymentResult(true, transactionId, 
                "Payment successful via Credit Card", totalAmount);
        } else {
            return new PaymentResult(false, null, "Credit card payment failed - network error", amount);
        }
    }

    @Override
    public boolean validate() {
        // Basic credit card validation simulation
        return cardNumber.length() >= 13 && cardNumber.length() <= 19 &&
               holderName.trim().length() > 0 &&
               cvv.length() == 3 &&
               cardNumber.matches("\\d+");
    }

    @Override
    public String getPaymentType() {
        return "Credit Card";
    }

    @Override
    public BigDecimal getTransactionFee(BigDecimal amount) {
        // 2.5% fee for credit cards
        return amount.multiply(BigDecimal.valueOf(0.025));
    }

    @Override
    public boolean supportsAmount(BigDecimal amount) {
        // Credit cards support up to $50,000
        return amount.compareTo(BigDecimal.valueOf(50000)) <= 0;
    }

    // Getter methods
    public String getCardNumber() { return "****-****-****-" + cardNumber.substring(cardNumber.length() - 4); }
    public String getHolderName() { return holderName; }
    
    @Override
    public String toString() {
        return String.format("CreditCard{holder='%s', card='%s'}", holderName, getCardNumber());
    }
}

// LEARNING ENHANCEMENT 5: UPI with different behavior and limits
class UPIFixed extends BasePaymentStrategy {
    private final String upiId;
    private final String pin;

    public UPIFixed(String upiId, String pin) {
        this.upiId = Objects.requireNonNull(upiId, "UPI ID cannot be null");
        this.pin = Objects.requireNonNull(pin, "UPI PIN cannot be null");
    }

    @Override
    public PaymentResult processPayment(BigDecimal amount) {
        if (!validate()) {
            return new PaymentResult(false, null, "Invalid UPI credentials", amount);
        }
        
        if (!supportsAmount(amount)) {
            return new PaymentResult(false, null, "Amount exceeds UPI daily limit", amount);
        }

        BigDecimal fee = getTransactionFee(amount);
        BigDecimal totalAmount = amount.add(fee);
        
        if (simulateNetworkCall()) {
            String transactionId = generateTransactionId();
            return new PaymentResult(true, transactionId, 
                "Payment successful via UPI", totalAmount);
        } else {
            return new PaymentResult(false, null, "UPI payment failed - service unavailable", amount);
        }
    }

    @Override
    public boolean validate() {
        return upiId.contains("@") && pin.length() == 4 && pin.matches("\\d{4}");
    }

    @Override
    public String getPaymentType() {
        return "UPI";
    }

    @Override
    public BigDecimal getTransactionFee(BigDecimal amount) {
        // UPI has no fee for amounts under $100, 1% above
        if (amount.compareTo(BigDecimal.valueOf(100)) <= 0) {
            return BigDecimal.ZERO;
        }
        return amount.multiply(BigDecimal.valueOf(0.01));
    }

    @Override
    public boolean supportsAmount(BigDecimal amount) {
        // UPI supports up to $5,000 per transaction
        return amount.compareTo(BigDecimal.valueOf(5000)) <= 0;
    }

    public String getUpiId() { return upiId; }
    
    @Override
    public String toString() {
        return String.format("UPI{id='%s'}", upiId);
    }
}

// LEARNING ENHANCEMENT 6: Crypto with unique characteristics
class CryptoFixed extends BasePaymentStrategy {
    private final String walletAddress;
    private final String privateKey;
    private final String cryptoType;

    public CryptoFixed(String walletAddress, String privateKey, String cryptoType) {
        this.walletAddress = Objects.requireNonNull(walletAddress, "Wallet address cannot be null");
        this.privateKey = Objects.requireNonNull(privateKey, "Private key cannot be null");
        this.cryptoType = Objects.requireNonNull(cryptoType, "Crypto type cannot be null");
    }

    @Override
    public PaymentResult processPayment(BigDecimal amount) {
        if (!validate()) {
            return new PaymentResult(false, null, "Invalid crypto wallet details", amount);
        }
        
        if (!supportsAmount(amount)) {
            return new PaymentResult(false, null, "Amount too small for crypto transaction", amount);
        }

        BigDecimal fee = getTransactionFee(amount);
        BigDecimal totalAmount = amount.add(fee);
        
        // Crypto has longer processing time
        try {
            Thread.sleep(random.nextInt(200) + 100); // 100-300ms for blockchain confirmation
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        if (simulateNetworkCall()) {
            String transactionId = generateTransactionId();
            return new PaymentResult(true, transactionId, 
                "Payment successful via " + cryptoType + " cryptocurrency", totalAmount);
        } else {
            return new PaymentResult(false, null, "Crypto payment failed - blockchain congestion", amount);
        }
    }

    @Override
    public boolean validate() {
        return walletAddress.length() >= 26 && privateKey.length() >= 50 && cryptoType.trim().length() > 0;
    }

    @Override
    public String getPaymentType() {
        return "Cryptocurrency (" + cryptoType + ")";
    }

    @Override
    public BigDecimal getTransactionFee(BigDecimal amount) {
        // Fixed fee for crypto transactions
        return BigDecimal.valueOf(5.0);
    }

    @Override
    public boolean supportsAmount(BigDecimal amount) {
        // Crypto requires minimum $10 due to network fees
        return amount.compareTo(BigDecimal.valueOf(10)) >= 0;
    }

    public String getWalletAddress() { 
        return walletAddress.substring(0, 6) + "..." + walletAddress.substring(walletAddress.length() - 6); 
    }
    public String getCryptoType() { return cryptoType; }
    
    @Override
    public String toString() {
        return String.format("Crypto{type='%s', wallet='%s'}", cryptoType, getWalletAddress());
    }
}

// LEARNING ENHANCEMENT 7: Better context class with strategy switching
class PaymentProcessor {
    private PaymentStrategyFixed paymentStrategy;
    private final String processorId;

    public PaymentProcessor(PaymentStrategyFixed paymentStrategy, String processorId) {
        this.paymentStrategy = Objects.requireNonNull(paymentStrategy, "Payment strategy cannot be null");
        this.processorId = Objects.requireNonNull(processorId, "Processor ID cannot be null");
    }

    // LEARNING ENHANCEMENT 8: Runtime strategy switching
    public void setPaymentStrategy(PaymentStrategyFixed paymentStrategy) {
        this.paymentStrategy = Objects.requireNonNull(paymentStrategy, "Payment strategy cannot be null");
        System.out.println("Payment strategy changed to: " + paymentStrategy.getPaymentType());
    }

    public PaymentResult processPayment(BigDecimal amount) {
        Objects.requireNonNull(amount, "Amount cannot be null");
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return new PaymentResult(false, null, "Amount must be positive", amount);
        }

        System.out.printf("Processing payment of $%.2f using %s...%n", amount, paymentStrategy.getPaymentType());
        
        // Pre-processing validation
        if (!paymentStrategy.validate()) {
            return new PaymentResult(false, null, "Payment method validation failed", amount);
        }

        PaymentResult result = paymentStrategy.processPayment(amount);
        
        System.out.printf("Payment result: %s%n", result.isSuccessful() ? "SUCCESS" : "FAILED");
        if (result.isSuccessful()) {
            System.out.printf("Transaction ID: %s%n", result.getTransactionId());
            System.out.printf("Total charged: $%.2f (including fees)%n", result.getFinalAmount());
        } else {
            System.out.printf("Error: %s%n", result.getMessage());
        }
        
        return result;
    }

    public PaymentStrategyFixed getCurrentStrategy() { return paymentStrategy; }
    public String getProcessorId() { return processorId; }
    
    @Override
    public String toString() {
        return String.format("PaymentProcessor{id='%s', strategy='%s'}", processorId, paymentStrategy.getPaymentType());
    }
}

// LEARNING ENHANCEMENT 9: Comprehensive demonstration
public class Strategy_1_fix {
    public static void main(String[] args) {
        System.out.println("=== Enhanced Strategy Pattern Demo ===\n");

        // Create payment strategies with realistic data
        PaymentStrategyFixed creditCard = new CreditCardFixed("4532123456789012", "John Doe", "12/25", "123");
        PaymentStrategyFixed upi = new UPIFixed("john.doe@bankupi", "1234");
        PaymentStrategyFixed crypto = new CryptoFixed("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa", 
            "L4rK3g4G6Jh7H8J9K0L1M2N3O4P5Q6R7S8T9U0V1W2X3Y4Z5", "Bitcoin");

        // Create payment processor
        PaymentProcessor processor = new PaymentProcessor(creditCard, "PROC-001");

        // DEMO 1: Basic payments with different strategies
        System.out.println("1. BASIC PAYMENTS WITH DIFFERENT STRATEGIES:");
        processor.processPayment(BigDecimal.valueOf(100.00));
        
        processor.setPaymentStrategy(upi);
        processor.processPayment(BigDecimal.valueOf(50.00));
        
        processor.setPaymentStrategy(crypto);
        processor.processPayment(BigDecimal.valueOf(25.00));

        // DEMO 2: Error handling and validation
        System.out.println("\n2. ERROR HANDLING AND VALIDATION:");
        
        // Test invalid amount
        processor.processPayment(BigDecimal.valueOf(-10.00));
        
        // Test amount limits
        processor.setPaymentStrategy(upi);
        processor.processPayment(BigDecimal.valueOf(10000.00)); // Exceeds UPI limit
        
        processor.setPaymentStrategy(crypto);
        processor.processPayment(BigDecimal.valueOf(5.00)); // Below crypto minimum

        // DEMO 3: Fee comparison
        System.out.println("\n3. FEE COMPARISON FOR $1000 PAYMENT:");
        BigDecimal testAmount = BigDecimal.valueOf(1000.00);
        
        PaymentStrategyFixed[] strategies = {creditCard, upi, crypto};
        for (PaymentStrategyFixed strategy : strategies) {
            BigDecimal fee = strategy.getTransactionFee(testAmount);
            System.out.printf("%s: Fee = $%.2f, Supports amount: %s%n", 
                strategy.getPaymentType(), fee, strategy.supportsAmount(testAmount));
        }

        // DEMO 4: Polymorphic behavior
        System.out.println("\n4. POLYMORPHIC PAYMENT PROCESSING:");
        PaymentStrategyFixed[] paymentMethods = {
            new CreditCardFixed("5555444433332222", "Jane Smith", "06/27", "456"),
            new UPIFixed("jane.smith@phonepe", "5678"),
            new CryptoFixed("3J98t1WpEZ73CNmQviecrnyiWrnqRhWNLy", 
                "5K9H7G6F5D4S3A2P1O9I8U7Y6T5R4E3W2Q1", "Ethereum")
        };

        processor = new PaymentProcessor(paymentMethods[0], "PROC-002");
        
        for (int i = 0; i < paymentMethods.length; i++) {
            processor.setPaymentStrategy(paymentMethods[i]);
            BigDecimal amount = BigDecimal.valueOf(100.0 * (i + 1));
            PaymentResult result = processor.processPayment(amount);
            System.out.println("Result: " + result);
            System.out.println();
        }

        // DEMO 5: Strategy selection based on amount
        System.out.println("5. SMART STRATEGY SELECTION:");
        demonstrateSmartStrategySelection();
    }

    // LEARNING ENHANCEMENT 10: Smart strategy selection
    private static void demonstrateSmartStrategySelection() {
        BigDecimal[] amounts = {
            BigDecimal.valueOf(5.00),    // Small amount
            BigDecimal.valueOf(500.00),  // Medium amount  
            BigDecimal.valueOf(10000.00) // Large amount
        };

        PaymentStrategyFixed[] strategies = {
            new UPIFixed("smart@upi", "1111"),
            new CreditCardFixed("4111111111111111", "Smart User", "12/26", "789"),
            new CryptoFixed("bc1qxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh", 
                "L3H8F5D9S2A7P4O6I1U3Y8T7R5E2W9Q4", "Bitcoin")
        };

        for (BigDecimal amount : amounts) {
            PaymentStrategyFixed bestStrategy = selectBestStrategy(amount, strategies);
            PaymentProcessor processor = new PaymentProcessor(bestStrategy, "SMART-PROC");
            
            System.out.printf("For amount $%.2f, selected: %s%n", amount, bestStrategy.getPaymentType());
            PaymentResult result = processor.processPayment(amount);
            System.out.println("Result: " + (result.isSuccessful() ? "SUCCESS" : "FAILED"));
            System.out.println();
        }
    }

    private static PaymentStrategyFixed selectBestStrategy(BigDecimal amount, PaymentStrategyFixed[] strategies) {
        PaymentStrategyFixed bestStrategy = null;
        BigDecimal lowestFee = BigDecimal.valueOf(Double.MAX_VALUE);

        for (PaymentStrategyFixed strategy : strategies) {
            if (strategy.supportsAmount(amount)) {
                BigDecimal fee = strategy.getTransactionFee(amount);
                if (fee.compareTo(lowestFee) < 0) {
                    lowestFee = fee;
                    bestStrategy = strategy;
                }
            }
        }

        return bestStrategy != null ? bestStrategy : strategies[0]; // Fallback
    }
}
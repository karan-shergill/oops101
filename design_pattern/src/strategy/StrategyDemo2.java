// UML: https://tinyurl.com/2bratchq

package strategy;

// Strategy Interface
interface PaymentStrategy {
    void pay(int amount);
}

// Concrete Strategy - Credit Card Payment
class CreditCardPayment implements PaymentStrategy {
    private final String cardNumber;

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using Credit Card: " + cardNumber);
    }
}

// Concrete Strategy - PayPal Payment
class PayPalPayment implements PaymentStrategy {
    private final String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using PayPal account linked to email: " + email);
    }
}

// Context
class ShoppingCart {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void checkout(int amount) {
        paymentStrategy.pay(amount);
    }
}

// Usage
public class StrategyDemo2 {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();

        // Set strategy to Credit Card Payment
        cart.setPaymentStrategy(new CreditCardPayment("1234-5678-9012-3456"));
        cart.checkout(100);  // Output: Paid 100 using Credit Card.

        // Set strategy to PayPal Payment
        cart.setPaymentStrategy(new PayPalPayment("user@example.com"));
        cart.checkout(200);  // Output: Paid 200 using PayPal.
    }
}

/*
OUTPUT:
Paid 100 using Credit Card.
Paid 200 using PayPal.
*/
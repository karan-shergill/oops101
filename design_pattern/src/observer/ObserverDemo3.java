/*
Implementing a notify me alert button for a single product iPhone 16 on amazon.com when the product is out of stock.
Users can select he/she want to get notified via SMS or Email, once iPhone is back in stock

This implementation effectively demonstrates the Observer Design Pattern by creating a subscription mechanism where
multiple observers are updated automatically when the subject's state changes.
*/

// UML: https://tinyurl.com/226gk6yh

package observer;

import java.util.ArrayList;
import java.util.List;

// The 'Observable' interface defines the methods to add, remove, and notify observers.
interface StockObservable {
    void add(NotificationAlertObserver observer);
    void remove(NotificationAlertObserver observer);
    void notifySubscribers();
    void setStockCount(int newStockAdded);
    int getStockCount();
}

// Concrete implementation of the 'Observable' interface.
class IphoneObservable implements StockObservable {
    private List<NotificationAlertObserver> observerList = new ArrayList<>(); // List to hold observers
    private int stockCount = 0; // Internal state of stock count

    @Override
    public void add(NotificationAlertObserver observer) {
        observerList.add(observer); // Add observer to the list
    }

    @Override
    public void remove(NotificationAlertObserver observer) {
        observerList.remove(observer); // Remove observer from the list
    }

    @Override
    public void notifySubscribers() {
        // Notify all registered observers about the state change
        for (NotificationAlertObserver observer : observerList) {
            observer.update(); // Call the update method of each observer
        }
    }

    @Override
    public void setStockCount(int newStockAdded) {
        // Only notify observers if stock count changes from 0
        if (stockCount == 0) {
            notifySubscribers(); // Notify all observers when new stock is added
        }
        stockCount += newStockAdded; // Update the stock count
    }

    @Override
    public int getStockCount() {
        return stockCount; // Return the current stock count
    }
}

// Observer interface that defines the update method that gets called by the observable
interface NotificationAlertObserver {
    void update();
}

// Concrete implementation of the Observer interface for email alerts
class EmailAlertObserverImpl implements NotificationAlertObserver {
    private String email; // Email address to send notifications
    private StockObservable stockObservable; // Reference to the observable

    public EmailAlertObserverImpl(String email, StockObservable stockObservable) {
        this.email = email;
        this.stockObservable = stockObservable;
    }

    @Override
    public void update() {
        // Called when the observable's state changes
        sendEmail(email, "iPhone 16 is back in stock! Hurry up!!!");
    }

    private void sendEmail(String emailId, String message) {
        System.out.println("Email sent to " + emailId + ". Subject: " + message);
    }
}

// Concrete implementation of the Observer interface for SMS alerts
class MobileSmsAlertObserverImpl implements NotificationAlertObserver {
    private String mobileNumber; // Mobile number to send SMS
    private StockObservable stockObservable; // Reference to the observable

    public MobileSmsAlertObserverImpl(String mobileNumber, StockObservable stockObservable) {
        this.mobileNumber = mobileNumber;
        this.stockObservable = stockObservable;
    }

    @Override
    public void update() {
        // Called when the observable's state changes
        sendSms(mobileNumber, "iPhone 16 is back in stock! Hurry up!!!");
    }

    private void sendSms(String mobileNumber, String message) {
        System.out.println("Text SMS sent to " + mobileNumber + ". Subject: " + message);
    }
}

// Main class to demonstrate the Observer pattern in action
public class ObserverDemo3 {
    public static void main(String[] args) {
        // Create an instance of the observable (iPhone stock)
        StockObservable iphoneStockObservable = new IphoneObservable();

        // Create observers and register them to the observable
        NotificationAlertObserver user1 = new EmailAlertObserverImpl("user1@xyz.com", iphoneStockObservable);
        NotificationAlertObserver user2 = new EmailAlertObserverImpl("user2@xyz.com", iphoneStockObservable);
        NotificationAlertObserver user3 = new MobileSmsAlertObserverImpl("9876543210", iphoneStockObservable);

        iphoneStockObservable.add(user1); // Register user1 for email alerts
        iphoneStockObservable.add(user2); // Register user2 for email alerts
        iphoneStockObservable.add(user3); // Register user3 for SMS alerts

        // Update the stock, which will notify all registered observers
        iphoneStockObservable.setStockCount(10);
    }
}

/*
Email sent to user1@xyz.com. Subject: iPhone 16 is back in stock! Hurry up!!!
Email sent to user2@xyz.com. Subject: iPhone 16 is back in stock! Hurry up!!!
Text SMS sent to 9876543210. Subject: iPhone 16 is back in stock! Hurry up!!!
*/
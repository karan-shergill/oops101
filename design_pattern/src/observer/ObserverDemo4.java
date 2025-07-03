package observer;

import java.util.ArrayList;
import java.util.List;

enum StockType {
    ADD, SUBTRACT;
}

interface StockObservableV2 {
    void addToWaitList(NotificationAlertObserverV2 notificationAlertObserverV2);
    void removeFromWaitList(NotificationAlertObserverV2 notificationAlertObserverV2);
    void updateStock(int count, StockType stockType);
    int getStock();
    String getProductName();
}

class Iphone17Observable implements  StockObservableV2 {
    private List<NotificationAlertObserverV2> listOfNotifications;
    private int currStock;
    public final String productName = "Iphone-17";

    public Iphone17Observable(int currSTock) {
        this.currStock = currSTock;
        listOfNotifications = new ArrayList<>();
    }

    @Override
    public void addToWaitList(NotificationAlertObserverV2 notificationAlertObserverV2) {
        this.listOfNotifications.add(notificationAlertObserverV2);
    }

    @Override
    public void removeFromWaitList(NotificationAlertObserverV2 notificationAlertObserverV2) {
        this.listOfNotifications.remove(notificationAlertObserverV2);
    }

    @Override
    public void updateStock(int count, StockType stockType) {
        switch (stockType) {
            case ADD -> {
                int previousStock = this.currStock;
                this.currStock += count;
                if (previousStock == 0 && this.currStock > 0) {
                    notifyUsersRegardingBackInStock();
                }
            }
            case SUBTRACT -> {
                this.currStock -= count; 
                if (this.currStock < 0) {
                    this.currStock = 0;
                }
            }
        }
    }

    @Override
    public int getStock() {
        return this.currStock;
    }

    @Override
    public String getProductName() {
        return productName;
    }

    private void notifyUsersRegardingBackInStock() {
        for (NotificationAlertObserverV2 curr : this.listOfNotifications) {
            curr.update(this);
        }
    }
}

interface NotificationAlertObserverV2 {
    void update(StockObservableV2 stockObservableV2);
}

class EmailAlert implements NotificationAlertObserverV2 {
    String email;

    public EmailAlert(String email) {
        this.email = email;
    }

    @Override
    public void update(StockObservableV2 stockObservableV2) {
        System.out.println(stockObservableV2.getProductName() + " is back in STOCK! Email Notification sent to " + this.email);
    }
}

class AppAlert implements NotificationAlertObserverV2 {
    String addID;

    public AppAlert(String addID) {
        this.addID = addID;
    }

    @Override
    public void update(StockObservableV2 stockObservableV2) {
        System.out.println(stockObservableV2.getProductName() + " is back in STOCK! AppAlert Notification sent to " + this.addID);
    }
}

class SMS implements NotificationAlertObserverV2 {
    String mobileNo;

    public SMS(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    @Override
    public void update(StockObservableV2 stockObservableV2) {
        System.out.println(stockObservableV2.getProductName() + " is back in STOCK! SMS Notification sent to " + this.mobileNo);
    }
}

public class ObserverDemo4 {
    public static void main(String[] args) {
        StockObservableV2 iphone17 = new Iphone17Observable(1);

        NotificationAlertObserverV2 user1 = new EmailAlert("user1@gmail.com");
        NotificationAlertObserverV2 user2 = new AppAlert("user_2");
        NotificationAlertObserverV2 user3 = new SMS("9876543210");

        iphone17.updateStock(1, StockType.SUBTRACT);

        iphone17.addToWaitList(user1);
        iphone17.addToWaitList(user2);
        iphone17.addToWaitList(user3);

        iphone17.updateStock(5, StockType.ADD);
    }
}


/* OUTPUT
Iphone-17 is back in STOCK! Email Notification sent to user1@gmail.com
Iphone-17 is back in STOCK! AppAlert Notification sent to user_2
Iphone-17 is back in STOCK! SMS Notification sent to 9876543210
*/
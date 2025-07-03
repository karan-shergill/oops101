// UML: https://tinyurl.com/236zycvt

package observer;

import java.util.ArrayList;
import java.util.List;

// Observer interface with the update method
interface Channel {
    // Method to be called when the subject updates
    void update(Object o);
}

// Subject class that manages and notifies observers
class NewsAgency {
    private String news;  // State that observers are interested in
    private List<Channel> channels = new ArrayList<>();  // List of observers

    // Method to add an observer
    public void addObserver(Channel channel) {
        this.channels.add(channel);
    }

    // Method to remove an observer
    public void removeObserver(Channel channel) {
        this.channels.remove(channel);
    }

    // Method to update the state and notify observers
    public void setNews(String news) {
        this.news = news;  // Update the state
        // Notify all registered observers about the state change
        for (Channel channel : this.channels) {
            channel.update(this.news);  // Pass the new state to observers
        }
    }
}

// Concrete observer class that reacts to state changes in the subject
class AajTakNews implements Channel {
    private String news;  // Observer's state

    // Update method called by the subject to notify observer of state change
    @Override
    public void update(Object news) {
        this.setNews((String) news + " - AajTakNews");  // Update observer's state
    }

    // Getter for observer's state
    public String getNews() {
        return news;
    }

    // Setter for observer's state
    public void setNews(String news) {
        this.news = news;
    }
}

// Another concrete observer class that reacts to state changes in the subject
class NDTVNews implements Channel {
    private String news;  // Observer's state

    // Update method called by the subject to notify observer of state change
    @Override
    public void update(Object news) {
        this.setNews((String) news + " - NDTVNews");  // Update observer's state
    }

    // Getter for observer's state
    public String getNews() {
        return news;
    }

    // Setter for observer's state
    public void setNews(String news) {
        this.news = news;
    }
}

// Another concrete observer class that reacts to state changes in the subject
class BBCNews implements Channel {
    private String news;  // Observer's state

    // Update method called by the subject to notify observer of state change
    @Override
    public void update(Object news) {
        this.setNews((String) news + " - BBCNews");  // Update observer's state
    }

    // Getter for observer's state
    public String getNews() {
        return news;
    }

    // Setter for observer's state
    public void setNews(String news) {
        this.news = news;
    }
}

// Main class to demonstrate the Observer pattern
public class ObserverDemo1 {
    public static void main(String[] args) {
        // Create the subject instance
        NewsAgency observable = new NewsAgency();

        // Create observer instances
        AajTakNews aajTakNews = new AajTakNews();
        NDTVNews ndtvNews = new NDTVNews();
        BBCNews bbcNews = new BBCNews();

        // Register observers with the subject
        observable.addObserver(aajTakNews);
        observable.addObserver(ndtvNews);

        // Update the subject's state
        observable.setNews("First confirmed case of COVID-19 infection in India");

        // Output the state of observers that have been updated
        System.out.println(aajTakNews.getNews());
        System.out.println(ndtvNews.getNews());

        // Observer not registered with the subject will not have updated state
        System.out.println(bbcNews.getNews());

        observable.removeObserver(ndtvNews);

        // Update the subject's state
        observable.setNews("First confirmed case of COVID-29 infection in India");

        System.out.println(aajTakNews.getNews());
        System.out.println(ndtvNews.getNews());
        System.out.println(bbcNews.getNews());
    }
}

/*
OUTPUT:
First confirmed case of COVID-19 infection in India - AajTakNews
First confirmed case of COVID-19 infection in India - NDTVNews
null
First confirmed case of COVID-29 infection in India - AajTakNews
First confirmed case of COVID-19 infection in India - NDTVNews
null
*/

/**
 * Observer is a behavioral design pattern that lets you define a subscription mechanism to notify multiple
 * objects about any events that happen to the object they’re observing.
 * Reference: https://refactoring.guru/design-patterns/observer
 *            https://www.baeldung.com/java-observer-pattern
 */

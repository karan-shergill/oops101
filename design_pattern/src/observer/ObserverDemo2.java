// extension of ObserverDemo1 - subscribing to multiple
package observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

interface Agency {
    void addObserver(ChannelV1 channel);
    void removeObserver(ChannelV1 channel);
}

class NewsAgencyV1 implements Agency {
    String news;
    List<ChannelV1> channelList;

    public NewsAgencyV1() {
        channelList = new ArrayList<>();
    }

    @Override
    public void addObserver(ChannelV1 channel) {
        this.channelList.add(channel);
    }

    @Override
    public void removeObserver(ChannelV1 channel) {
        this.channelList.remove(channel);
    }

    public void setNews(String news) {
        this.news = news;
        notifyChannels();
    }

    private void notifyChannels() {
        for (ChannelV1 curr : this.channelList) {
            curr.updateNews(news, this);
        }
    }

    public String getNews() {
        return news;
    }

    public List<ChannelV1> getChannelList() {
        return channelList;
    }
}


interface ChannelV1 {
    void updateNews(String news, NewsAgencyV1 newsAgencyV1);
}

class AajTak implements ChannelV1 {
    HashMap<NewsAgencyV1, List<String>> allNews;

    public AajTak() {
        allNews = new HashMap<>();
    }

    @Override
    public void updateNews(String news, NewsAgencyV1 newsAgencyV1) {
        allNews.getOrDefault(newsAgencyV1, allNews.put(newsAgencyV1, new ArrayList<>())).add(news);
    }
}

class NDTV implements ChannelV1 {
    HashMap<NewsAgencyV1, List<String>> allNews;

    public NDTV() {
        allNews = new HashMap<>();
    }

    @Override
    public void updateNews(String news, NewsAgencyV1 newsAgencyV1) {
        allNews.getOrDefault(newsAgencyV1, allNews.put(newsAgencyV1, new ArrayList<>())).add(news);

    }
}

class Republic implements ChannelV1 {
    HashMap<NewsAgencyV1, List<String>> allNews;

    public Republic() {
        allNews = new HashMap<>();
    }

    @Override
    public void updateNews(String news, NewsAgencyV1 newsAgencyV1) {
        allNews.getOrDefault(newsAgencyV1, allNews.put(newsAgencyV1, new ArrayList<>())).add(news);
    }
}


public class ObserverDemo2 {
    public static void main(String[] args) {
        NewsAgencyV1 PTI = new NewsAgencyV1();
        NewsAgencyV1 ANI = new NewsAgencyV1();

        AajTak aajTak = new AajTak();
        NDTV ndtv = new NDTV();
        Republic republic = new Republic();

        PTI.addObserver(aajTak);
        PTI.addObserver(ndtv);
        PTI.addObserver(republic);

        ANI.addObserver(aajTak);
        ANI.addObserver(republic);

        PTI.setNews("India won the 1st test match against England");
        ANI.setNews("USA dropped B2 booms on Iran");
        ANI.setNews("England to buy 5 B2 boomers from USA");

        System.out.println("AajTak News: " + aajTak.allNews);
        System.out.println("NDTV News: " + ndtv.allNews);
        System.out.println("Republic News: " + republic.allNews);

    }
}

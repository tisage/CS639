package com.rssreader;

/**
 * Created by tianyuelite on 6/30/15.
 */

import java.util.List;
import java.util.Vector;

public class RSSFeed {
    private String title = null;
    private String description = null; // not used for current version
    private String link = null; //
    private String pubdate = null;
    private List<RSSItem> itemList;

    RSSFeed() {
        itemList = new Vector<RSSItem>(0);
    }

    void addItem(RSSItem item) {
        itemList.add(item);
    }

    RSSItem getItem(int location) {
        return itemList.get(location);
    }

    List<RSSItem> getList() {
        return itemList;
    }

    void setTitle(String value) {
        title = value;
    }

    void setDescription(String value) {
        description = value;
    }

    void setLink(String value) {
        link = value;
    }

    void setPubdate(String value) {
        pubdate = value;
    }

    String getTitle() {
        return title;
    }

    String getPubdate() {
        return pubdate;
    }

}


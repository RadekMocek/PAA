package com.radekmocek.mybeerdiary.model;

import java.io.Serializable;
import java.util.Comparator;

public class PubVisit implements Serializable {

    private int id;
    private String pubName;
    private long timestamp;
    private int totalBeers;
    private int totalCost;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getTotalBeers() {
        return totalBeers;
    }

    public void setTotalBeers(int nBeers) {
        this.totalBeers = nBeers;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public static final Comparator<PubVisit> comparator = (PubVisit p1, PubVisit p2) -> Long.compare(p2.timestamp, p1.timestamp);
}

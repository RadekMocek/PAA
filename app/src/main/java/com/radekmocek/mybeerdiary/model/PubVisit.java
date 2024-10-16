package com.radekmocek.mybeerdiary.model;

public class PubVisit {

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
}

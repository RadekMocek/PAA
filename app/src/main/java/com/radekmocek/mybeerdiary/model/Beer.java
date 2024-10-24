package com.radekmocek.mybeerdiary.model;

import java.io.Serializable;
import java.util.Comparator;

public class Beer implements Serializable {

    private long id;
    private long pubVisitID;
    private String breweryName;
    private String description;
    private long timestamp;
    private int decilitres;
    private double EPM;
    private double ABV;
    private int price;

    public Beer cloneWithoutIDAndTimestamp() {
        Beer b2 = new Beer();
        b2.pubVisitID = this.pubVisitID;
        b2.breweryName = this.breweryName;
        b2.description = this.description;
        b2.decilitres = this.decilitres;
        b2.EPM = this.EPM;
        b2.ABV = this.ABV;
        b2.price = this.price;
        return b2;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPubVisitID() {
        return pubVisitID;
    }

    public void setPubVisitID(long pubVisitID) {
        this.pubVisitID = pubVisitID;
    }

    public String getBreweryName() {
        return breweryName;
    }

    public void setBreweryName(String breweryName) {
        this.breweryName = breweryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getDecilitres() {
        return decilitres;
    }

    public void setDecilitres(int decilitres) {
        this.decilitres = decilitres;
    }

    public double getEPM() {
        return EPM;
    }

    public void setEPM(double EPM) {
        this.EPM = EPM;
    }

    public double getABV() {
        return ABV;
    }

    public void setABV(double ABV) {
        this.ABV = ABV;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public static final Comparator<Beer> comparator = Comparator.comparingLong((Beer b) -> b.timestamp);
}

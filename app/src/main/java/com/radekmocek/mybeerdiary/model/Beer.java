package com.radekmocek.mybeerdiary.model;

public class Beer {

    private int id;
    private String breweryName;
    private String description;
    private long timestamp;
    private int decilitres;
    private double EPM;
    private double ABV;
    private int price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}

package com.radekmocek.mybeerdiary.model;

public class PubVisitInfoCrate {

    public int totalCost;
    public double permille;
    public String sober;

    public PubVisitInfoCrate(int totalCost, double permille, String soberIn) {
        this.totalCost = totalCost;
        this.permille = permille;
        this.sober = soberIn;
    }
}

package com.radekmocek.mybeerdiary.util;

import com.radekmocek.mybeerdiary.adapter.BeersAdapter;
import com.radekmocek.mybeerdiary.model.Beer;
import com.radekmocek.mybeerdiary.model.PubVisitInfoCrate;

import java.util.List;

public final class Calc {

    private Calc() {
    }

    // https://www.brewersfriend.com/abv-calculator/
    private final static double FINAL_GRAVITY_SG1 = 1.0109079891683685d; // Approximated Final Gravity

    private static double plato2SG1(double plato) {
        return 1d + (plato / (258.6d - ((plato / 258.2d) * 227.1d)));
    }

    public static double EPM2AVG(double OriginalGravityPlato) {
        double OriginalGravitySG1 = plato2SG1(OriginalGravityPlato);
        return (76.08d * (OriginalGravitySG1 - FINAL_GRAVITY_SG1) / (1.775d - OriginalGravitySG1)) * (FINAL_GRAVITY_SG1 / 0.794d);
    }

    //
    public static double fitDoubleToTwoIntAndTwoFractionPlacesPositive(double in) {
        if (in > 99.99d) return 99.99d;
        if (in < 0d) return 0d;
        return Math.round(in * 100d) / 100d;
    }

    // https://www.wikihow.com/Calculate-Blood-Alcohol-Content-(Widmark-Formula)
    // https://en.wikipedia.org/wiki/Standard_drink
    private final static double BAC_R_CONSTANT_MALE = 0.68d;
    private final static double BAC_R_CONSTANT_FEMALE = 0.55d;
    private final static double ALCOHOL_METABOLISM_RATE_PERCENT_PER_HOUR = 0.016d;
    private final static double ALCOHOL_METABOLISM_RATE_PERMILLE_PER_HOUR = 10 * ALCOHOL_METABOLISM_RATE_PERCENT_PER_HOUR;

    public static PubVisitInfoCrate getPubVisitInfo(BeersAdapter adBeers, int userWeight, boolean isUserMale) {
        List<Beer> beers = adBeers.getCollection();
        if (beers.isEmpty() || userWeight <= 0) return new PubVisitInfoCrate(0, 0, "0h0m");

        // Assuming collection is sorted by timestamp (earliest first)
        long firstTimestamp = beers.get(0).getTimestamp();
        long lastTimestamp = beers.get(beers.size() - 1).getTimestamp();
        long duration = lastTimestamp - firstTimestamp;

        int totalPrice = 0;
        double gramsOfAlcohol = 0d;
        for (Beer b : beers) {
            totalPrice += b.getPrice();
            gramsOfAlcohol += ((b.getDecilitres() / 10d) * b.getABV() * 789.45d) / 100d;
        }

        double rConstant = (isUserMale) ? BAC_R_CONSTANT_MALE : BAC_R_CONSTANT_FEMALE;
        double permille = (gramsOfAlcohol / (rConstant * userWeight)) - ((duration / 3600000d) * ALCOHOL_METABOLISM_RATE_PERCENT_PER_HOUR);

        double remainingHours = permille / ALCOHOL_METABOLISM_RATE_PERMILLE_PER_HOUR;
        int hours = (int) remainingHours;
        int minutes = (int) ((remainingHours - hours) * 60);
        String soberIn = hours + "h" + minutes + "m";

        return new PubVisitInfoCrate(totalPrice, fitDoubleToTwoIntAndTwoFractionPlacesPositive(permille), soberIn);
    }

}

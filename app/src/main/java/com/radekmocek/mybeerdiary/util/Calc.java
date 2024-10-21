package com.radekmocek.mybeerdiary.util;

public final class Calc {

    private Calc() {
    }

    private final static double FinalGravitySG1 = 1.0109079891683685d; // Approximated Final Gravity

    private static double plato2SG1(double plato) {
        return 1d + (plato / (258.6d - ((plato / 258.2d) * 227.1d)));
    }

    public static double EPM2AVG(double OriginalGravityPlato) {
        double OriginalGravitySG1 = plato2SG1(OriginalGravityPlato);
        return (76.08d * (OriginalGravitySG1 - FinalGravitySG1) / (1.775d - OriginalGravitySG1)) * (FinalGravitySG1 / 0.794d);
    }

    public static double fitDoubleToTwoIntAndTwoFractionPlacesPositive(double in) {
        if (in > 99.99d) return 99.99d;
        if (in < 0d) return 0d;
        return Math.round(in * 100d) / 100d;
    }

}

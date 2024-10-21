package com.radekmocek.mybeerdiary.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class Conv {

    private Conv() {
    }

    private static final DateFormat df = new SimpleDateFormat("dd. MM. yyyy – HH:mm", Locale.forLanguageTag("cs-CZ"));

    private static Date long2date(long l) {
        return new Date(l);
    }

    private static String date2str(Date d) {
        if (d == null) return "";
        return df.format(d);
    }

    public static String longDate2str(long l) {
        return date2str(long2date(l));
    }

    public static String nBeerLitres2str(float f) {
        int decilitres = Math.round(f * 10);
        switch (decilitres) {
            case 1:
                return "Deci";
            case 3:
                return "Malé";
            case 4:
                return "Čtyřka";
            case 5:
                return "Velké";
            case 10:
                return "Tuplák";
            default:
                return decilitres + " deci";
        }
    }

    public static String EPMEditText2EBMEditText(String EPMEditText) {
        double EPM;
        try {
            EPM = Double.parseDouble(EPMEditText);
        } catch (Exception e) {
            return String.valueOf(0d);
        }
        return String.valueOf(Calc.fitDoubleToTwoIntAndTwoFractionPlacesPositive(Calc.EPM2AVG(EPM)));
    }
}

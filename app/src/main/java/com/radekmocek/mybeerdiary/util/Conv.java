package com.radekmocek.mybeerdiary.util;

import android.content.res.Resources;

import com.radekmocek.mybeerdiary.R;
import com.radekmocek.mybeerdiary.model.Beer;

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

    public static String nBeerLitres2str(Resources res, float f) {
        return nBeerDecilitres2str(res, Math.round(f * 10));
    }

    public static String nBeerDecilitres2str(Resources res, int decilitres) {
        switch (decilitres) {
            case 1:
                return res.getString(R.string.beer_size_1dl);
            case 2:
                return res.getString(R.string.beer_size_2dl);
            case 3:
                return res.getString(R.string.beer_size_3dl);
            case 4:
                return res.getString(R.string.beer_size_4dl);
            case 5:
                return res.getString(R.string.beer_size_5dl);
            case 6:
                return res.getString(R.string.beer_size_6dl);
            case 7:
                return res.getString(R.string.beer_size_7dl);
            case 8:
                return res.getString(R.string.beer_size_8dl);
            case 9:
                return res.getString(R.string.beer_size_9dl);
            case 10:
                return res.getString(R.string.beer_size_10dl);
            default:
                return res.getString(R.string.placeholder);
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

    public static String beer2str(Resources res, Beer b) {
        if (b == null) return res.getString(R.string.beer);
        String breweryName = b.getBreweryName();
        String result = (!breweryName.isEmpty()) ? breweryName : res.getString(R.string.beer);
        String description = b.getDescription();
        if (!description.isEmpty()) result += " " + description;
        if (result.length() > Const.MAX_EDIT_BEER_DIALOG_FRAGMENT_HEADER_CHARACTERS) {
            result = result.substring(0, Const.MAX_EDIT_BEER_DIALOG_FRAGMENT_HEADER_CHARACTERS - 3) + "...";
        }
        return result;
    }
}

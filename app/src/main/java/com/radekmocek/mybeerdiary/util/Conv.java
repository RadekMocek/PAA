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
}

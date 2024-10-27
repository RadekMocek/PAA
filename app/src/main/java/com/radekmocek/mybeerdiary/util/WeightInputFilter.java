package com.radekmocek.mybeerdiary.util;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeightInputFilter implements InputFilter {

    private final Pattern mPattern = Pattern.compile("[1-9]?|[1-9][0-9]|[1-9][0-9][0-9]");

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dStart, int dEnd) {
        String replacement = source.subSequence(start, end).toString();
        String newVal = dest.subSequence(0, dStart) + replacement + dest.subSequence(dEnd, dest.length());
        Matcher matcher = mPattern.matcher(newVal);
        if (matcher.matches()) return null;
        if (TextUtils.isEmpty(source)) return dest.subSequence(dStart, dEnd);
        else return "";
    }
}

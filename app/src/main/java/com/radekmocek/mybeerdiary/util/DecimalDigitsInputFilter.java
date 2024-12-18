package com.radekmocek.mybeerdiary.util;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// https://stackoverflow.com/a/24632346
public class DecimalDigitsInputFilter implements InputFilter {

    private final Pattern mPattern;

    private static final int DIGITS_BEFORE_ZERO_DEFAULT = 100;
    private static final int DIGITS_AFTER_ZERO_DEFAULT = 100;

    public DecimalDigitsInputFilter(Integer digitsBeforeZero, Integer digitsAfterZero) {
        int mDigitsBeforeZero = (digitsBeforeZero != null ? digitsBeforeZero : DIGITS_BEFORE_ZERO_DEFAULT);
        int mDigitsAfterZero = (digitsAfterZero != null ? digitsAfterZero : DIGITS_AFTER_ZERO_DEFAULT);
        mPattern = Pattern.compile("[0-9]{0," + (mDigitsBeforeZero) + "}+((\\.[0-9]{0," + (mDigitsAfterZero) + "})?)|(\\.)?");
    }

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

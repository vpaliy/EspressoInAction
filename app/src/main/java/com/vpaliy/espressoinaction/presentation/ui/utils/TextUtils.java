package com.vpaliy.espressoinaction.presentation.ui.utils;

import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

public class TextUtils {

    private TextUtils() {
        throw new UnsupportedOperationException();
    }

    public static SpannableString buildSpannableText(String itemText, int spanOffset) {
        final SpannableString string = new SpannableString(itemText);
        string.setSpan(new RelativeSizeSpan(1.65f), itemText.length() - spanOffset, itemText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return string;
    }

    public static SpannableStringBuilder mergeColoredText(String leftPart, String rightPart, int leftColor, int rightColor) {
        final SpannableStringBuilder builder = new SpannableStringBuilder();
        final SpannableString leftPartSpannable = new SpannableString(leftPart);
        final SpannableString rightPartSpannable = new SpannableString(rightPart);
        leftPartSpannable.setSpan(new ForegroundColorSpan(leftColor), 0, leftPart.length(), 0);
        rightPartSpannable.setSpan(new ForegroundColorSpan(rightColor), 0, rightPart.length(), 0);
        return builder.append(leftPartSpannable).append(rightPartSpannable);
    }

    public static String toSingleLine(CharSequence sequence) {
        return sequence.toString().replace('\n', ' ');
    }

    public static String parseToUri(int resourceId) {
        return Uri.parse("android.resource://com.vpaliy.espressoinaction/" + resourceId).toString();
    }

    public static int parseToResourceId(String imageUrl){
        return 0;
    }
}

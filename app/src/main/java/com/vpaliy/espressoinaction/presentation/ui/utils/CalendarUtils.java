package com.vpaliy.espressoinaction.presentation.ui.utils;


import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;

import com.vpaliy.espressoinaction.R;

import java.util.Calendar;

public class CalendarUtils {
    private CalendarUtils(){
        throw new UnsupportedOperationException("Don't create an instance of this class");
    }

    public static SpannableString buildSpannableText(String itemText, int spanOffset){
        final SpannableString string = new SpannableString(itemText);
        string.setSpan(new RelativeSizeSpan(1.65f), itemText.length() - spanOffset, itemText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return string;
    }

    public static String getDay(Context context, Calendar calendar){
        String dayText=null;
        Calendar today=Calendar.getInstance();
        if(isToday(calendar,today)){
            return context.getString(R.string.today_date);
        }
        calendar.add(Calendar.DAY_OF_MONTH,1);
        if(isToday(calendar,today)){
            return context.getString(R.string.tomorrow_date);
        }
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        switch (calendar.get(Calendar.DAY_OF_WEEK)){
            case Calendar.MONDAY:
                dayText=context.getString(R.string.monday_date);
                break;
            case Calendar.TUESDAY:
                dayText=context.getString(R.string.tuesday_date);
                break;
            case Calendar.WEDNESDAY:
                dayText=context.getString(R.string.wednesday_date);
                break;
            case Calendar.THURSDAY:
                dayText=context.getString(R.string.thursday_date);
                break;
            case Calendar.FRIDAY:
                dayText=context.getString(R.string.friday_date);
                break;
            case Calendar.SATURDAY:
                dayText=context.getString(R.string.saturday_date);
                break;
            case Calendar.SUNDAY:
                dayText=context.getString(R.string.sunday_date);
                break;
        }
        return dayText;
    }

    private static boolean isToday(Calendar calendar, Calendar today){
        return today.get(Calendar.DAY_OF_MONTH)==calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static String dayOfMonth(Calendar calendar){
        StringBuilder dayBuilder=new StringBuilder();
        int number=calendar.get(Calendar.DAY_OF_MONTH);
        if(number<10) dayBuilder.append("0");
        dayBuilder.append(number);
        return dayBuilder.toString();
    }
}

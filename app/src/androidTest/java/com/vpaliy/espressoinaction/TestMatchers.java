package com.vpaliy.espressoinaction;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.vpaliy.espressoinaction.presentation.ui.utils.CalendarUtils;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import java.util.Calendar;
import java.util.Locale;
import de.hdodenhof.circleimageview.CircleImageView;

public class TestMatchers {

    private static final String TAG=TestMatchers.class.getSimpleName();

    static Matcher<View> withCompoundDrawable(final int resourceId){
        return new BoundedMatcher<View, TextView>(TextView.class) {
            @Override
            protected boolean matchesSafely(TextView item) {
                for(Drawable drawable:item.getCompoundDrawables()){
                    if(drawablesMatch(drawable, ContextCompat.getDrawable(item.getContext(),resourceId))){
                        return true;
                    }
                }
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(String.format(Locale.US,"Has image drawable %d",resourceId));
            }
        };
    }

    static Matcher<View> withSameDay(Calendar calendar){
        return new BoundedMatcher<View, TextView>(TextView.class) {

            @Override
            protected boolean matchesSafely(TextView item) {
                CharSequence sample=item.getText();
                if(sample!=null){
                    String day=CalendarUtils.getDay(item.getContext(),calendar);
                    day+=" "+CalendarUtils.dayOfMonth(calendar);
                    return TextUtils.equals(sample,day);
                }
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Text should be the same day:"+CalendarUtils.dayOfMonth(calendar));
            }
        };
    }

    static Matcher<View> withTrimmedText(int resourceId){
        return new BoundedMatcher<View, TextView>(TextView.class) {
            @Override
            protected boolean matchesSafely(TextView item) {
                String text=item.getContext().getString(resourceId);
                return text.replace('\n',' ').equals(item.getText());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Find text with id:"+resourceId);
            }
        };
    }

    static Matcher<View> withDoubleText(final int firstResourceId, final int secondResourceId){
        return new BoundedMatcher<View,TextView>(TextView.class){
            @Override
            protected boolean matchesSafely(TextView item) {
                Context context=item.getContext();
                return TextUtils.equals(item.getText(),
                        TextUtils.concat(context.getString(firstResourceId),
                                context.getString(secondResourceId)));
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(String.format(Locale.US,"Has text with resources: %d, %d",firstResourceId,secondResourceId));
            }
        };
    }

    static Matcher<View> withDrawable(final int resourceId){
        return new BoundedMatcher<View, CircleImageView>(CircleImageView.class) {
            @Override
            protected boolean matchesSafely(CircleImageView item) {
                return drawablesMatch(item.getDrawable(),ContextCompat.getDrawable(item.getContext(),resourceId));
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(String.format(Locale.US,"Has image drawable %d",resourceId));
            }
        };
    }

    private static boolean drawablesMatch(Drawable drawableA, Drawable drawableB) {
        if(drawableA==null||drawableB==null) return false;
        Drawable.ConstantState stateA = drawableA.getConstantState();
        Drawable.ConstantState stateB = drawableB.getConstantState();
        return (stateA != null && stateB != null && stateA.equals(stateB))
                || getBitmap(drawableA).sameAs(getBitmap(drawableB));
    }

    private static Bitmap getBitmap(Drawable drawable) {
        Bitmap result;
        if (drawable instanceof BitmapDrawable) {
            result = ((BitmapDrawable) drawable).getBitmap();
        } else {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            if (width <= 0) {
                width = 1;
            }
            if (height <= 0) {
                height = 1;
            }
            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }
        return result;
    }
}

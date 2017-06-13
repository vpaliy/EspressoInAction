package com.vpaliy.espressoinaction;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;
import com.vpaliy.espressoinaction.presentation.view.SweetnessTracker;
import org.hamcrest.Matcher;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

public class TestViewActions {

    public static ViewAction dragToPosition(float progress){
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isDisplayed();
            }

            @Override
            public String getDescription() {
                return "Set position to sweetness tracker:"+progress;
            }

            @Override
            public void perform(UiController uiController, View view) {
                SweetnessTracker tracker = SweetnessTracker.class.cast(view);
                tracker.setValue(progress);
            }
        };
    }
}

package com.vpaliy.espressoinaction;

import android.app.Instrumentation;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.util.Pair;
import android.util.SparseArray;
import android.widget.TextView;

import com.vpaliy.espressoinaction.di.DaggerMockApplicationComponent;
import com.vpaliy.espressoinaction.di.MockApplicationComponent;
import com.vpaliy.espressoinaction.di.MockDataModule;
import com.vpaliy.espressoinaction.di.module.ApplicationModule;
import com.vpaliy.espressoinaction.domain.IRepository;
import com.vpaliy.espressoinaction.domain.model.Coffee;
import com.vpaliy.espressoinaction.domain.model.CoffeeType;
import com.vpaliy.espressoinaction.domain.model.Order;
import com.vpaliy.espressoinaction.domain.model.Sweetness;
import com.vpaliy.espressoinaction.fake.DataProvider;
import com.vpaliy.espressoinaction.presentation.ui.activity.HomeActivity;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.vpaliy.espressoinaction.TestMatchers.withCompoundDrawable;
import static com.vpaliy.espressoinaction.TestMatchers.withDoubleText;
import static com.vpaliy.espressoinaction.TestMatchers.withSameDay;
import static com.vpaliy.espressoinaction.TestMatchers.withTrimmedText;
import static com.vpaliy.espressoinaction.TestViewActions.dragToPosition;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BottomSheetTest {

    private static final int ITEM_POSITION=0;
    private List<Coffee> data=DataProvider.fakeCoffees();

    @Rule
    public ActivityTestRule<HomeActivity> activityTestRule = new ActivityTestRule<>(HomeActivity.class,true,false);

    @Before
    public void setUp(){
        Instrumentation instrumentation= InstrumentationRegistry.getInstrumentation();
        CoffeeApp app=CoffeeApp.class.cast(instrumentation.getTargetContext().getApplicationContext());
        MockApplicationComponent component= DaggerMockApplicationComponent.builder()
                .applicationModule(new ApplicationModule(instrumentation.getContext()))
                .mockDataModule(new MockDataModule())
                .build();
        app.setComponent(component);
        IRepository<Coffee> mockCoffees=component.coffeeRepository();
        IRepository<Order> mockOrders=component.orderRepository();

        Coffee coffee=data.get(ITEM_POSITION);
        when(mockCoffees.getAll()).thenReturn(Observable.just(data));
        when(mockOrders.getAll()).thenReturn(Observable.just(DataProvider.fakeOrders()));
        when(mockCoffees.getById(coffee.getCoffeeId())).thenReturn(Observable.just(coffee));
        activityTestRule.launchActivity(null);
    }

    @Test
    public void showsBottomSheetWithStepOneWhenItemIsSelected(){
        Coffee coffee=data.get(ITEM_POSITION);
        openBottomSheet();

        onView(withId(R.id.coffee_name))
                .check(matches(withText(coffee.getCoffeeType().name)));

        onView(withId(R.id.go_button))
                .check(matches(isDisplayed()));

        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.go_button))))
                .check(matches(withText(R.string.go_label)));

        onView(withId(R.id.coffee_price))
                .check(matches(withText(String.format(Locale.US,"$ %.0f",coffee.getPrice()))));

        onView(withId(R.id.small_cup_size)).check(matches(allOf(isDisplayed(),
                withDoubleText(R.string.small_size_label,R.string.plus_zero_label),
                withCompoundDrawable(R.drawable.ic_small_coffee_size))));

        onView(withId(R.id.medium_size_cup)).check(matches(allOf(isDisplayed(),
                withDoubleText(R.string.medium_size_label,R.string.plus_one_label),
                withCompoundDrawable(R.drawable.ic_coffee_medium_size))));

        onView(withId(R.id.large_size_cup)).check(matches(allOf(isDisplayed(),
                withDoubleText(R.string.large_size_label,R.string.plus_two_label),
                withCompoundDrawable(R.drawable.ic_coffee_large_size))));

        onView(withId(R.id.tall_size_cup)).check(matches(allOf(isDisplayed(),
                withDoubleText(R.string.tall_size_label,R.string.plus_three_label),
                withCompoundDrawable(R.drawable.ic_coffee_xlarge_size))));

        onView(withId(R.id.coconut_milk)).check(matches(allOf(isDisplayed(),
                withText(R.string.coconut_milk_label), withCompoundDrawable(R.drawable.ic_coconut_x))));

        onView(withId(R.id.soy_milk)).check(matches(allOf(isDisplayed(),
                withText(R.string.soy_milk_label),withCompoundDrawable(R.drawable.ic_beans))));

        onView(withId(R.id.almond_milk)).check(matches(allOf(isDisplayed(),
                withText(R.string.almond_milk_label),withCompoundDrawable(R.drawable.ic_almond))));

        onView(withId(R.id.cow_milk)).check(matches(allOf(isDisplayed(),
                withText(R.string.whole_milk_label),withCompoundDrawable(R.drawable.ic_milk_bottle))));
    }

    @Test
    public void showsBottomSheetWithStepTwoWhenItemIsSelected(){
        openBottomSheet();
        moveToNextStep();

        Coffee coffee=data.get(ITEM_POSITION);

        onView(withId(R.id.coffee_name))
                .check(matches(allOf(isDisplayed(),withText(coffee.getCoffeeType().name))));

        onView(withId(R.id.go_button))
                .check(matches(isDisplayed()));

        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.go_button))))
                .check(matches(withText(R.string.go_label)));

        onView(withId(R.id.coffee_price))
                .check(matches(allOf(isDisplayed(),
                        withText(String.format(Locale.US,"$ %.0f",coffee.getPrice())))));

        onView(withId(R.id.picker)).perform(dragToPosition(Sweetness.NOT_SWEET.percent));
        onView(withId(R.id.sugar_level)).check(matches(withText(R.string.not_sweet_label)));

        onView(withId(R.id.picker)).perform(dragToPosition(Sweetness.SLIGHTLY_SWEET.percent));
        onView(withId(R.id.sugar_level)).check(matches(withText(R.string.slightly_sweet_label)));

        onView(withId(R.id.picker)).perform(dragToPosition(Sweetness.HALF_SWEET.percent));
        onView(withId(R.id.sugar_level)).check(matches(withText(R.string.half_sweet_label)));

        onView(withId(R.id.picker)).perform(dragToPosition(Sweetness.MODERATELY_SWEET.percent));
        onView(withId(R.id.sugar_level)).check(matches(withText(R.string.moderately_sweet_label)));

        onView(withId(R.id.picker)).perform(dragToPosition(Sweetness.FULL_SWEETNESS.percent));
        onView(withId(R.id.sugar_level)).check(matches(withText(R.string.full_sweetness_label)));
    }

    @Test
    public void showsBottomSheetWithStepThreeWhenItemIsSelected(){
        openBottomSheet();
        moveToNextStep();
        moveToNextStep();

        Coffee coffee=data.get(ITEM_POSITION);

        onView(withId(R.id.coffee_name))
                .check(matches(allOf(isDisplayed(),withText(coffee.getCoffeeType().name))));

        onView(withId(R.id.go_button))
                .check(matches(isDisplayed()));

        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.go_button))))
                .check(matches(withText(R.string.order_label)));

        onView(withId(R.id.coffee_price))
                .check(matches(allOf(isDisplayed(),
                        withText(String.format(Locale.US,"$ %.0f",coffee.getPrice())))));

        Calendar calendar=Calendar.getInstance();
        onView(withId(R.id.today_day)).perform(click());
        onView(allOf(hasSibling(withId(R.id.property_label_one)),
                withSameDay(calendar)))
                .check(matches(isDisplayed()));

        calendar.add(Calendar.DAY_OF_MONTH,1);
        onView(withId(R.id.tomorrow_day)).perform(click());
        onView(allOf(hasSibling(withId(R.id.property_label_one)),
                withSameDay(calendar)))
                .check(matches(isDisplayed()));

        calendar.add(Calendar.DAY_OF_MONTH,1);
        onView(withId(R.id.third_day)).perform(click());
        onView(allOf(hasSibling(withId(R.id.property_label_one)),
                withSameDay(calendar)))
                .check(matches(isDisplayed()));

        calendar.add(Calendar.DAY_OF_MONTH,1);
        onView(withId(R.id.fourth_day)).perform(click());
        onView(allOf(hasSibling(withId(R.id.property_label_one)),
                withSameDay(calendar)))
                .check(matches(isDisplayed()));

        onView(withId(R.id.time_frame_one)).perform(click());
        onView(allOf(hasSibling(withId(R.id.property_label_two)),
                withTrimmedText(R.string.nine_to_twelve_label)))
                .check(matches(isDisplayed()));

        onView(withId(R.id.time_frame_two)).perform(click());
        onView(allOf(hasSibling(withId(R.id.property_label_two)),
                withTrimmedText(R.string.twelve_to_three_label)))
                .check(matches(isDisplayed()));

        onView(withId(R.id.time_frame_three)).perform(click());
        onView(allOf(hasSibling(withId(R.id.property_label_two)),
                withTrimmedText(R.string.three_to_five_label)))
                .check(matches(isDisplayed()));

        onView(withId(R.id.time_frame_four)).perform(click());
        onView(allOf(hasSibling(withId(R.id.property_label_two)),
                withTrimmedText(R.string.five_to_nine_label)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void showsConfirmationScreenWithAllPossibleCombinations(){
        openBottomSheet();
        moveToNextWithClicks(R.id.small_cup_size,R.id.almond_milk);
        moveToNextWithDrag(100);
        moveToNextStep();
        onView(withId(R.id.coffee_title))
                .check(matches(allOf(withText(CoffeeType.ESPRESSO_FRAPPUCCINO.name),isDisplayed())));
        onView(withId(R.id.confirmation_sweetness))
                .check(matches(allOf(withText(R.string.full_sweetness_label),isDisplayed())));

        onView(withId(R.id.txt_subtitle))
                .check(matches(allOf(withText(R.string.order_placed),isDisplayed())));

        onView(withId(R.id.confirmation_cup_type))
                .check(matches(allOf(withText(R.string.small_size_label),
                        withCompoundDrawable(R.drawable.ic_small_coffee_size),isDisplayed())));

        onView(withId(R.id.confirmation_milk_type))
                .check(matches(allOf(withText(R.string.almond_milk_label),
                        withCompoundDrawable(R.drawable.ic_almond),isDisplayed())));
    }

    /* Just opens the bottom sheet*/
    private void openBottomSheet(){
        onView(allOf(isDisplayed(),withId(R.id.coffee_recycler)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(ITEM_POSITION,click()));
        onView(withId(R.id.content)).check(matches(isDisplayed()));
        onView(isRoot()).perform(swipeUp());
    }

    private void moveToNextStep(){
        onView(withId(R.id.go_button)).perform(click());
        onView(withId(R.id.content)).check(matches(isDisplayed()));
    }

    private void moveToNextWithDrag(float progress){
        onView(withId(R.id.picker)).perform(dragToPosition(progress));
        moveToNextStep();
    }

    private void moveToNextWithClicks(int...clicks){
        if(clicks!=null){
            for (int click : clicks) {
                onView(withId(click)).perform(click());
            }
        }
        moveToNextStep();
    }

    @Test
    public void clicksAllSizeTypesAndAnimatesThemToSizeLabel(){
        openBottomSheet();
        onView(withId(R.id.small_cup_size))
                .perform(click());
        onView(allOf(hasSibling(withId(R.id.property_label_one)),
                withCompoundDrawable(R.drawable.ic_small_coffee_size)))
                .check(matches(isDisplayed()));
        onView(withId(R.id.medium_size_cup))
                .perform(click());
        onView(allOf(hasSibling(withId(R.id.property_label_one)),
                withCompoundDrawable(R.drawable.ic_coffee_medium_size)))
                .check(matches(isDisplayed()));
        onView(withId(R.id.large_size_cup))
                .perform(click());
        onView(allOf(hasSibling(withId(R.id.property_label_one)),
                withCompoundDrawable(R.drawable.ic_coffee_large_size)))
                .check(matches(isDisplayed()));
        onView(withId(R.id.tall_size_cup))
                .perform(click());
        onView(allOf(hasSibling(withId(R.id.property_label_one)),
                withCompoundDrawable(R.drawable.ic_coffee_xlarge_size)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void clicksAllMilkTypesAndAnimatesThemToMilkLabel(){
        openBottomSheet();

        onView(withId(R.id.soy_milk))
                .perform(click());
        onView(allOf(hasSibling(withId(R.id.property_label_two)),
                withCompoundDrawable(R.drawable.ic_beans)))
                .check(matches(isDisplayed()));

        onView(withId(R.id.almond_milk))
                .perform(click());
        onView(allOf(hasSibling(withId(R.id.property_label_two)),
                withCompoundDrawable(R.drawable.ic_almond)))
                .check(matches(isDisplayed()));

        onView(withId(R.id.coconut_milk))
                .perform(click());
        onView(allOf(hasSibling(withId(R.id.property_label_two)),
                withCompoundDrawable(R.drawable.ic_coconut_x)))
                .check(matches(isDisplayed()));

        onView(withId(R.id.cow_milk))
                .perform(click());
        onView(allOf(hasSibling(withId(R.id.property_label_two)),
                withCompoundDrawable(R.drawable.ic_milk_bottle)))
                .check(matches(isDisplayed()));
    }
}

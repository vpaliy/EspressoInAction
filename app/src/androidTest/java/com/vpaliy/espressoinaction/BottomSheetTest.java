package com.vpaliy.espressoinaction;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import com.vpaliy.espressoinaction.di.DaggerMockApplicationComponent;
import com.vpaliy.espressoinaction.di.MockApplicationComponent;
import com.vpaliy.espressoinaction.di.MockDataModule;
import com.vpaliy.espressoinaction.di.module.ApplicationModule;
import com.vpaliy.espressoinaction.domain.IRepository;
import com.vpaliy.espressoinaction.domain.model.Coffee;
import com.vpaliy.espressoinaction.domain.model.Order;
import com.vpaliy.espressoinaction.fake.DataProvider;
import com.vpaliy.espressoinaction.presentation.ui.activity.HomeActivity;
import com.vpaliy.espressoinaction.presentation.ui.utils.TextUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Locale;

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
import static com.vpaliy.espressoinaction.TestMatchers.withDrawable;
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
    public void showsBottomSheetWhenItemIsSelected(){
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

        //here's concurrency
       // onView(withId(R.id.coffee_image))
           //     .check(matches(allOf(isDisplayed(), withDrawable(TextUtils.parseToResourceId(coffee.getImageUrl())))));
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

    /* Just opens the bottom sheet*/
    private void openBottomSheet(){
        onView(allOf(isDisplayed(),withId(R.id.coffee_recycler)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(ITEM_POSITION,click()));
        onView(withId(R.id.content)).check(matches(isDisplayed()));
        onView(isRoot()).perform(swipeUp());
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

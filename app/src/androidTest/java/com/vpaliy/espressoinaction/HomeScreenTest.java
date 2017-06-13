package com.vpaliy.espressoinaction;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
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

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import java.util.List;
import java.util.Locale;
import rx.Observable;
import static com.vpaliy.espressoinaction.TestMatchers.withCompoundDrawable;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.when;

public class HomeScreenTest {

    private IRepository<Coffee> mockCoffees;
    private IRepository<Order> mockOrders;

    @Rule
    public ActivityTestRule<HomeActivity> activityTestRule = new ActivityTestRule<>(HomeActivity.class,true,false);

    private IdlingResource idlingResource;

    @Before
    public void setUp(){
        Instrumentation instrumentation= InstrumentationRegistry.getInstrumentation();
        CoffeeApp app=CoffeeApp.class.cast(instrumentation.getTargetContext().getApplicationContext());
        MockApplicationComponent component= DaggerMockApplicationComponent.builder()
                .applicationModule(new ApplicationModule(instrumentation.getContext()))
                .mockDataModule(new MockDataModule())
                .build();
        app.setComponent(component);
        mockCoffees=component.coffeeRepository();
        mockOrders=component.orderRepository();
    }

    private List<Coffee> regularLaunch(){
        List<Coffee> data = DataProvider.fakeCoffees();
        when(mockCoffees.getAll()).thenReturn(Observable.just(data));
        when(mockOrders.getAll()).thenReturn(Observable.just(DataProvider.fakeOrders()));
        activityTestRule.launchActivity(null);
        return data;
    }

    @Test
    public void changesActionBarTitlesWhenTabIsSelected(){
        regularLaunch();
        onView(withId(R.id.coffee))
                .perform(click());
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.actionBar))))
                .check(matches(withText(R.string.coffee_tab)));
        onView(withId(R.id.orders))
                .perform(click());
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.actionBar))))
                .check(matches(withText(R.string.order_tab)));
        onView(withId(R.id.info))
                .perform(click());
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.actionBar))))
                .check(matches(withText(R.string.info_tab)));
    }

    @Test
    public void showsListOfCoffeesWhenThereIsData() {
        List<Coffee> data=regularLaunch();
        for (int index = 0; index < data.size(); index++) {
            Coffee coffee = data.get(index);
            String title = coffee.getCoffeeType().name;
            String price = String.format(Locale.US, "$%.0f", coffee.getPrice());
            onView(allOf(withId(R.id.coffee_recycler), isDisplayed())).perform(RecyclerViewActions.scrollToPosition(index));
            onView(allOf(withId(R.id.name), hasSibling(allOf(withText(price), withId(R.id.price))),
                    withText(title)))
                    .check(matches(isDisplayed()));
            onView(allOf(withId(R.id.price), hasSibling(allOf(withText(title), withId(R.id.name))),
                    withText(price)))
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void showsListOfOrdersWhenThereIsData(){
        regularLaunch();
        List<Order> orders=DataProvider.fakeOrders();
        idlingResource=activityTestRule.getActivity().getIdlingResource();
        //Espresso.registerIdlingResources(idlingResource);
        onView(withId(R.id.orders)).perform(click());
        for(int index=0;index<orders.size();index++){
            Order order=orders.get(index);
            Coffee coffee=order.getCoffee();
            String title = coffee.getCoffeeType().name;
            String price = String.format(Locale.US, "$ %.0f", coffee.getPrice());

            onView(allOf(withId(R.id.coffee_recycler),isDisplayed()))
                    .perform(RecyclerViewActions.scrollToPosition(index));

            onView(allOf(withId(R.id.coffee_title),withText(title)))
                    .check(matches(isDisplayed()));

            onView(allOf(withId(R.id.order_price),withText(price),
                     hasSibling(allOf(withId(R.id.coffee_title),withText(title)))))
                    .check(matches(isDisplayed()));

            onView(allOf(withId(R.id.order_pick_day),hasSibling(allOf(withText(title),withId(R.id.coffee_title)))))
                    .check(matches(allOf(withCompoundDrawable(R.drawable.ic_access_time_black_24dp),
                            withText(order.getPickUpDay()),isDisplayed())));

            onView(allOf(withId(R.id.order_pick_time),hasSibling(allOf(withText(title),withId(R.id.coffee_title)))))
                    .check(matches(allOf(withCompoundDrawable(R.drawable.ic_calendar),
                            withText(order.getPickUpTime()),isDisplayed())));
        }
    }

    @After
    public void tearUp(){
        if (idlingResource != null) {
            Espresso.unregisterIdlingResources(idlingResource);
        }
    }

    @Test
    public void showsErrorMessageWhenThereIsNoData(){

    }
}

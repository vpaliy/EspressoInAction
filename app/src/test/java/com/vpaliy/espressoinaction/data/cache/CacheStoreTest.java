package com.vpaliy.espressoinaction.data.cache;

import android.os.Build;

import com.vpaliy.espressoinaction.BuildConfig;
import com.vpaliy.espressoinaction.domain.model.Coffee;
import com.vpaliy.espressoinaction.domain.model.Order;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.vpaliy.espressoinaction.common.FakeDataProvider.FAKE_ID;
import static com.vpaliy.espressoinaction.common.FakeDataProvider.provideCoffee;
import static com.vpaliy.espressoinaction.common.FakeDataProvider.provideOrder;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,
        manifest = Config.DEFAULT_MANIFEST,
        sdk = Build.VERSION_CODES.LOLLIPOP)
public class CacheStoreTest {


    private static final int INITIAL_SIZE=10;

    private CacheStore<Order> orderCache;
    private CacheStore<Coffee> coffeeCache;

    @Before
    public void setUp(){
        orderCache=new CacheStore<>(INITIAL_SIZE);
        coffeeCache=new CacheStore<>(INITIAL_SIZE);
    }

    @Test
    public void putsValueIntoCache(){
        Order order=provideOrder();
        Coffee coffee=provideCoffee();
        int id=order.getOrderId();

        orderCache.put(id,order);
        coffeeCache.put(id,coffee);

        assertThat(orderCache.get(id),is(order));
        assertThat(coffeeCache.get(id),is(coffee));
    }

    @Test
    public void returnsNullIfThereIsNoValueInCache(){
        assertThat(orderCache.get(FAKE_ID),nullValue());
        assertThat(coffeeCache.get(FAKE_ID),nullValue());
    }

    @Test
    public void replacesOneValueWithAnotherIfTheyHaveSimilarKey(){
        Order order=provideOrder();
        Coffee coffee=provideCoffee();
        int id=FAKE_ID;

        orderCache.put(id,order);
        coffeeCache.put(id,coffee);

        Order newOrder=provideOrder();
        Coffee newCoffee=provideCoffee();

        orderCache.put(id,newOrder);
        coffeeCache.put(id,newCoffee);

        assertThat(orderCache.get(id),not(order));
        assertThat(coffeeCache.get(id),not(coffee));
    }
}

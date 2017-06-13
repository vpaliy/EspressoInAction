package com.vpaliy.espressoinaction.common;


import com.vpaliy.espressoinaction.domain.model.Coffee;
import com.vpaliy.espressoinaction.domain.model.CoffeeType;
import com.vpaliy.espressoinaction.domain.model.MilkType;
import com.vpaliy.espressoinaction.domain.model.Order;
import com.vpaliy.espressoinaction.domain.model.SizeType;
import com.vpaliy.espressoinaction.domain.model.Sweetness;
import java.util.Arrays;
import java.util.List;

public class FakeDataProvider {

    public static final int FAKE_ID=123;
    public static final double FAKE_PRICE=100.0;
    public static final String FAKE_TIME="fake_time";
    public static final String FAKE_DAY="fake_day";
    public static final String FAKE_NAME="fake_name";
    public static final CoffeeType FAKE_COFFEE_TYPE=CoffeeType.ESPRESSO;
    public static final String FAKE_IMAGE_URL="fake_image_url";
    public static final MilkType FAKE_MILK_TYPE= MilkType.WHOLE_MILK;
    public static final SizeType FAKE_SIZE_TYPE=SizeType.LARGE;
    public static final Sweetness FAKE_SWEETNESS=Sweetness.FULL_SWEETNESS;


    public static Coffee provideCoffee(){
        Coffee coffee=new Coffee();
        coffee.setCoffeeId(FAKE_ID);
        coffee.setCoffeeType(FAKE_COFFEE_TYPE);
        coffee.setImageUrl(FAKE_IMAGE_URL);
        coffee.setCoffeeType(FAKE_COFFEE_TYPE);
        coffee.setMilkType(FAKE_MILK_TYPE);
        coffee.setPrice(FAKE_PRICE);
        coffee.setSweetness(FAKE_SWEETNESS);
        coffee.setSizeType(FAKE_SIZE_TYPE);
        return coffee;
    }

    public static List<Coffee> provideCoffeeList(){
        return Arrays.asList(provideCoffee(),provideCoffee(),provideCoffee(),
                provideCoffee(),provideCoffee());
    }

    public static List<Order> provideOrderList(){
        return Arrays.asList(provideOrder(),provideOrder(),provideOrder(),
                    provideOrder(),provideOrder());
    }

    public static Order provideOrder(){
        Order order=new Order();
        order.setCoffee(provideCoffee());
        order.setName(FAKE_NAME);
        order.setOrderId(FAKE_ID);
        order.setPickUpTime(FAKE_TIME);
        order.setPickUpDay(FAKE_DAY);
        return order;
    }
}

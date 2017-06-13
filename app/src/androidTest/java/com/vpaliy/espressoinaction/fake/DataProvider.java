package com.vpaliy.espressoinaction.fake;

import android.net.Uri;

import com.vpaliy.espressoinaction.R;
import com.vpaliy.espressoinaction.domain.model.Coffee;
import com.vpaliy.espressoinaction.domain.model.CoffeeType;
import com.vpaliy.espressoinaction.domain.model.MilkType;
import com.vpaliy.espressoinaction.domain.model.Order;
import com.vpaliy.espressoinaction.domain.model.SizeType;
import com.vpaliy.espressoinaction.domain.model.Sweetness;
import com.vpaliy.espressoinaction.presentation.ui.utils.CalendarUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class DataProvider {

    public static final String AMERICANO_PIC=getUrl(R.drawable.americano_black).toString();
    public static final String ESPRESSO_PIC=getUrl(R.drawable.espresso).toString();
    public static final String LATTE_PIC=getUrl(R.drawable.latte).toString();
    public static final String MOCHA_PIC=getUrl(R.drawable.mocha).toString();
    public static final String CAPPUCCINO_PIC=getUrl(R.drawable.cap).toString();
    public static final String CARAMEL_FRAPPUCCINO_PIC=getUrl(R.drawable.caramel_frap).toString();
    public static final String ESPRESSO_FRAPPUCCINO_PIC=getUrl(R.drawable.espresso_frap).toString();

    public static final String ORDER_PICK_UP_TIME="PM 9-10";

    public static List<Coffee> fakeCoffees(){
        List<Coffee> result=new ArrayList<>();
        int id=0;
        Random random=new Random();
        for(CoffeeType type:CoffeeType.values()){
            Coffee coffee=new Coffee();
            coffee.setSizeType(SizeType.SMALL);
            int price=random.nextInt(8);
            if(price<=1) price++;
            coffee.setPrice(price);
            coffee.setMilkType(MilkType.NONE);
            coffee.setSweetness(Sweetness.FULL_SWEETNESS);
            coffee.setCoffeeId(id++);
            coffee.setCoffeeType(type);
            switch (type){
                case AMERICANO:
                    coffee.setImageUrl(AMERICANO_PIC);
                    break;
                case ESPRESSO:
                    coffee.setImageUrl(ESPRESSO_PIC);
                    break;
                case LATTE:
                    coffee.setImageUrl(LATTE_PIC);
                    break;
                case MOCHA:
                    coffee.setImageUrl(MOCHA_PIC);
                    break;
                case CAPPUCCINO:
                    coffee.setImageUrl(CAPPUCCINO_PIC);
                    break;
                case CARAMEL_FRAPPUCCINO:
                    coffee.setImageUrl(CARAMEL_FRAPPUCCINO_PIC);
                    break;
                case ESPRESSO_FRAPPUCCINO:
                    coffee.setImageUrl(ESPRESSO_FRAPPUCCINO_PIC);
                    break;
            }
            result.add(0,coffee);
        }
        return result;
    }

    public static List<Order> fakeOrders(){
        List<Coffee> coffees=fakeCoffees();
        List<Order> orders=new ArrayList<>(coffees.size());
        Calendar calendar=Calendar.getInstance();
        coffees.forEach(coffee -> {
            Order order=new Order();
            order.setCoffee(coffee);
            order.setPickUpDay(CalendarUtils.dayOfMonth(calendar));
            calendar.add(Calendar.DAY_OF_MONTH,1);
            order.setOrderId(coffee.getCoffeeId());
            order.setPickUpTime(ORDER_PICK_UP_TIME);
            orders.add(order);
        });
        return orders;
    }

    private static Uri getUrl(int res){
        return Uri.parse("android.resource://com.vpaliy.espressoinaction/" + res);
    }
}

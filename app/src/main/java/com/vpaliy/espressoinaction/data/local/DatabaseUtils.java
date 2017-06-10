package com.vpaliy.espressoinaction.data.local;

import android.content.ContentValues;
import android.database.Cursor;
import com.vpaliy.espressoinaction.domain.model.Coffee;
import com.vpaliy.espressoinaction.domain.model.CoffeeType;
import com.vpaliy.espressoinaction.domain.model.MilkType;
import com.vpaliy.espressoinaction.domain.model.Order;
import com.vpaliy.espressoinaction.domain.model.SizeType;
import com.vpaliy.espressoinaction.domain.model.Sweetness;

import static com.vpaliy.espressoinaction.data.local.CoffeeContract.CoffeeColumns;
import static com.vpaliy.espressoinaction.data.local.CoffeeContract.OrderColumns;

public class DatabaseUtils {

    public static ContentValues toValues(Coffee coffee){
        if(coffee==null) return null;
        ContentValues values=new ContentValues();
        values.put(CoffeeColumns.COFFEE_ID,coffee.getCoffeeId());
        values.put(CoffeeColumns.COFFEE_COFFEE_TYPE,coffee.getCoffeeType().name());
        values.put(CoffeeColumns.COFFEE_IMAGE_URL,coffee.getImageUrl());
        values.put(CoffeeColumns.COFFEE_MILK_TYPE,coffee.getMilkType().name());
        values.put(CoffeeColumns.COFFEE_PRICE,coffee.getPrice());
        values.put(CoffeeColumns.COFFEE_SIZE_TYPE,coffee.getSizeType().name());
        values.put(CoffeeColumns.COFFEE_SWEETNESS,coffee.getSweetness().name());
        return values;
    }

    public static ContentValues toValues(Order order){
        if(order==null) return null;
        ContentValues values=new ContentValues();
        values.put(OrderColumns.COFFEE_ID,order.getCoffee().getCoffeeId());
        values.put(OrderColumns.ORDER_ID,order.getOrderId());
        values.put(OrderColumns.ORDER_PICK_UP_DAY,order.getPickUpDay());
        values.put(OrderColumns.ORDER_PICK_UP_TIME,order.getPickUpTime());
        return values;
    }

    public static Order toOrder(Cursor cursor){
        if(cursor==null) return null;
        Order order=new Order();
        order.setOrderId(cursor.getInt(cursor.getColumnIndex(OrderColumns.ORDER_ID)));
        order.setPickUpDay(cursor.getString(cursor.getColumnIndex(OrderColumns.ORDER_PICK_UP_DAY)));
        order.setPickUpTime(cursor.getString(cursor.getColumnIndex(OrderColumns.ORDER_PICK_UP_TIME)));
        order.setName(cursor.getString(cursor.getColumnIndex(OrderColumns.ORDER_NAME)));
        return order;
    }

    public static Coffee toCoffee(Cursor cursor){
        if(cursor==null) return null;
        Coffee coffee=new Coffee();
        coffee.setCoffeeId(cursor.getInt(cursor.getColumnIndex(CoffeeColumns.COFFEE_ID)));
        coffee.setPrice(cursor.getDouble(cursor.getColumnIndex(CoffeeColumns.COFFEE_PRICE)));
        coffee.setImageUrl(cursor.getString(cursor.getColumnIndex(CoffeeColumns.COFFEE_IMAGE_URL)));
        Sweetness sweetness=Sweetness.valueOf(cursor.getString(cursor.getColumnIndex(CoffeeColumns.COFFEE_SWEETNESS)));
        MilkType milkType=MilkType.valueOf(cursor.getString(cursor.getColumnIndex(CoffeeColumns.COFFEE_MILK_TYPE)));
        SizeType sizeType=SizeType.valueOf(cursor.getString(cursor.getColumnIndex(CoffeeColumns.COFFEE_SIZE_TYPE)));
        CoffeeType coffeeType=CoffeeType.valueOf(cursor.getString(cursor.getColumnIndex(CoffeeColumns.COFFEE_COFFEE_TYPE)));
        coffee.setSweetness(sweetness);
        coffee.setMilkType(milkType);
        coffee.setSizeType(sizeType);
        coffee.setCoffeeType(coffeeType);
        return coffee;
    }
}

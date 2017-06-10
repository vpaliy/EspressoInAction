package com.vpaliy.espressoinaction.data.local;


import android.content.ContentResolver;
import android.database.Cursor;
import android.util.Log;

import com.vpaliy.espressoinaction.domain.model.Coffee;
import com.vpaliy.espressoinaction.domain.model.Order;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class OrderHandler implements DataHandler<Order> {

    private ContentResolver contentResolver;

    @Inject
    public OrderHandler(ContentResolver contentResolver){
        this.contentResolver=contentResolver;
    }

    @Override
    public Order fetchById(int id){
        Cursor cursor=contentResolver.query(CoffeeProvider.Orders.withId(id),null,null,null,null);
        Order order=DatabaseUtils.toOrder(cursor);
        if(cursor!=null) cursor.close();
        return order;
    }

    @Override
    public void insert(Order item) {
        contentResolver.insert(CoffeeProvider.Orders.ORDERS,DatabaseUtils.toValues(item));
        contentResolver.insert(CoffeeProvider.OrderedCoffees.COFFEES,DatabaseUtils.toValues(item.getCoffee()));
    }

    @Override
    public List<Order> fetchAll() {
        Cursor cursor=contentResolver.query(CoffeeProvider.Orders.ORDERS,null,null,null,null);
        if(cursor!=null){
            List<Order> orders=new ArrayList<>();
            while(cursor.moveToNext()){
                Order order = DatabaseUtils.toOrder(cursor);
                Coffee coffee = DatabaseUtils.toCoffee(cursor);
                order.setCoffee(coffee);
                orders.add(order);

            }
            if(!cursor.isClosed()) cursor.close();
            return orders;
        }
        return new ArrayList<>();
    }

}

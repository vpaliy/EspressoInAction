package com.vpaliy.espressoinaction.data.local;


import android.content.ContentResolver;

import com.vpaliy.espressoinaction.domain.model.Order;

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
        Order order=new Order();
        return order;
    }

    @Override
    public void insert(Order item) {

    }

    @Override
    public List<Order> fetchAll() {
        return null;
    }

}

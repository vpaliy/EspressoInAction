package com.vpaliy.espressoinaction.data.local;

import android.content.ContentResolver;
import com.vpaliy.espressoinaction.domain.model.Coffee;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CoffeeHandler implements DataHandler<Coffee> {

    private ContentResolver contentResolver;

    @Inject
    public CoffeeHandler(ContentResolver contentResolver){
        this.contentResolver=contentResolver;
    }

    @Override
    public Coffee fetchById(int id) {
        return null;
    }

    @Override
    public void insert(Coffee item) {

    }

    @Override
    public List<Coffee> fetchAll() {
        return null;
    }
}

package com.vpaliy.espressoinaction.di.module;

import android.content.ContentResolver;
import android.content.Context;

import com.vpaliy.espressoinaction.data.repository.CoffeeRepository;
import com.vpaliy.espressoinaction.data.repository.OrderRepository;
import com.vpaliy.espressoinaction.domain.IRepository;
import com.vpaliy.espressoinaction.domain.model.Coffee;
import com.vpaliy.espressoinaction.domain.model.Order;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Singleton
    @Provides
    public IRepository<Coffee> coffeeIRepository(CoffeeRepository repository){
        return repository;
    }

    @Singleton
    @Provides
    public IRepository<Order> orderIRepository(OrderRepository repository){
        return repository;
    }

    @Singleton
    @Provides
    public ContentResolver provideContentResolver(Context context){
        return context.getContentResolver();
    }

}

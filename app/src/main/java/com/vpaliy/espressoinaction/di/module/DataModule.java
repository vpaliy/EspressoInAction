package com.vpaliy.espressoinaction.di.module;

import android.content.ContentResolver;
import android.content.Context;

import com.vpaliy.espressoinaction.data.cache.CacheStore;
import com.vpaliy.espressoinaction.data.local.CoffeeHandler;
import com.vpaliy.espressoinaction.data.local.DataHandler;
import com.vpaliy.espressoinaction.data.local.OrderHandler;
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

    private static final int CACHE_SIZE=100;

    @Singleton
    @Provides
    IRepository<Coffee> provideCoffeeIRepository(CoffeeRepository repository){
        return repository;
    }

    @Singleton
    @Provides
    IRepository<Order> provideOrderIRepository(OrderRepository repository){
        return repository;
    }

    @Singleton
    @Provides
    ContentResolver provideContentResolver(Context context){
        return context.getContentResolver();
    }

    @Singleton
    @Provides
    DataHandler<Coffee> provideCoffeeHandler(CoffeeHandler handler){
        return handler;
    }

    @Singleton
    @Provides
    DataHandler<Order> provideOrderHandler(OrderHandler handler){
        return handler;
    }

    @Singleton
    @Provides
    CacheStore<Coffee> provideCoffeeCache(){
        return new CacheStore<>(CACHE_SIZE);
    }

    @Singleton
    @Provides
    CacheStore<Order> provideOrderCache(){
        return new CacheStore<>(CACHE_SIZE);
    }

}

package com.vpaliy.espressoinaction.data;

import com.vpaliy.espressoinaction.data.cache.Cache;
import com.vpaliy.espressoinaction.data.local.DataHandler;
import com.vpaliy.espressoinaction.domain.IRepository;
import com.vpaliy.espressoinaction.domain.model.Coffee;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class CoffeeRepository implements IRepository<Coffee> {

    private DataHandler<Coffee> handler;
    private Cache<Coffee> cache;

    @Inject
    public CoffeeRepository(DataHandler<Coffee> handler,
                            Cache<Coffee> cache){
        this.handler=handler;
        this.cache=cache;
    }

    @Override
    public Observable<List<Coffee>> getAll() {
        return Observable.fromCallable(()->handler.fetchAll());
    }

    @Override
    public Observable<Coffee> getById(int id) {
        return Observable.fromCallable(()->handler.fetchById(id));
    }

    @Override
    public void insert(Coffee item) {
        handler.insert(item);
    }
}

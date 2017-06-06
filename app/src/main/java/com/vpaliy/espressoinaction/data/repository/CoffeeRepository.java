package com.vpaliy.espressoinaction.data.repository;

import com.vpaliy.espressoinaction.data.cache.CacheStore;
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
    private CacheStore<Coffee> cache;

    @Inject
    public CoffeeRepository(DataHandler<Coffee> handler,
                            CacheStore<Coffee> cache){
        this.handler=handler;
        this.cache=cache;
    }

    @Override
    public Observable<List<Coffee>> getAll() {
        return Observable.fromCallable(()->handler.fetchAll())
                .doOnNext(this::cache);
    }

    private void cache(List<Coffee> coffees){
        if(coffees!=null){
            coffees.forEach(coffee -> cache.put(coffee.getCoffeeId(),coffee));
        }
    }

    @Override
    public Observable<Coffee> getById(int id) {
        if(cache.isInCache(id)){
            return Observable.just(cache.get(id));
        }
        return Observable.fromCallable(()->handler.fetchById(id));
    }

    @Override
    public void insert(Coffee item) {
        cache.put(item.getCoffeeId(),item);
        handler.insert(item);
    }
}

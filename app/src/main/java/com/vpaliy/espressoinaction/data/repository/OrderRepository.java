package com.vpaliy.espressoinaction.data.repository;

import android.support.annotation.NonNull;

import com.vpaliy.espressoinaction.data.cache.CacheStore;
import com.vpaliy.espressoinaction.data.local.DataHandler;
import com.vpaliy.espressoinaction.domain.IRepository;
import com.vpaliy.espressoinaction.domain.model.Order;
import java.util.List;
import rx.Observable;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class OrderRepository implements IRepository<Order> {

    private CacheStore<Order> cache;
    private DataHandler<Order> handler;

    @Inject
    public OrderRepository(@NonNull DataHandler<Order> handler,
                           @NonNull CacheStore<Order> cache){
        this.handler=handler;
        this.cache=cache;
    }

    @Override
    public Observable<List<Order>> getAll() {
        return Observable.fromCallable(()->handler.fetchAll())
                .doOnNext(this::cache);
    }

    @Override
    public void delete(Order item) {
        handler.delete(item);
    }

    private void cache(List<Order> orders){
        if(orders!=null){
            orders.forEach(order->cache.put(order.getOrderId(),order));
        }
    }

    @Override
    public Observable<Order> getById(int id) {
        if(cache.isInCache(id)){
            return Observable.just(cache.get(id));
        }
        return Observable.fromCallable(()->handler.fetchById(id));
    }

    @Override
    public void insert(Order item) {
        cache.put(item.getOrderId(),item);
        handler.insert(item);
    }
}

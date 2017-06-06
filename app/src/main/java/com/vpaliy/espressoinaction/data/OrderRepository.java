package com.vpaliy.espressoinaction.data;

import com.vpaliy.espressoinaction.data.cache.Cache;
import com.vpaliy.espressoinaction.data.local.DataHandler;
import com.vpaliy.espressoinaction.domain.IRepository;
import com.vpaliy.espressoinaction.domain.model.Order;
import java.util.List;
import rx.Observable;
import javax.inject.Inject;

public class OrderRepository implements IRepository<Order> {

    private Cache<Order> cache;
    private DataHandler<Order> handler;

    @Inject
    public OrderRepository(DataHandler<Order> handler,
                           Cache<Order> cache){
        this.handler=handler;
        this.cache=cache;
    }

    @Override
    public Observable<List<Order>> getAll() {
        return Observable.fromCallable(()->handler.fetchAll());
    }

    @Override
    public Observable<Order> getById(int id) {
        return Observable.fromCallable(()->handler.fetchById(id));
    }

    @Override
    public void insert(Order item) {
        handler.insert(item);
    }
}

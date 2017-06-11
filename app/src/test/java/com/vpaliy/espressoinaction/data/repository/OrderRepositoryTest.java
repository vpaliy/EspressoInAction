package com.vpaliy.espressoinaction.data.repository;

import com.vpaliy.espressoinaction.data.cache.CacheStore;
import com.vpaliy.espressoinaction.data.local.DataHandler;
import com.vpaliy.espressoinaction.domain.model.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.List;
import rx.schedulers.Schedulers;
import static com.vpaliy.espressoinaction.common.FakeDataProvider.FAKE_ID;
import static com.vpaliy.espressoinaction.common.FakeDataProvider.provideOrder;
import static com.vpaliy.espressoinaction.common.FakeDataProvider.provideOrderList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class OrderRepositoryTest {

    @Mock
    private CacheStore<Order> cache;

    @Mock
    private DataHandler<Order> handler;

    @InjectMocks
    private OrderRepository repository;

    @Test
    public void getsAllOrdersAndPutsItInCache(){
        List<Order> orderList=provideOrderList();
        when(handler.fetchAll()).thenReturn(orderList);
        repository.getAll()
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe(this::shouldBeNotNull);
        verify(handler).fetchAll();
        verify(cache,times(orderList.size())).put(anyInt(), any(Order.class));
    }

    @Test
    public void getsOrderByIdWhenItIsNotInCache(){
        when(handler.fetchById(FAKE_ID)).thenReturn(provideOrder());
        when(cache.isInCache(anyInt())).thenReturn(false);
        repository.getById(FAKE_ID)
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe(this::shouldBeNotNull);
        verify(cache).isInCache(eq(FAKE_ID));
        verify(handler).fetchById(FAKE_ID);
    }

    @Test
    public void deletesOrder(){
        Order order=provideOrder();
        repository.delete(order);

        verify(handler).delete(order);
    }

    @Test
    public void getOrderByIdFromCache(){
        when(cache.isInCache(FAKE_ID)).thenReturn(true);
        when(cache.get(FAKE_ID)).thenReturn(provideOrder());
        repository.getById(FAKE_ID)
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe(this::shouldBeNotNull);
        verify(cache).isInCache(eq(FAKE_ID));
        verify(cache).get(FAKE_ID);
    }

    @Test
    public void insertsOrderIntoCacheAndHandler(){
        Order order=provideOrder();
        repository.insert(order);

        verify(cache).put(order.getOrderId(),order);
        verify(handler).insert(order);
    }

    private void shouldBeNotNull(Object object){
        assertTrue(object!=null);
    }
}

package com.vpaliy.espressoinaction.data.repository;


import com.vpaliy.espressoinaction.data.cache.CacheStore;
import com.vpaliy.espressoinaction.data.local.DataHandler;
import com.vpaliy.espressoinaction.domain.model.Coffee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.List;
import rx.schedulers.Schedulers;
import static com.vpaliy.espressoinaction.common.FakeDataProvider.FAKE_ID;
import static com.vpaliy.espressoinaction.common.FakeDataProvider.provideCoffee;
import static com.vpaliy.espressoinaction.common.FakeDataProvider.provideCoffeeList;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CoffeeRepositoryTest {

    @Mock
    private CacheStore<Coffee> cache;

    @Mock
    private DataHandler<Coffee> handler;

    @InjectMocks
    private CoffeeRepository repository;

    @Test
    public void getsAllCoffeesAndPutsItInCache(){
        List<Coffee> coffeeList=provideCoffeeList();
        when(handler.fetchAll()).thenReturn(coffeeList);
        repository.getAll()
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe(this::shouldBeNotNull);
        verify(handler).fetchAll();
        verify(cache,times(coffeeList.size())).put(anyInt(), any(Coffee.class));
    }

    @Test
    public void getsCoffeeByIdWhenItIsNotInCache(){
        when(handler.fetchById(FAKE_ID)).thenReturn(provideCoffee());
        when(cache.isInCache(anyInt())).thenReturn(false);
        repository.getById(FAKE_ID)
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe(this::shouldBeNotNull);
        verify(cache).isInCache(eq(FAKE_ID));
        verify(handler).fetchById(FAKE_ID);
    }

    @Test
    public void getCoffeeByIdFromCache(){
        when(cache.isInCache(FAKE_ID)).thenReturn(true);
        when(cache.get(FAKE_ID)).thenReturn(provideCoffee());
        repository.getById(FAKE_ID)
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe(this::shouldBeNotNull);
        verify(cache).isInCache(eq(FAKE_ID));
        verify(cache).get(FAKE_ID);
    }

    @Test
    public void deletesCoffee(){
        Coffee coffee=provideCoffee();
        repository.delete(coffee);

        verify(handler).delete(coffee);
    }

    @Test
    public void insertsCoffeeIntoCacheAndHandler(){
        Coffee coffee=provideCoffee();
        repository.insert(coffee);

        verify(cache).put(coffee.getCoffeeId(),coffee);
        verify(handler).insert(coffee);
    }

    private void shouldBeNotNull(Object object){
        assertTrue(object!=null);
    }
}

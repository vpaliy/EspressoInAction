package com.vpaliy.espressoinaction.di;

import com.vpaliy.espressoinaction.common.scheduler.BaseSchedulerProvider;
import com.vpaliy.espressoinaction.common.scheduler.ImmediateSchedulerProvider;
import com.vpaliy.espressoinaction.data.repository.CoffeeRepository;
import com.vpaliy.espressoinaction.data.repository.OrderRepository;
import com.vpaliy.espressoinaction.domain.IRepository;
import com.vpaliy.espressoinaction.domain.model.Coffee;
import com.vpaliy.espressoinaction.domain.model.Order;
import org.mockito.Mockito;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class MockDataModule {

    @Singleton
    @Provides
    IRepository<Coffee> provideCoffeeIRepository(){
        return Mockito.mock(CoffeeRepository.class);
    }

    @Singleton
    @Provides
    IRepository<Order> provideOrderIRepository(){
        return Mockito.mock(OrderRepository.class);
    }

    @Singleton
    @Provides
    BaseSchedulerProvider provideScheduler(){
        return new ImmediateSchedulerProvider();
    }
}

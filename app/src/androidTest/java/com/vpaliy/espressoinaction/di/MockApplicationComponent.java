package com.vpaliy.espressoinaction.di;

import android.content.Context;

import com.vpaliy.espressoinaction.common.scheduler.BaseSchedulerProvider;
import com.vpaliy.espressoinaction.di.component.ApplicationComponent;
import com.vpaliy.espressoinaction.di.module.ApplicationModule;
import com.vpaliy.espressoinaction.di.module.DataModule;
import com.vpaliy.espressoinaction.domain.IRepository;
import com.vpaliy.espressoinaction.domain.model.Coffee;
import com.vpaliy.espressoinaction.domain.model.Order;
import com.vpaliy.espressoinaction.presentation.bus.RxBus;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MockDataModule.class, ApplicationModule.class})
public interface MockApplicationComponent extends ApplicationComponent{
    IRepository<Coffee> coffeeRepository();
    IRepository<Order> orderRepository();
    BaseSchedulerProvider scheduler();
}

package com.vpaliy.espressoinaction.di.module;

import com.vpaliy.espressoinaction.di.scope.ViewScope;
import com.vpaliy.espressoinaction.presentation.mvp.contract.CoffeeOrderContract;
import com.vpaliy.espressoinaction.presentation.mvp.contract.CoffeesContract;
import com.vpaliy.espressoinaction.presentation.mvp.contract.OrdersContract;
import com.vpaliy.espressoinaction.presentation.mvp.presenter.CoffeeOrderPresenter;
import com.vpaliy.espressoinaction.presentation.mvp.presenter.CoffeesPresenter;
import com.vpaliy.espressoinaction.presentation.mvp.presenter.OrdersPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @ViewScope
    @Provides
    CoffeesContract.Presenter coffeesPresenter(CoffeesPresenter presenter){
        return presenter;
    }

    @ViewScope
    @Provides
    OrdersContract.Presenter ordersPresenter(OrdersPresenter presenter){
        return presenter;
    }

    @ViewScope
    @Provides
    CoffeeOrderContract.Presenter orderPresenter(CoffeeOrderPresenter presenter){
        return presenter;
    }
}

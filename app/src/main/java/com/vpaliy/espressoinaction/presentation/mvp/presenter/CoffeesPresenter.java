package com.vpaliy.espressoinaction.presentation.mvp.presenter;

import android.support.annotation.NonNull;

import com.vpaliy.espressoinaction.common.scheduler.BaseSchedulerProvider;
import com.vpaliy.espressoinaction.domain.IRepository;
import com.vpaliy.espressoinaction.domain.model.Coffee;
import com.vpaliy.espressoinaction.presentation.coffees.CoffeesContract.Presenter;
import com.vpaliy.espressoinaction.presentation.coffees.CoffeesContract.View;
import java.util.List;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

public class CoffeesPresenter implements Presenter{

    private View view;
    private IRepository<Coffee> repository;
    private BaseSchedulerProvider schedulerProvider;
    private CompositeSubscription subscriptions;

    @Inject
    public CoffeesPresenter(IRepository<Coffee> repository,
                            BaseSchedulerProvider schedulerProvider){
        this.repository=repository;
        this.schedulerProvider=schedulerProvider;
        this.subscriptions=new CompositeSubscription();
    }

    @Override
    public void start() {
        subscriptions.add(repository.getAll()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::process));
    }

    private void process(List<Coffee> coffeeList){
        view.showCoffeeList(coffeeList);
    }

    @Override
    public void stop() {
        subscriptions.clear();
    }

    @Override
    public void onCoffeeSelected(int id) {

    }

    @Override
    public void attachView(@NonNull View view) {
        this.view=view;
    }
}

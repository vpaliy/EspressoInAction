package com.vpaliy.espressoinaction.presentation.mvp.presenter;

import com.vpaliy.espressoinaction.common.scheduler.BaseSchedulerProvider;
import com.vpaliy.espressoinaction.di.scope.ViewScope;
import com.vpaliy.espressoinaction.domain.IRepository;
import com.vpaliy.espressoinaction.domain.model.Coffee;
import com.vpaliy.espressoinaction.domain.model.MilkType;
import com.vpaliy.espressoinaction.domain.model.Order;
import com.vpaliy.espressoinaction.domain.model.SizeType;
import com.vpaliy.espressoinaction.domain.model.Sweetness;
import com.vpaliy.espressoinaction.presentation.mvp.contract.CoffeeOrderContract;
import com.vpaliy.espressoinaction.presentation.mvp.contract.CoffeeOrderContract.View;
import java.util.Collections;
import java.util.Date;
import android.support.annotation.NonNull;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.subscriptions.CompositeSubscription;

@ViewScope
public class CoffeeOrderPresenter implements CoffeeOrderContract.Presenter {

    private IRepository<Coffee> coffeeIRepository;
    private IRepository<Order> orderIRepository;
    private BaseSchedulerProvider schedulerProvider;
    private CompositeSubscription subscriptions;
    private View view;
    private Coffee coffee;
    private Order order;

    @Inject
    public CoffeeOrderPresenter(IRepository<Coffee> coffeeIRepository,
                                IRepository<Order> orderIRepository,
                                BaseSchedulerProvider schedulerProvider){
        this.coffeeIRepository=coffeeIRepository;
        this.orderIRepository=orderIRepository;
        this.schedulerProvider=schedulerProvider;
        this.subscriptions=new CompositeSubscription();
        this.coffee=new Coffee();
        this.order=new Order();
    }

    @Override
    public void attachView(@NonNull View view) {
        this.view=view;
    }

    @Override
    public void onMilkTypeSelected(MilkType milkType) {
        this.coffee.setMilkType(milkType);
    }

    @Override
    public void onPickUpTimeSelected(Date date) {
        this.order.setPickUpTime(date);
    }

    @Override
    public void onSizeTypeSelected(SizeType sizeType) {
        this.coffee.setSizeType(sizeType);
        if(sizeType!=SizeType.SMALL) {
            view.appendSizeCharge(coffee.getPrice(),sizeType.price);
        }else{
            view.showUpdatedPrice(coffee.getPrice());
        }
    }

    @Override
    public void onSweetnessTypeSelected(Sweetness sweetness) {
        this.coffee.setSweetness(sweetness);
    }

    @Override
    public void onFinish() {
        this.order.setCoffees(Collections.singletonList(coffee));
        orderIRepository.insert(order);
        coffee.setPrice(coffee.getPrice()+coffee.getSizeType().price);
        view.showCustomizedCoffee(coffee);
    }

    @Override
    public void start(int id) {
        subscriptions.add(coffeeIRepository.getById(id)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(this::process));
    }

    private void process(Coffee coffee){
        this.coffee=coffee;
        view.showCoffee(coffee);
    }

    @Override
    public void stop() {
        subscriptions.clear();
    }
}

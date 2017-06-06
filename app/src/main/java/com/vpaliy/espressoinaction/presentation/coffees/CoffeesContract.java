package com.vpaliy.espressoinaction.presentation.coffees;

import android.support.annotation.NonNull;

import com.vpaliy.espressoinaction.domain.model.Coffee;
import com.vpaliy.espressoinaction.presentation.BasePresenter;
import com.vpaliy.espressoinaction.presentation.BaseView;

import java.util.List;

public interface CoffeesContract {

    interface Presenter extends BasePresenter<View>{
        void attachView(@NonNull View view);
        void start();
        void stop();
        void onCoffeeSelected(int id);
    }

    interface View extends BaseView<Presenter>{
        void attachPresenter(@NonNull Presenter presenter);
        void showCoffeeList(@NonNull List<Coffee> coffeeList);
        void showMessage(String message);
    }
}

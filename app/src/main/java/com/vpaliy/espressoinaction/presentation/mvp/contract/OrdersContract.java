package com.vpaliy.espressoinaction.presentation.mvp.contract;

import android.support.annotation.NonNull;

import com.vpaliy.espressoinaction.domain.model.Order;
import com.vpaliy.espressoinaction.presentation.mvp.BasePresenter;
import com.vpaliy.espressoinaction.presentation.mvp.BaseView;

import java.util.List;

public interface OrdersContract {

    interface Presenter extends BasePresenter<View> {
        void attachView(@NonNull View view);
        void start();
        void stop();
        void cancelOrder(Order order);
        void onOrderSelected(int id);
    }

    interface View extends BaseView<Presenter> {
        void attachPresenter(@NonNull Presenter presenter);
        void showOrderList(@NonNull List<Order> coffeeList);
        void showMessage(String message);
    }
}

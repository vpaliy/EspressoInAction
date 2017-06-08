package com.vpaliy.espressoinaction.presentation.mvp.contract;

import android.support.annotation.NonNull;

import com.vpaliy.espressoinaction.domain.model.Coffee;
import com.vpaliy.espressoinaction.domain.model.MilkType;
import com.vpaliy.espressoinaction.domain.model.SizeType;
import com.vpaliy.espressoinaction.domain.model.Sweetness;
import com.vpaliy.espressoinaction.presentation.mvp.BasePresenter;
import com.vpaliy.espressoinaction.presentation.mvp.BaseView;

import java.util.Date;

public interface CoffeeOrderContract {

    interface Presenter extends BasePresenter<View> {
        void attachView(@NonNull View view);
        void onMilkTypeSelected(MilkType milkType);
        void onSizeTypeSelected(SizeType sizeType);
        void onSweetnessTypeSelected(Sweetness sweetness);
        void onPickUpTimeSelected(Date date);
        void onFinish();
        void start(int id);
        void stop();
    }

    interface View extends BaseView<Presenter> {
        void attachPresenter(@NonNull Presenter presenter);
        void showCoffee(Coffee coffee);
        void appendSizeCharge(double original, double additional);
        void showUpdatedPrice(double price);
        void showMessage(String message);
    }
}

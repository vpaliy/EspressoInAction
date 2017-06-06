package com.vpaliy.espressoinaction.presentation.mvp;


import android.support.annotation.NonNull;

public interface BasePresenter<V extends BaseView> {
    void attachView(@NonNull V view);
}
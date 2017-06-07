package com.vpaliy.espressoinaction.presentation.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vpaliy.espressoinaction.CoffeeApp;
import com.vpaliy.espressoinaction.R;
import com.vpaliy.espressoinaction.di.component.DaggerViewComponent;
import com.vpaliy.espressoinaction.di.module.PresenterModule;
import com.vpaliy.espressoinaction.domain.model.Coffee;
import com.vpaliy.espressoinaction.presentation.mvp.contract.CoffeeOrderContract;
import com.vpaliy.espressoinaction.presentation.mvp.contract.CoffeeOrderContract.Presenter;

import javax.inject.Inject;

public class CoffeeOrderFragment extends BaseFragment
        implements CoffeeOrderContract.View{

    private Presenter presenter;

    @Override
    void initializeDependencies() {
        DaggerViewComponent.builder()
                .presenterModule(new PresenterModule())
                .applicationComponent(CoffeeApp.app().component())
                .build().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_order_form,container,false);
        bind(root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showCoffee(Coffee coffee) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Inject
    @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }
}

package com.vpaliy.espressoinaction.presentation.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.vpaliy.espressoinaction.CoffeeApp;
import com.vpaliy.espressoinaction.R;
import com.vpaliy.espressoinaction.di.component.DaggerViewComponent;
import com.vpaliy.espressoinaction.di.module.PresenterModule;
import com.vpaliy.espressoinaction.domain.model.Order;
import com.vpaliy.espressoinaction.presentation.mvp.contract.OrdersContract.Presenter;
import com.vpaliy.espressoinaction.presentation.mvp.contract.OrdersContract;
import com.vpaliy.espressoinaction.presentation.ui.adapter.OrderAdapter;
import java.util.List;
import javax.inject.Inject;


public class OrdersFragment extends BaseFragment
            implements OrdersContract.View {

    private Presenter presenter;
    private OrderAdapter adapter;

    @Inject
    @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_layout,container,false);
        bind(root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(view!=null){
            adapter=new OrderAdapter(getContext(),rxBus);
            list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
            list.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));
            list.setAdapter(adapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    void initializeDependencies() {
        DaggerViewComponent.builder()
                .applicationComponent(CoffeeApp.app().component())
                .presenterModule(new PresenterModule())
                .build().inject(this);
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    public void showOrderList(@NonNull List<Order> coffeeList) {
        adapter.setData(coffeeList);
    }


}

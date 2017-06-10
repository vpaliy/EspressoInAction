package com.vpaliy.espressoinaction.presentation.ui.fragment;

import android.os.Bundle;

import com.vpaliy.espressoinaction.CoffeeApp;
import com.vpaliy.espressoinaction.R;
import com.vpaliy.espressoinaction.di.component.DaggerViewComponent;
import com.vpaliy.espressoinaction.di.module.PresenterModule;
import com.vpaliy.espressoinaction.domain.model.Coffee;
import com.vpaliy.espressoinaction.presentation.bus.RxBus;
import com.vpaliy.espressoinaction.presentation.mvp.contract.CoffeesContract;
import com.vpaliy.espressoinaction.presentation.mvp.contract.CoffeesContract.Presenter;
import com.vpaliy.espressoinaction.presentation.ui.adapter.CoffeeAdapter;
import com.vpaliy.espressoinaction.presentation.view.PaddingDecoration;
import java.util.List;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import butterknife.BindView;
import javax.inject.Inject;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

public class CoffeesFragment extends BaseFragment
        implements CoffeesContract.View {

    private Presenter presenter;

    private CoffeeAdapter adapter;

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
    void initializeDependencies() {
        DaggerViewComponent.builder()
                .applicationComponent(CoffeeApp.app().component())
                .presenterModule(new PresenterModule())
                .build().inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(view!=null){
            adapter=new CoffeeAdapter(getContext(),rxBus);
            list.addItemDecoration(new PaddingDecoration(getContext()));
            list.setAdapter(adapter);
            presenter.start();
        }
    }

    @Override
    public void showCoffeeList(@NonNull List<Coffee> coffeeList) {
        adapter.setData(coffeeList);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter!=null) adapter.resume();
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Inject
    @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }
}

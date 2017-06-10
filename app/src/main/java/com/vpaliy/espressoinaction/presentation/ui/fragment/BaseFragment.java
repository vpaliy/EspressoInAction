package com.vpaliy.espressoinaction.presentation.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.vpaliy.espressoinaction.R;
import com.vpaliy.espressoinaction.presentation.bus.RxBus;
import javax.inject.Inject;

public abstract class BaseFragment extends Fragment {

    protected Unbinder unbinder;

    @Inject
    protected RxBus rxBus;

    @BindView(R.id.coffee_recycler)
    protected RecyclerView list;


    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        initializeDependencies();
    }

    void bind(View root){
        if(root!=null){
            unbinder= ButterKnife.bind(this,root);
        }
    }

    void bind(Activity activity){
        if(activity!=null){
            unbinder= ButterKnife.bind(this,activity);
        }
    }

    abstract void initializeDependencies();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(unbinder!=null) unbinder.unbind();
    }
}
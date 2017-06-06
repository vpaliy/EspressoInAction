package com.vpaliy.espressoinaction.presentation.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.BindBool;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
public abstract class BaseFragment extends Fragment {

    protected Unbinder unbinder;


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
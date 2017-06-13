package com.vpaliy.espressoinaction.presentation.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.annotation.CallSuper;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.roughike.bottombar.BottomBar;
import com.vpaliy.espressoinaction.CoffeeApp;
import com.vpaliy.espressoinaction.R;
import com.vpaliy.espressoinaction.presentation.bus.RxBus;
import com.vpaliy.espressoinaction.presentation.bus.event.OnCoffeeClicked;
import com.vpaliy.espressoinaction.presentation.ui.adapter.CoffeePagerAdapter;
import com.vpaliy.espressoinaction.presentation.ui.fragment.CoffeeOrderFragment;
import com.vpaliy.espressoinaction.presentation.view.CoffeePager;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.pager)
    protected CoffeePager pager;

    @BindView(R.id.actionBar)
    protected Toolbar actionBar;

    @BindView(R.id.bottom_navigation)
    protected BottomBar bottomNavigation;

    @Inject
    protected RxBus rxBus;

    private CompositeDisposable disposables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        inject();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUI();
    }

    private void inject(){
        disposables=new CompositeDisposable();
        CoffeeApp.app().component().inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        disposables.clear();
        disposables.add(rxBus.asFlowable()
            .subscribe(this::handleEvent));
    }

    private void handleEvent(Object object){
        if(object!=null){
            if(object instanceof OnCoffeeClicked){
                OnCoffeeClicked event=OnCoffeeClicked.class.cast(object);
                prepareOrder(event);
            }
        }
    }

    private void prepareOrder(OnCoffeeClicked event){
        CoffeeOrderFragment.newInstance(event.convertToBundle())
                .show(getSupportFragmentManager(), null);

    }

    private void setUI(){
        setPager();
        setBottomNavigation();
        actionBar.setTitleTextColor(ContextCompat.getColor(this,R.color.colorSugar));
    }

    private void setPager(){
        CoffeePagerAdapter adapter=new CoffeePagerAdapter(getSupportFragmentManager(),this);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(2);
    }

    private void setBottomNavigation(){
        bottomNavigation.setOnTabSelectListener((tabId -> {
            pager.animate()
                    .alpha(0)
                    .setDuration(200)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            switch (tabId){
                                case R.id.coffee:
                                    actionBar.setTitle(R.string.coffee_tab);
                                    pager.setCurrentItem(0,false);
                                    break;
                                case R.id.orders:
                                    actionBar.setTitle(R.string.order_tab);
                                    pager.setCurrentItem(1,false);
                                    break;
                                case R.id.info:
                                    actionBar.setTitle(R.string.info_tab);
                                    pager.setCurrentItem(2,false);
                            }
                            pager.animate()
                                    .alpha(1.f)
                                    .setDuration(200)
                                    .setListener(null).start();
                        }
                    }).start();
        }));
    }

    @Override
    protected void onStop(){
        super.onStop();
        disposables.clear();
    }
}

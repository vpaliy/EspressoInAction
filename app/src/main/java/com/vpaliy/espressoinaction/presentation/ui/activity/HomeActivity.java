package com.vpaliy.espressoinaction.presentation.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import com.roughike.bottombar.BottomBar;
import com.vpaliy.espressoinaction.R;
import com.vpaliy.espressoinaction.presentation.ui.adapter.CoffeePagerAdapter;
import com.vpaliy.espressoinaction.presentation.view.CoffeePager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.pager)
    protected CoffeePager pager;

    @BindView(R.id.actionBar)
    protected Toolbar actionBar;

    @BindView(R.id.bottom_navigation)
    protected BottomBar bottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUI();
    }

    private void setUI(){
        setPager();
        setBottomNavigation();
    }

    private void setPager(){
        CoffeePagerAdapter adapter=new CoffeePagerAdapter(getSupportFragmentManager(),this);
        pager.setAdapter(adapter);
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
}

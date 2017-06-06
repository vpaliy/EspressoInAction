package com.vpaliy.espressoinaction.presentation.ui.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.vpaliy.espressoinaction.R;
import com.vpaliy.espressoinaction.presentation.ui.fragment.CoffeesFragment;
import com.vpaliy.espressoinaction.presentation.ui.fragment.InfoFragment;
import com.vpaliy.espressoinaction.presentation.ui.fragment.OrdersFragment;

public class CoffeePagerAdapter  extends FragmentStatePagerAdapter {

    private Context context;

    public CoffeePagerAdapter(FragmentManager manager, Context context){
        super(manager);
        this.context=context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return context.getString(R.string.coffee_tab);
            case 1:
                return context.getString(R.string.order_tab);
            case 2:
                return context.getString(R.string.info_tab);
        }
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new CoffeesFragment();
            case 1:
                return new OrdersFragment();
            case 2:
                return new InfoFragment();
        }
        return null;
    }

}
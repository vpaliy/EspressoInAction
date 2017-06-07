package com.vpaliy.espressoinaction.presentation.bus.event;

import android.os.Bundle;

import com.vpaliy.espressoinaction.common.Constants;
import com.vpaliy.espressoinaction.domain.model.Coffee;

public class OnCoffeeClicked {

    public final Coffee coffee;

    private OnCoffeeClicked(Coffee coffee){
        this.coffee=coffee;
    }

    public static OnCoffeeClicked click(Coffee coffee){
        return new OnCoffeeClicked(coffee);
    }

    public Bundle convertToBundle(){
        Bundle bundle=new Bundle();
        bundle.putInt(Constants.EXTRA_COFFEE_ID,coffee.getCoffeeId());
        return bundle;
    }
}

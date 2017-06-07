package com.vpaliy.espressoinaction.presentation.bus.event;

import com.vpaliy.espressoinaction.domain.model.Coffee;

public class OnCoffeeClicked {

    public final Coffee coffee;

    private OnCoffeeClicked(Coffee coffee){
        this.coffee=coffee;
    }

    public static OnCoffeeClicked click(Coffee coffee){
        return new OnCoffeeClicked(coffee);
    }
}

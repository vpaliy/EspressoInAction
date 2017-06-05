package com.vpaliy.espressoinaction.model;


public enum Sweetness {

    NOT_SWEET(0),
    SLIGHTLY_SWEET(25),
    HALF_SWEET(50),
    MODERATELY_SWEET(75),
    FULL_SWEETNESS(100);

    public final double percent;

    Sweetness(double percent){
        this.percent=percent;
    }
}

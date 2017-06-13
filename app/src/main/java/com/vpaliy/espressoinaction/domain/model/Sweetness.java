package com.vpaliy.espressoinaction.domain.model;


public enum Sweetness {

    NOT_SWEET(5),
    SLIGHTLY_SWEET(35),
    HALF_SWEET(50),
    MODERATELY_SWEET(75),
    FULL_SWEETNESS(100);

    public final float percent;

    Sweetness(float percent){
        this.percent=percent;
    }
}

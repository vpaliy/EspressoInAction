package com.vpaliy.espressoinaction.domain.model;

public enum SizeType {
    SMALL(0),
    MEDIUM(1),
    LARGE(2),
    TALL(3);
    SizeType(int price){
        this.price=price;
    }
    public final int price;
}

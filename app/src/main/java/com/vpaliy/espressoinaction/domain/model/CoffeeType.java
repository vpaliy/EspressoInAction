package com.vpaliy.espressoinaction.domain.model;

public enum CoffeeType {
    LATTE("Latte"),
    MOCHA("Mocha"),
    AMERICANO("Americano"),
    ESPRESSO("Espresso"),
    CAPPUCCINO("Cappuccino"),
    CARAMEL_FRAPPUCCINO("Caramel Frappuccino"),
    ESPRESSO_FRAPPUCCINO("Espresso Frappuccino");

    CoffeeType(String name){
        this.name=name;
    }
    public final String name;
}

package com.vpaliy.espressoinaction.data.local;


import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

import static com.vpaliy.espressoinaction.data.local.CoffeeDatabase.DATABASE_VERSION;

@Database(version =DATABASE_VERSION)
public class CoffeeDatabase {

    static final int DATABASE_VERSION=3;

    @Table(CoffeeContract.CoffeeColumns.class)
    public static final String COFFEES="coffees";

    @Table(CoffeeContract.OrderColumns.class)
    public static final String ORDERS="orders";

    @Table(CoffeeContract.CoffeeColumns.class)
    public static final String ORDERED_COFFEES="ordered_coffees";
}

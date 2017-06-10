package com.vpaliy.espressoinaction.data.local;


import android.net.Uri;
import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;
import static com.vpaliy.espressoinaction.data.local.CoffeeContract.PATH_COFFEE;
import static com.vpaliy.espressoinaction.data.local.CoffeeContract.PATH_ORDER;
import static com.vpaliy.espressoinaction.data.local.CoffeeContract.CoffeeColumns;
import static com.vpaliy.espressoinaction.data.local.CoffeeContract.OrderColumns;

@ContentProvider(authority = CoffeeProvider.AUTHORITY,
                 database = CoffeeDatabase.class)
public class CoffeeProvider {

    public static final String AUTHORITY = "com.vpaliy.espressoinaction.CoffeeProvider";

    @TableEndpoint(table=CoffeeDatabase.COFFEES)
    public static class Coffees{

        @ContentUri(path = PATH_COFFEE,
                 type = "vnd.android.cursor.dir/coffees",
                 defaultSort = CoffeeColumns.COFFEE_COFFEE_TYPE+" ASC")
        public static final Uri COFFEES = Uri.parse("content://" + AUTHORITY +"/"+ PATH_COFFEE);

        @InexactContentUri(
                path = PATH_COFFEE+"/#",
                name = CoffeeColumns.COFFEE_ID,
                type = "vnd.android.cursor.item/coffees",
                whereColumn =CoffeeColumns.COFFEE_ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return Uri.parse("content://" + AUTHORITY + "/"+PATH_COFFEE+"/" + id);
        }
    }

    @TableEndpoint(table=CoffeeDatabase.ORDERS)
    public static class Orders{
        @ContentUri(path = PATH_ORDER,
                type = "vnd.android.cursor.dir/order",
                defaultSort = OrderColumns.ORDER_ID+" ASC")
        public static final Uri ORDERS = Uri.parse("content://" + AUTHORITY + "/"+PATH_ORDER);

        @InexactContentUri(
                path = PATH_ORDER+"/#",
                name = OrderColumns.ORDER_ID,
                type = "vnd.android.cursor.item/orders",
                whereColumn = OrderColumns.ORDER_ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return Uri.parse("content://" + AUTHORITY + "/"+PATH_ORDER+"/" + id);
        }
    }

}

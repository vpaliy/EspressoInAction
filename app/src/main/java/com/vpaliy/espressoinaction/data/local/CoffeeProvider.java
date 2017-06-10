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
import static com.vpaliy.espressoinaction.data.local.CoffeeContract.PATH_ORDERED_COFFEE;

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
                join = "INNER JOIN ordered_coffees ON ordered_coffees.coffee_id=orders.ref_coffee_id",
                type = "vnd.android.cursor.dir/order",
                defaultSort = OrderColumns.ORDER_ID+" ASC")
        public static final Uri ORDERS = Uri.parse("content://" + AUTHORITY + "/"+PATH_ORDER);

        @InexactContentUri(
                path = PATH_ORDER+"/#",
                name = OrderColumns.ORDER_ID,
                join = "INNER JOIN ordered_coffees ON ordered_coffees.coffee_id=orders.ref_coffee_id",
                type = "vnd.android.cursor.item/orders",
                whereColumn = OrderColumns.ORDER_ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return Uri.parse("content://" + AUTHORITY + "/"+PATH_ORDER+"/" + id);
        }
    }

    @TableEndpoint(table=CoffeeDatabase.ORDERED_COFFEES)
    public static class OrderedCoffees{
        @ContentUri(path = PATH_ORDERED_COFFEE,
                type = "vnd.android.cursor.dir/ordered_coffees",
                defaultSort = CoffeeColumns.COFFEE_COFFEE_TYPE+" ASC")
        public static final Uri COFFEES = Uri.parse("content://" + AUTHORITY +"/"+ PATH_ORDERED_COFFEE);

        @InexactContentUri(
                path = PATH_ORDERED_COFFEE+"/#",
                name = CoffeeColumns.COFFEE_ID,
                type = "vnd.android.cursor.item/ordered_coffees",
                whereColumn =CoffeeColumns.COFFEE_ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return Uri.parse("content://" + AUTHORITY + "/"+PATH_ORDERED_COFFEE+"/" + id);
        }
    }

}

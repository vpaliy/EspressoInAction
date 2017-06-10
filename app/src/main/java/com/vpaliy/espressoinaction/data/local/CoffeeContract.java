package com.vpaliy.espressoinaction.data.local;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.References;
import static com.vpaliy.espressoinaction.data.local.CoffeeDatabase.COFFEES;

public class CoffeeContract {


    public static final String PATH_COFFEE="coffees";
    public static final String PATH_ORDER="orders";

    public interface CoffeeColumns{
        @DataType(DataType.Type.INTEGER)
        @PrimaryKey
        @AutoIncrement
        String COFFEE_ID="coffee_id";

        @DataType(DataType.Type.REAL)
        String COFFEE_PRICE="coffee_price";

        @DataType(DataType.Type.TEXT)
        String COFFEE_SWEETNESS="coffee_sweetness";

        @DataType(DataType.Type.TEXT)
        String COFFEE_MILK_TYPE="coffee_milk_type";

        @DataType(DataType.Type.TEXT)
        String COFFEE_SIZE_TYPE="coffee_size_type";

        @DataType(DataType.Type.TEXT)
        String COFFEE_COFFEE_TYPE="coffee_coffee_type";

        @DataType(DataType.Type.TEXT)
        String COFFEE_IMAGE_URL="coffee_image_url";
    }

    public interface OrderColumns{
        @DataType(DataType.Type.INTEGER)
        @PrimaryKey
        @AutoIncrement
        String ORDER_ID="order_id";

        @DataType(DataType.Type.INTEGER)
        @References(table = COFFEES, column = CoffeeColumns.COFFEE_ID)
        String COFFEE_ID="ref_coffee_id";

        @DataType(DataType.Type.TEXT)
        String ORDER_PICK_UP_TIME="order_pick_up_time";

        @DataType(DataType.Type.TEXT)
        String ORDER_PICK_UP_DAY="order_pick_up_day";

        @DataType(DataType.Type.TEXT)
        String ORDER_NAME="order_name";
    }

}

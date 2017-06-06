package com.vpaliy.espressoinaction.data.local;

import android.content.ContentResolver;
import android.net.Uri;

import com.vpaliy.espressoinaction.R;
import com.vpaliy.espressoinaction.domain.model.Coffee;
import com.vpaliy.espressoinaction.domain.model.CoffeeType;
import com.vpaliy.espressoinaction.domain.model.MilkType;
import com.vpaliy.espressoinaction.domain.model.SizeType;
import com.vpaliy.espressoinaction.domain.model.Sweetness;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CoffeeHandler implements DataHandler<Coffee> {

    private ContentResolver contentResolver;

    @Inject
    public CoffeeHandler(ContentResolver contentResolver){
        this.contentResolver=contentResolver;
    }

    @Override
    public Coffee fetchById(int id) {
        return null;
    }

    @Override
    public void insert(Coffee item) {

    }

    @Override
    public List<Coffee> fetchAll() {
        List<Coffee> result=new ArrayList<>();
        for(CoffeeType type:CoffeeType.values()){
            Coffee coffee=new Coffee();
            coffee.setSizeType(SizeType.LARGE);
            coffee.setPrice(2.f);
            coffee.setMilkType(MilkType.NONFAT_MILK);
            coffee.setSweetness(Sweetness.FULL_SWEETNESS);
            coffee.setCoffeeId(1);
            coffee.setCoffeeType(type);
            switch (type){
                case AMERICANO:
                    coffee.setImageUrl(getUrl(R.drawable.americano_black).toString());
                    break;
                case ESPRESSO:
                    coffee.setImageUrl(getUrl(R.drawable.espresso).toString());
                    break;
                case LATTE:
                    coffee.setImageUrl(getUrl(R.drawable.latte).toString());
                    break;
                case MOCHA:
                    coffee.setImageUrl(getUrl(R.drawable.mocha).toString());
                    break;
                case CAPPUCCINO:
                    coffee.setImageUrl(getUrl(R.drawable.cap).toString());
                    break;
                case CARAMEL_FRAPPUCCINO:
                    coffee.setImageUrl(getUrl(R.drawable.caramel_frap).toString());
                    break;
                case ESPRESSO_FRAPPUCCINO:
                    coffee.setImageUrl(getUrl(R.drawable.espresso_frap).toString());
                    break;
            }
            result.add(0,coffee);
        }
        return result;
    }

    private Uri getUrl(int res){
        return Uri.parse("android.resource://com.vpaliy.espressoinaction/" + res);
    }

}

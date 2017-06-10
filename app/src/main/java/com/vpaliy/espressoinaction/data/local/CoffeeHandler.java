package com.vpaliy.espressoinaction.data.local;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.vpaliy.espressoinaction.R;
import com.vpaliy.espressoinaction.domain.model.Coffee;
import com.vpaliy.espressoinaction.domain.model.CoffeeType;
import com.vpaliy.espressoinaction.domain.model.MilkType;
import com.vpaliy.espressoinaction.domain.model.SizeType;
import com.vpaliy.espressoinaction.domain.model.Sweetness;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CoffeeHandler implements DataHandler<Coffee> {

    private static final String TAG=CoffeeHandler.class.getSimpleName();

    private ContentResolver contentResolver;

    @Inject
    public CoffeeHandler(ContentResolver contentResolver){
        this.contentResolver=contentResolver;
    }

    @Override
    public Coffee fetchById(int id) {
        Cursor cursor=contentResolver.query(CoffeeProvider.Coffees.withId(id),null,null,null,null);
        Coffee coffee=DatabaseUtils.toCoffee(cursor);
        if(cursor!=null) cursor.close();
        return coffee;
    }

    @Override
    public void insert(Coffee item) {
        contentResolver.insert(CoffeeProvider.Coffees.COFFEES,DatabaseUtils.toValues(item));
    }

    @Override
    public List<Coffee> fetchAll() {
        Cursor cursor=contentResolver.query(CoffeeProvider.Coffees.COFFEES,null,null,null,null);
        if(cursor!=null){
            if(cursor.getCount()>0){
                List<Coffee> result=new ArrayList<>(cursor.getCount());
                while(cursor.moveToNext()){
                    Coffee coffee=DatabaseUtils.toCoffee(cursor);
                    result.add(coffee);
                }
                if(!cursor.isClosed()) cursor.close();
                return result;
            }
        }
        List<Coffee> fakes=createFake();
        fakes.forEach(this::insert);
        return fakes;
    }

    private List<Coffee> createFake(){
        List<Coffee> result=new ArrayList<>();
        int id=0;
        Random random=new Random();
        for(CoffeeType type:CoffeeType.values()){
            Coffee coffee=new Coffee();
            coffee.setSizeType(SizeType.SMALL);
            int price=random.nextInt(8);
            if(price<=1) price++;
            coffee.setPrice(price);
            coffee.setMilkType(MilkType.NONE);
            coffee.setSweetness(Sweetness.FULL_SWEETNESS);
            coffee.setCoffeeId(id++);
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

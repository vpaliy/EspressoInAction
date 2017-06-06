package com.vpaliy.espressoinaction;

import android.app.Application;
import com.vpaliy.espressoinaction.di.component.ApplicationComponent;

public class CoffeeApp extends Application {

    private ApplicationComponent component;

    private static CoffeeApp app;
    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        initializeComponent();
    }

    public static CoffeeApp app(){
        return app;
    }

    public ApplicationComponent component(){
        return component;
    }

    private void initializeComponent(){

    }

}

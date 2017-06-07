package com.vpaliy.espressoinaction;

import android.app.Application;
import com.vpaliy.espressoinaction.di.component.ApplicationComponent;
import com.vpaliy.espressoinaction.di.component.DaggerApplicationComponent;
import com.vpaliy.espressoinaction.di.module.ApplicationModule;
import com.vpaliy.espressoinaction.di.module.DataModule;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class CoffeeApp extends Application {

    private ApplicationComponent component;
    private final static String ROBOTO_SLAB = "fonts/RobotoSlab-Regular.ttf";
    private static CoffeeApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        initializeComponent();
        configureDefaultFont(ROBOTO_SLAB);
    }

    private void configureDefaultFont(String robotoSlab) {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(robotoSlab)
                .setFontAttrId(R.attr.fontPath)
                .build());
    }
    public static CoffeeApp app(){
        return app;
    }

    public ApplicationComponent component(){
        return component;
    }

    private void initializeComponent(){
        component= DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .dataModule(new DataModule())
                .build();
    }

}

package com.vpaliy.espressoinaction.di.module;


import android.content.Context;

import com.vpaliy.espressoinaction.presentation.bus.RxBus;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private Context context;

    public ApplicationModule(Context context){
        this.context=context;
    }

    @Provides
    @Singleton
    Context provideContext(){
        return context;
    }

    @Provides
    @Singleton
    RxBus provideBus(){
        return new RxBus();
    }
}

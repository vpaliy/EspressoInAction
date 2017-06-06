package com.vpaliy.espressoinaction.presentation.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import com.vpaliy.espressoinaction.domain.model.Coffee;
import com.vpaliy.espressoinaction.presentation.bus.RxBus;

public class CoffeeAdapter extends AbstractAdapter<Coffee> {

    public CoffeeAdapter(@NonNull Context context,
                         @NonNull RxBus rxBus){
        super(context,rxBus);
    }

    @Override
    public AbstractViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    public class CoffeeViewHolder extends AbstractAdapter.AbstractViewHolder{

        public CoffeeViewHolder(View itemView){
            super(itemView);
        }

        @Override
        void onBind() {

        }
    }
}

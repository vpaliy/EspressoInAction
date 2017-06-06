package com.vpaliy.espressoinaction.presentation.ui.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import com.vpaliy.espressoinaction.domain.model.Order;
import com.vpaliy.espressoinaction.presentation.bus.RxBus;

public class OrderAdapter extends AbstractAdapter<Order> {

    public OrderAdapter(@NonNull Context context,
                        @NonNull RxBus rxBus) {
        super(context, rxBus);
    }

    @Override
    public AbstractViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    public class OrderViewHolder extends AbstractAdapter.AbstractViewHolder {

        public OrderViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        void onBind() {

        }
    }
}
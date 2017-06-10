package com.vpaliy.espressoinaction.presentation.ui.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.vpaliy.espressoinaction.R;
import com.vpaliy.espressoinaction.domain.model.Coffee;
import com.vpaliy.espressoinaction.domain.model.Order;
import com.vpaliy.espressoinaction.presentation.bus.RxBus;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderAdapter extends AbstractAdapter<Order> {

    public OrderAdapter(@NonNull Context context,
                        @NonNull RxBus rxBus) {
        super(context, rxBus);
    }

    @Override
    public AbstractViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root=inflater.inflate(R.layout.adapter_order_item,parent,false);
        return new OrderViewHolder(root);
    }


    public class OrderViewHolder extends AbstractAdapter<Order>.AbstractViewHolder {

        @BindView(R.id.coffee_image)
        ImageView image;

        @BindView(R.id.order_pick_day)
        TextView day;

        @BindView(R.id.order_pick_time)
        TextView time;

        @BindView(R.id.order_price)
        TextView price;

        @BindView(R.id.coffee_title)
        TextView coffeeTitle;

        OrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        void onBind() {
            Order order=at(getAdapterPosition());
            Coffee coffee=order.getCoffee();
            Glide.with(inflater.getContext())
                    .load(coffee.getImageUrl())
                    .centerCrop()
                    .into(image);
            coffeeTitle.setText(coffee.getCoffeeType().name);
            time.setText(order.getPickUpTime());
            day.setText(order.getPickUpDay());
            price.setText(String.format(Locale.US,"$ %.0f",coffee.getPrice()));
        }
    }
}
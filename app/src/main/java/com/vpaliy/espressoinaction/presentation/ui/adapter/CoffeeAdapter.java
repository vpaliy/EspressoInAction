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
import com.vpaliy.espressoinaction.presentation.bus.RxBus;
import com.vpaliy.espressoinaction.presentation.bus.event.OnCoffeeClicked;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CoffeeAdapter extends AbstractAdapter<Coffee> {

    private static final int UNLOCK_TIME=500;

    public CoffeeAdapter(@NonNull Context context,
                         @NonNull RxBus rxBus){
        super(context,rxBus);
    }

    @Override
    public AbstractViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root=inflater.inflate(R.layout.adapter_coffee_item,parent,false);
        return new CoffeeViewHolder(root);
    }

    public class CoffeeViewHolder
            extends AbstractAdapter<Coffee>.AbstractViewHolder
            implements View.OnClickListener{

        @BindView(R.id.image) ImageView image;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.price) TextView price;

        CoffeeViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(!isLocked()){
                lock();
                Coffee coffee=at(getAdapterPosition());
                rxBus.send(OnCoffeeClicked.click(coffee));
                unlockAfter(UNLOCK_TIME);
            }
        }

        @Override
        void onBind() {
            Coffee coffee=at(getAdapterPosition());
            String drawableUri=coffee.getImageUrl();
            Glide.with(inflater.getContext())
                    .load(drawableUri)
                    .centerCrop()
                    .into(image);
            name.setText(coffee.getCoffeeType().name);
            price.setText(String.format(Locale.US,"$%.0f",coffee.getPrice()));
        }
    }
}

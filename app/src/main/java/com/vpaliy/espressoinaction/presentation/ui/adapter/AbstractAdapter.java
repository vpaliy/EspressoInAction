package com.vpaliy.espressoinaction.presentation.ui.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;


import com.vpaliy.espressoinaction.presentation.bus.RxBus;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAdapter<T> extends RecyclerView.Adapter<AbstractAdapter<T>.AbstractViewHolder>{

    protected LayoutInflater inflater;
    protected RxBus rxBus;
    protected List<T> data;
    private boolean locked;
    private Handler handler=new Handler();

    public AbstractAdapter(@NonNull Context context,
                           @NonNull RxBus rxBus){
        this.inflater=LayoutInflater.from(context);
        this.rxBus=rxBus;
        this.data=new ArrayList<>();
    }

    public abstract class AbstractViewHolder extends RecyclerView.ViewHolder{
        public AbstractViewHolder(View itemView){
            super(itemView);
        }

        abstract void onBind();
    }

    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public boolean isLocked(){
        return locked;
    }

    public void lock(){
        locked=true;
    }

    protected void unlockAfter(long milliSec){
        handler.postDelayed(this::unlock,milliSec);
    }

    public void unlock(){
        locked=false;
    }

    public void resume(){
        unlock();
    }

    public T at(int index){
        return data.get(index);
    }

    public void removeAt(int index){
        if(data.size()>index){
            data.remove(index);
            notifyItemRemoved(index);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(AbstractViewHolder holder, int position) {
        holder.onBind();
    }
}
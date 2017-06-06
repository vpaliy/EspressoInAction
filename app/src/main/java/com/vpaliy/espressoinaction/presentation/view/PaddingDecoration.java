package com.vpaliy.espressoinaction.presentation.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vpaliy.espressoinaction.R;

public class PaddingDecoration extends RecyclerView.ItemDecoration {

    private final Rect paddingRect;

    public PaddingDecoration(Context context) {
        final int padding = (int) context.getResources().getDimension(R.dimen.coffee_margin);
        paddingRect = new Rect(padding, 0, padding, 0);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(paddingRect);
    }
}
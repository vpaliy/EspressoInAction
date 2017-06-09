package com.vpaliy.espressoinaction.presentation.ui.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.vpaliy.espressoinaction.R;
import com.vpaliy.espressoinaction.domain.model.MilkType;
import com.vpaliy.espressoinaction.domain.model.SizeType;
import com.vpaliy.espressoinaction.domain.model.Sweetness;

public final class ConvertUtils {
    private ConvertUtils(){
        throw new UnsupportedOperationException();
    }

    public static String getMilkTypeLabel(Context context,MilkType type){
        switch (type){
            case NONE:
                return context.getString(R.string.not_sweet_label);
            case ALMOND:
                return context.getString(R.string.almond_milk_label);
            case SOY:
                return context.getString(R.string.soy_milk_label);
            case COCONUT:
                return context.getString(R.string.coconut_milk_label);
            case WHOLE_MILK:
                return context.getString(R.string.whole_milk_label);
            default:
                throw new UnsupportedOperationException("Unknown milk type:"+type);
        }
    }

    public static Drawable getMilkImage(Context context, MilkType type){
        switch (type){
            case NONE:
                return ContextCompat.getDrawable(context,R.drawable.ic_almond);
            case ALMOND:
                return ContextCompat.getDrawable(context,R.drawable.ic_almond);
            case SOY:
                return ContextCompat.getDrawable(context,R.drawable.ic_beans);
            case COCONUT:
                return ContextCompat.getDrawable(context,R.drawable.ic_coconut_x);
            case WHOLE_MILK:
                return ContextCompat.getDrawable(context,R.drawable.ic_cow);
            default:
                throw new UnsupportedOperationException("Unknown milk type:"+type);
        }
    }

    public static String getSizeTypeLabel(Context context, SizeType type){
        switch (type){
            case SMALL:
                return context.getString(R.string.small_size_label);
            case MEDIUM:
                return context.getString(R.string.medium_size_label);
            case LARGE:
                return context.getString(R.string.large_size_label);
            case TALL:
                return context.getString(R.string.tall_size_label);
            default:
                throw new UnsupportedOperationException("Unknown size type:"+type);
        }
    }

    public static Drawable getSizeTypeImage(Context context, SizeType type){
        switch (type){
            case SMALL:
                return ContextCompat.getDrawable(context,R.drawable.ic_small_coffee_size);
            case MEDIUM:
                return ContextCompat.getDrawable(context,R.drawable.ic_coffee_medium_size);
            case LARGE:
                return ContextCompat.getDrawable(context,R.drawable.ic_coffee_large_size);
            case TALL:
                return ContextCompat.getDrawable(context,R.drawable.ic_coffee_xlarge_size);
            default:
                throw new UnsupportedOperationException("Unknown size type:"+type);
        }
    }

    public static String getSweetnessText(Context context, Sweetness sweetness){
        switch (sweetness){
            case NOT_SWEET:
                return context.getString(R.string.not_sweet_label);
            case SLIGHTLY_SWEET:
                return context.getString(R.string.slightly_sweet_label);
            case HALF_SWEET:
                return context.getString(R.string.half_sweet_label);
            case MODERATELY_SWEET:
                return context.getString(R.string.moderately_sweet_label);
            case FULL_SWEETNESS:
                return context.getString(R.string.full_sweetness_label);
            default:
                throw new UnsupportedOperationException("Unknown sweetness type"+sweetness);
        }
    }
}

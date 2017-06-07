package com.vpaliy.espressoinaction.presentation.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.vpaliy.espressoinaction.CoffeeApp;
import com.vpaliy.espressoinaction.R;
import com.vpaliy.espressoinaction.common.Constants;
import com.vpaliy.espressoinaction.di.component.DaggerViewComponent;
import com.vpaliy.espressoinaction.di.module.PresenterModule;
import com.vpaliy.espressoinaction.domain.model.Coffee;
import com.vpaliy.espressoinaction.domain.model.CoffeeType;
import com.vpaliy.espressoinaction.presentation.mvp.contract.CoffeeOrderContract;
import com.vpaliy.espressoinaction.presentation.mvp.contract.CoffeeOrderContract.Presenter;
import com.vpaliy.espressoinaction.presentation.ui.utils.CalendarUtils;

import java.util.Calendar;
import java.util.Locale;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ViewFlipper;

import javax.inject.Inject;
import butterknife.BindView;

public class CoffeeOrderFragment extends BottomSheetDialogFragment
        implements CoffeeOrderContract.View{

    private static final String TAG=CoffeeOrderFragment.class.getSimpleName();

    private Presenter presenter;
    private Unbinder unbinder;
    private int coffeeId;

    @BindView(R.id.coffee_image)
    protected ImageView image;

    @BindView(R.id.coffee_name)
    protected TextView coffeeName;

    @BindView(R.id.coffee_price)
    protected TextView coffeePrice;

    @BindView(R.id.go_button)
    protected View view;

    @BindView(R.id.layout_one)
    protected View first;

    @BindView(R.id.layout_two)
    protected View second;

    @BindView(R.id.layout_three)
    protected View third;

    @BindView(R.id.switcher)
    protected ViewFlipper flipper;

    @BindView(R.id.property_label_one)
    protected TextView firstLabel;

    @BindView(R.id.property_label_two)
    protected TextView secondLabel;


    public static CoffeeOrderFragment newInstance(Bundle args){
        CoffeeOrderFragment fragment=new CoffeeOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void initializeDependencies() {
        DaggerViewComponent.builder()
                .presenterModule(new PresenterModule())
                .applicationComponent(CoffeeApp.app().component())
                .build().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        initializeDependencies();
        if(savedInstanceState==null) {
            savedInstanceState=getArguments();
        }
        this.coffeeId=savedInstanceState.getInt(Constants.EXTRA_COFFEE_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_order_form,container,false);
        unbinder= ButterKnife.bind(this,root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(view!=null){
            presenter.start(coffeeId);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.EXTRA_COFFEE_ID,coffeeId);
    }

    @Override
    public void showCoffee(@NonNull Coffee coffee) {
        showCoffeeImage(coffee);
        showCoffeePrice(coffee.getPrice());
        showCoffeeType(coffee.getCoffeeType());
    }

    private void showCoffeeType(CoffeeType coffeeType){
        coffeeName.setText(coffeeType.toString());
    }

    private void showCoffeeImage(Coffee coffee){
        String drawableUri=coffee.getImageUrl();
        int resourceId=Integer.parseInt(drawableUri.substring(drawableUri.lastIndexOf("/")+1));
        Glide.with(getContext())
                .load(resourceId)
                .centerCrop()
                .into(image);
    }

    private void showCoffeePrice(double price){
        coffeePrice.setText(String.format(Locale.US,"$ "+"%.1f",price));
    }

    @Override
    public void showMessage(String message) {

    }

    @OnClick(R.id.go_button)
    public void go(){
        if(isVisible(first)){
            if(!flipper.isFlipping()) {
                flipper.showNext();
            }
        }else if(isVisible(second)){
            prepareDay();
            prepareTime();
            if(!flipper.isFlipping()) {
                flipper.showNext();
            }
        }
    }

    private void prepareDay(){
        Calendar calendar=Calendar.getInstance();
        for(int index=0;index<3;index++) {
            TextView day;
            switch (index){
                case 0:
                    day=ButterKnife.findById(third,R.id.today_day);
                    break;
                case 1:
                    day=ButterKnife.findById(third,R.id.tomorrow_day);
                    break;
                default:
                    day=ButterKnife.findById(third,R.id.third_day);
                    break;
            }
            StringBuilder builder = new StringBuilder();
            builder.append(CalendarUtils.getDay(getContext(), calendar));
            builder.append('\n');
            builder.append(CalendarUtils.dayOfMonth(calendar));
            day.setText(CalendarUtils.buildSpannableText(builder.toString(), 3));
            calendar.add(Calendar.DAY_OF_MONTH,1);
        }
    }

    private void prepareTime(){
        firstLabel.setText(getString(R.string.day_label));
        secondLabel.setText(getString(R.string.time_label));
        for(int index=0;index<3;index++){
            TextView time;
            String text;
            switch (index){
                case 0:
                    time=ButterKnife.findById(third,R.id.time_frame_one);
                    text=time.getText().toString();
                    time.setText(CalendarUtils.buildSpannableText(text,4));
                    break;
                case 1:
                    time=ButterKnife.findById(third,R.id.time_frame_two);
                    text=time.getText().toString();
                    time.setText(CalendarUtils.buildSpannableText(text,4));
                    break;
                default:
                    time=ButterKnife.findById(third,R.id.time_frame_three);
                    text=time.getText().toString();
                    time.setText(CalendarUtils.buildSpannableText(text,3));
                    break;
            }
        }
    }

    private boolean isVisible(View view){
        return view.getVisibility()==View.VISIBLE;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(unbinder!=null) unbinder.unbind();
    }

    @Inject
    @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }
}

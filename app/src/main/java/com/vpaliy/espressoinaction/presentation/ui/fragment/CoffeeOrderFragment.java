package com.vpaliy.espressoinaction.presentation.ui.fragment;


import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.util.Pair;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.vpaliy.espressoinaction.CoffeeApp;
import com.vpaliy.espressoinaction.R;
import com.vpaliy.espressoinaction.databinding.FragmentOrderFormBinding;
import com.vpaliy.espressoinaction.di.component.DaggerViewComponent;
import com.vpaliy.espressoinaction.di.module.PresenterModule;
import com.vpaliy.espressoinaction.domain.model.Coffee;
import com.vpaliy.espressoinaction.domain.model.CoffeeType;
import com.vpaliy.espressoinaction.presentation.mvp.contract.CoffeeOrderContract;
import com.vpaliy.espressoinaction.presentation.mvp.contract.CoffeeOrderContract.Presenter;
import com.vpaliy.espressoinaction.presentation.ui.utils.CalendarUtils;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import icepick.Icepick;

import icepick.State;
import android.annotation.TargetApi;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import javax.inject.Inject;

public class CoffeeOrderFragment extends BottomSheetDialogFragment
        implements CoffeeOrderContract.View{

    private Presenter presenter;
    @State int coffeeId;
    private FragmentOrderFormBinding binding;
    private Map<View,Pair<View,View>> clonedViewsMap=new LinkedHashMap<>();

    public interface OnPropertyClicked {
        void onClick(View view);
    }

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
        Icepick.restoreInstanceState(this,savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_order_form,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(view!=null){
            presenter.start(coffeeId);
            binding.goButton.setOnClickListener(v->go());
            initStepOne();
            initStepTwo();
            initStepThree();
        }
    }

    private void initStepOne(){
        binding.layoutOne.setMilkHandler(text-> {
            removeIfExists(binding.propertyLabelTwo);
            text.setEnabled(false);
            animateIcon(createCupSizeView(text,binding.propertyLabelTwo),binding.propertyLabelTwo);
        });
        binding.layoutOne.setSizeHandler(text-> {
            removeIfExists(binding.propertyLabelOne);
            text.setEnabled(false);
            animateIcon(createCupSizeView(text,binding.propertyLabelOne), binding.propertyLabelOne);
        });
    }

    private void initStepTwo(){

    }

    private void initStepThree(){
        binding.layoutThree.setDayHandler(day->{
            removeIfExists(binding.propertyLabelTwo);
            day.setSelected(true);
            animateText(createTimeView(day,binding.propertyLabelTwo),binding.propertyLabelTwo);
        });
        binding.layoutThree.setTimeHandler(time->{
            removeIfExists(binding.propertyLabelOne);
            time.setSelected(true);
            animateText(createTimeView(time,binding.propertyLabelOne), binding.propertyLabelOne);
        });
    }

    private void removeIfExists(View key){
        if(clonedViewsMap.containsKey(key)) {
            Pair<View, View> pair = clonedViewsMap.get(key);
            pair.first.setEnabled(true);
            binding.mainContainer.removeView(pair.second);
            clonedViewsMap.remove(key);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this,outState);
    }

    @Override
    public void showCoffee(@NonNull Coffee coffee) {
        showCoffeeImage(coffee);
        showCoffeePrice(coffee.getPrice());
        showCoffeeType(coffee.getCoffeeType());
    }

    private void showCoffeeType(CoffeeType coffeeType){
        binding.coffeeName.setText(coffeeType.toString());
    }

    private void showCoffeeImage(Coffee coffee){
        String drawableUri=coffee.getImageUrl();
        int resourceId=Integer.parseInt(drawableUri.substring(drawableUri.lastIndexOf("/")+1));
        Glide.with(getContext())
                .load(resourceId)
                .centerCrop()
                .into(binding.coffeeImage);
    }

    private void showCoffeePrice(double price){
        binding.coffeePrice.setText(String.format(Locale.US,"$ "+"%.1f",price));
    }

    @Override
    public void showMessage(String message) {
    }

    public void go(){
        cleanUpOnFlip();
        if(isVisible(binding.layoutOne.getRoot())){
            if(!binding.switcher.isFlipping()) {
                binding.switcher.showNext();
            }
        }else if(isVisible(binding.layoutTwo)){
            prepareDay();
            prepareTime();
            if(!binding.switcher.isFlipping()) {
                binding.switcher.showNext();
            }
        }
    }

    private void cleanUpOnFlip(){
        if(clonedViewsMap.size()!=0){
            clonedViewsMap.forEach((key,pair)->{
                binding.mainContainer.removeView(pair.second);
            });
            clonedViewsMap.clear();
        }
    }

    private void prepareDay(){
        Calendar calendar=Calendar.getInstance();
        for(int index=0;index<3;index++) {
            TextView day;
            switch (index){
                case 0:
                    day=binding.layoutThree.todayDay;
                    break;
                case 1:
                    day=binding.layoutThree.tomorrowDay;
                    break;
                default:
                    day=binding.layoutThree.thirdDay;
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
        binding.propertyLabelOne.setText(getString(R.string.day_label));
        binding.propertyLabelTwo.setText(getString(R.string.time_label));
        for(int index=0;index<3;index++){
            TextView time;
            String text;
            switch (index){
                case 0:
                    time=binding.layoutThree.timeFrameOne;
                    text=time.getText().toString();
                    time.setText(CalendarUtils.buildSpannableText(text,4));
                    break;
                case 1:
                    time=binding.layoutThree.timeFrameTwo;
                    text=time.getText().toString();
                    time.setText(CalendarUtils.buildSpannableText(text,4));
                    break;
                default:
                    time=binding.layoutThree.timeFrameThree;
                    text=time.getText().toString();
                    time.setText(CalendarUtils.buildSpannableText(text,3));
                    break;
            }
        }
    }

    private ViewGroup getRoot(){
        return ViewGroup.class.cast(getView());
    }

    @TargetApi(19)
    private void animateIcon(View clonedView, View targetView){
        clonedView.post(()->{
            TransitionManager.beginDelayedTransition(getRoot());
            clonedView.setLayoutParams(SelectedParamsFactory.endIconParams(clonedView,targetView));
        });
    }

    @TargetApi(19)
    private void animateText(View clonedView, View targetView){
        clonedView.post(()->{
            TransitionManager.beginDelayedTransition(getRoot());
            clonedView.setLayoutParams(SelectedParamsFactory.endTextParams(clonedView,targetView));
        });
    }

    private TextView createCupSizeView(View real, TextView textLabel){
        final TextView fakeSelectedTextView = new TextView(getContext());
        TextView realTextView=TextView.class.cast(real);
        Drawable[] drawables=realTextView.getCompoundDrawables();
        fakeSelectedTextView.setCompoundDrawables(drawables[0],drawables[1],drawables[2],drawables[3]);
        fakeSelectedTextView.setLayoutParams(SelectedParamsFactory.startTextParams(real,binding));
        Pair<View,View> pair=new Pair<>(real,fakeSelectedTextView);
        clonedViewsMap.put(textLabel,pair);
        binding.mainContainer.addView(fakeSelectedTextView);
        return fakeSelectedTextView;
    }

    private TextView createTimeView(View real, TextView textLabel){
        final TextView fakeSelectedTextView = new TextView(getContext(),null,R.attr.textStyle);
        TextView realTextView=TextView.class.cast(real);
        fakeSelectedTextView.setText(realTextView.getText().toString().replace('\n',' '));
        fakeSelectedTextView.setLayoutParams(SelectedParamsFactory.startTextParams(real,binding));
        Pair<View,View> pair=new Pair<>(real,fakeSelectedTextView);
        clonedViewsMap.put(textLabel,pair);
        binding.mainContainer.addView(fakeSelectedTextView);
        return fakeSelectedTextView;
    }

    private boolean isVisible(View view){
        return view.getVisibility()==View.VISIBLE;
    }

    @Inject
    @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }

    private static class SelectedParamsFactory {

        private static ConstraintLayout.LayoutParams startTextParams(View selectedView, FragmentOrderFormBinding binding) {
            final ConstraintLayout.LayoutParams layoutParams =
                    new ConstraintLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.topToTop = binding.mainContainer.getId();
            layoutParams.leftToLeft = binding.mainContainer.getId();
            layoutParams.setMargins((int) selectedView.getX(), (int) selectedView.getY(), 0, 0);
            setStartState(selectedView,layoutParams);
            return layoutParams;
        }

        private static void setStartState(View selectedView, ConstraintLayout.LayoutParams layoutParams) {
            layoutParams.topToTop = ((ViewGroup) selectedView.getParent().getParent()).getId();
            layoutParams.leftToLeft = ((ViewGroup) selectedView.getParent().getParent()).getId();
            layoutParams.setMargins((int) selectedView.getX(), (int) selectedView.getY(), 0, 0);
        }

        private static ConstraintLayout.LayoutParams endIconParams(View v, View targetView) {
            final int marginLeft = v.getContext().getResources()
                    .getDimensionPixelOffset(R.dimen.spacing_medium);
            final int marginTop=v.getContext().getResources()
                    .getDimensionPixelOffset(R.dimen.spacing_big);
            return endParams(v,targetView,marginTop,marginLeft);
        }

        private static ConstraintLayout.LayoutParams endParams(View view, View targetView,
                                                               int marginTop, int marginLeft){
            final ConstraintLayout.LayoutParams layoutParams =
                    (ConstraintLayout.LayoutParams) view.getLayoutParams();
            layoutParams.setMargins(marginLeft, marginTop, 0, 0);
            layoutParams.topToTop = targetView.getId();
            layoutParams.startToEnd = targetView.getId();
            layoutParams.bottomToBottom = targetView.getId();
            return layoutParams;
        }

        private static ConstraintLayout.LayoutParams endTextParams(View v, View targetView) {
            final int marginLeft = v.getContext().getResources()
                    .getDimensionPixelOffset(R.dimen.spacing_medium);
            return endParams(v,targetView,0,marginLeft);
        }
    }

}

package com.vpaliy.espressoinaction.presentation.ui.fragment;


import android.annotation.TargetApi;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.vpaliy.espressoinaction.CoffeeApp;
import com.vpaliy.espressoinaction.R;
import com.vpaliy.espressoinaction.common.Constants;
import com.vpaliy.espressoinaction.databinding.FragmentOrderFormBinding;
import com.vpaliy.espressoinaction.di.component.DaggerViewComponent;
import com.vpaliy.espressoinaction.di.module.PresenterModule;
import com.vpaliy.espressoinaction.domain.model.Coffee;
import com.vpaliy.espressoinaction.domain.model.CoffeeType;
import com.vpaliy.espressoinaction.presentation.mvp.contract.CoffeeOrderContract;
import com.vpaliy.espressoinaction.presentation.mvp.contract.CoffeeOrderContract.Presenter;
import com.vpaliy.espressoinaction.presentation.ui.utils.CalendarUtils;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import butterknife.ButterKnife;
import icepick.Icepick;
import icepick.State;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import javax.inject.Inject;

public class CoffeeOrderFragment extends BottomSheetDialogFragment
        implements CoffeeOrderContract.View{

    private static final String TAG=CoffeeOrderFragment.class.getSimpleName();

    private Presenter presenter;

    @State int coffeeId;

    private FragmentOrderFormBinding binding;
    private List<View> clonedViews;

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
            clonedViews=new LinkedList<>();
            binding.goButton.setOnClickListener(v->go());
            binding.layoutOne.setMilkHandler(text-> {
                if (clonedViews.size() < 2)
                    animateToUpper(createCupSizeView(text), binding.propertyLabelTwo);
            });
            binding.layoutOne.setSizeHandler(text-> {
                if (clonedViews.size() < 2)
                    animateToUpper(createCupSizeView(text), binding.propertyLabelOne);
            });
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
        if(isVisible(binding.layoutOne.getRoot())){
            if(!clonedViews.isEmpty()){
                clonedViews.forEach(binding.mainContainer::removeView);
                clonedViews.clear();
            }
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

    private void prepareDay(){
        Calendar calendar=Calendar.getInstance();
        for(int index=0;index<3;index++) {
            TextView day;
            switch (index){
                case 0:
                    day=ButterKnife.findById(binding.layoutThree,R.id.today_day);
                    break;
                case 1:
                    day=ButterKnife.findById(binding.layoutThree,R.id.tomorrow_day);
                    break;
                default:
                    day=ButterKnife.findById(binding.layoutThree,R.id.third_day);
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
                    time=ButterKnife.findById(binding.layoutThree,R.id.time_frame_one);
                    text=time.getText().toString();
                    time.setText(CalendarUtils.buildSpannableText(text,4));
                    break;
                case 1:
                    time=ButterKnife.findById(binding.layoutThree,R.id.time_frame_two);
                    text=time.getText().toString();
                    time.setText(CalendarUtils.buildSpannableText(text,4));
                    break;
                default:
                    time=ButterKnife.findById(binding.layoutThree,R.id.time_frame_three);
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
    private void animateToUpper(View clonedView, View targetView){
        clonedView.post(()->{
            TransitionManager.beginDelayedTransition(getRoot());
            clonedView.setLayoutParams(SelectedParamsFactory.endParams(clonedView,targetView));
        });
    }

    private TextView createCupSizeView(View real){
        final TextView fakeSelectedTextView = new TextView(getContext());
        TextView realTextView=TextView.class.cast(real);
        Drawable[] drawables=realTextView.getCompoundDrawables();
        fakeSelectedTextView.setCompoundDrawables(drawables[0],drawables[1],drawables[2],drawables[3]);
        fakeSelectedTextView.setLayoutParams(SelectedParamsFactory.startTextParams(real,binding));
        if(clonedViews.size()<2) {
            clonedViews.add(fakeSelectedTextView);
            binding.mainContainer.addView(fakeSelectedTextView);
        }
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

        private static ConstraintLayout.LayoutParams endParams(View v, View targetView) {
            final ConstraintLayout.LayoutParams layoutParams =
                    (ConstraintLayout.LayoutParams) v.getLayoutParams();

            final int marginLeft = v.getContext().getResources()
                    .getDimensionPixelOffset(R.dimen.spacing_medium);
            final int marginTop=v.getContext().getResources()
                    .getDimensionPixelOffset(R.dimen.spacing_big);
            layoutParams.setMargins(marginLeft, marginTop, 0, 0);
            layoutParams.topToTop = targetView.getId();
            layoutParams.startToEnd = targetView.getId();
            layoutParams.bottomToBottom = targetView.getId();
            return layoutParams;
        }
    }

}

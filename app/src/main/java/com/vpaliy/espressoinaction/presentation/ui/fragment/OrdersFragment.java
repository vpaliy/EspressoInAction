package com.vpaliy.espressoinaction.presentation.ui.fragment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.vpaliy.espressoinaction.CoffeeApp;
import com.vpaliy.espressoinaction.R;
import com.vpaliy.espressoinaction.di.component.DaggerViewComponent;
import com.vpaliy.espressoinaction.di.module.PresenterModule;
import com.vpaliy.espressoinaction.domain.model.Order;
import com.vpaliy.espressoinaction.presentation.mvp.contract.OrdersContract.Presenter;
import com.vpaliy.espressoinaction.presentation.mvp.contract.OrdersContract;
import com.vpaliy.espressoinaction.presentation.ui.adapter.OrderAdapter;
import java.util.List;
import javax.inject.Inject;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class OrdersFragment extends BaseFragment
            implements OrdersContract.View {

    private Presenter presenter;
    private OrderAdapter adapter;

    @Inject
    @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_layout,container,false);
        bind(root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(view!=null){
            adapter=new OrderAdapter(getContext(),rxBus);
            list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
            list.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));
            list.setAdapter(adapter);
            hookUpTouchEvent();
        }
    }


    private void hookUpTouchEvent(){
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT){
            private Drawable background;
            private Drawable xMark;
            private int xMarkMargin;
            private boolean isSetUp;

            private void setUp() {
                background =new ColorDrawable(ContextCompat.getColor(getContext(),R.color.colorPrimaryDark));
                xMark = ContextCompat.getDrawable(getContext(), R.drawable.done);
                xMark.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                xMarkMargin = (int) getResources().getDimension(R.dimen.spacing_huge);
                isSetUp = true;
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Order order=adapter.at(viewHolder.getAdapterPosition());
                adapter.removeAt(viewHolder.getAdapterPosition());
                adapter.pendingRemoval(viewHolder.getAdapterPosition());
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (viewHolder.getAdapterPosition() == -1) {
                    return;
                }

                if (!isSetUp) {
                    setUp();
                }

                View itemView = viewHolder.itemView;

                background.setBounds(itemView.getLeft() + (int) dX, itemView.getTop(), itemView.getLeft(), itemView.getBottom());
                background.draw(c);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(list);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    void initializeDependencies() {
        DaggerViewComponent.builder()
                .applicationComponent(CoffeeApp.app().component())
                .presenterModule(new PresenterModule())
                .build().inject(this);
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    public void showOrderList(@NonNull List<Order> coffeeList) {
        adapter.setData(coffeeList);
    }


}

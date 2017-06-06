package com.vpaliy.espressoinaction.common.scheduler;

import android.support.annotation.NonNull;
import rx.Scheduler;

public interface BaseSchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler multi();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();

}
package com.vpaliy.espressoinaction.di.component;

import com.vpaliy.espressoinaction.di.module.ApplicationModule;
import com.vpaliy.espressoinaction.di.module.PresenterModule;
import com.vpaliy.espressoinaction.di.scope.ViewScope;
import com.vpaliy.espressoinaction.presentation.ui.fragment.CoffeeOrderFragment;
import com.vpaliy.espressoinaction.presentation.ui.fragment.CoffeesFragment;
import dagger.Component;

@ViewScope
@Component(modules = PresenterModule.class,
    dependencies = ApplicationComponent.class)
public interface ViewComponent {
    void inject(CoffeesFragment fragment);
    void inject(CoffeeOrderFragment fragment);
}

package com.apa.searchfortickets.dagger.module;

import com.apa.searchfortickets.ui.MainActivity;
import com.apa.searchfortickets.ui.currency.CurrencySearchActivity;
import com.apa.searchfortickets.ui.region.RegionSearchActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by apavlenco on 8/24/20.
 *
 * Bind all app activities here
 */
@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = {MainFragmentBindingModule.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract CurrencySearchActivity bindCurrencySearchActivity();

    @ContributesAndroidInjector
    abstract RegionSearchActivity bindCountrySearchActivity();
}

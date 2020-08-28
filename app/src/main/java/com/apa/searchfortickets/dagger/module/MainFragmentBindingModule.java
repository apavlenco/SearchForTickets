package com.apa.searchfortickets.dagger.module;

import com.apa.searchfortickets.ui.search.InboundLocationDialogFragment;
import com.apa.searchfortickets.ui.search.OutboundLocationDialogFragment;
import com.apa.searchfortickets.ui.search.SearchFragment;
import com.apa.searchfortickets.ui.settings.SettingsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by apavlenco on 8/24/20.
 */
@Module
public abstract class MainFragmentBindingModule {

    @ContributesAndroidInjector
    abstract SettingsFragment provideSettingsFragment();

    @ContributesAndroidInjector
    abstract SearchFragment provideSearchFragment();

    @ContributesAndroidInjector
    abstract OutboundLocationDialogFragment provideOutboundLocationDialogFragment();

    @ContributesAndroidInjector
    abstract InboundLocationDialogFragment provideInboundLocationDialogFragment();
}

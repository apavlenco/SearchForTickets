package com.apa.searchfortickets.dagger.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.apa.searchfortickets.dagger.util.ViewModelFactory;
import com.apa.searchfortickets.dagger.util.ViewModelKey;
import com.apa.searchfortickets.ui.currency.CurrencyViewModel;
import com.apa.searchfortickets.ui.region.RegionViewModel;
import com.apa.searchfortickets.ui.search.SearchViewModel;
import com.apa.searchfortickets.ui.settings.SettingsViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by apavlenco on 8/24/20.
 */
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel.class)
    abstract ViewModel bindSearchViewModel(SearchViewModel searchViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel.class)
    abstract ViewModel bindSettingsViewModel(SettingsViewModel settingsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyViewModel.class)
    abstract ViewModel bindCurrencyViewModel(CurrencyViewModel currencyViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RegionViewModel.class)
    abstract ViewModel bindRegionViewModel(RegionViewModel regionViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}

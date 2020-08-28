package com.apa.searchfortickets.ui.settings;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.apa.searchfortickets.data.AppRepository;

import javax.inject.Inject;

/**
 * Created by apavlenco on 8/25/20.
 */
public class SettingsViewModel extends ViewModel {

    private final AppRepository appRepository;
    private final MutableLiveData<String> currency = new MutableLiveData<>();
    private final MutableLiveData<String> region = new MutableLiveData<>();

    @Inject
    public SettingsViewModel(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    public void refreshSettings() {
        currency.setValue(appRepository.getCurrency());
        region.setValue(appRepository.getRegion());
    }

    public MutableLiveData<String> getCurrency() {
        return currency;
    }

    public MutableLiveData<String> getRegion() {
        return region;
    }
}

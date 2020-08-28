package com.apa.searchfortickets.ui.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.apa.searchfortickets.R;
import com.apa.searchfortickets.base.BaseFragment;
import com.apa.searchfortickets.dagger.util.ViewModelFactory;
import com.apa.searchfortickets.ui.currency.CurrencySearchActivity;
import com.apa.searchfortickets.ui.region.RegionSearchActivity;

import org.apache.commons.lang3.StringUtils;

import java.util.Currency;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingsFragment extends BaseFragment {

    @BindView(R.id.currency_selected)
    TextView currencySelected;
    @BindView(R.id.region_selected)
    TextView regionSelected;
    @Inject
    ViewModelFactory viewModelFactory;
    private SettingsViewModel settingsViewModel;

    @OnClick(R.id.region_selector)
    public void regionSelectClicked() {
        startActivity(RegionSearchActivity.getNewIntent(getContext()));
    }

    @OnClick(R.id.currency_selector)
    public void currencySelectClicked() {
        startActivity(CurrencySearchActivity.getNewIntent(getContext()));
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_settings;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        settingsViewModel = ViewModelProviders.of(this, viewModelFactory).get(SettingsViewModel.class);
        observeViewModel();
    }

    private void observeViewModel() {

        settingsViewModel.getCurrency().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (StringUtils.isNotBlank(s)) {
                    currencySelected.setText(Currency.getInstance(s).getDisplayName());
                }
            }
        });

        settingsViewModel.getRegion().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (StringUtils.isNotBlank(s)) {
                    regionSelected.setText(new Locale("", s).getDisplayCountry());
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        settingsViewModel.refreshSettings();
    }
}
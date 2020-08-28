package com.apa.searchfortickets.data.local;

import android.content.SharedPreferences;
import android.content.res.AssetManager;

import java.util.Currency;
import java.util.Locale;

/**
 * Created by apavlenco on 8/24/20.
 *
 * Accessing local storage here(preferences, db, assets)
 */
public class LocalDataStore {

    private AssetManager assetManager;
    private SharedPreferences sharedPreferences;

    public LocalDataStore(AssetManager assetManager, SharedPreferences sharedPreferences) {
        this.assetManager = assetManager;
        this.sharedPreferences = sharedPreferences;
    }

    /**
     * Save region setting
     */
    public void saveRegion(String region) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("region", region);
        editor.apply();
    }

    /**
     * Get region setting
     */
    public String getRegion() {
        return sharedPreferences.getString("region", Locale.getDefault().getCountry());
    }

    /**
     * Save currency setting
     */
    public void saveCurrency(String currency) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("currency", currency);
        editor.apply();
    }

    /**
     *Get currency setting
     */
    public String getCurrency() {
        return sharedPreferences.getString("currency", Currency.getInstance(Locale.getDefault()).getCurrencyCode());
    }

    /**
     * Save first launch flag.
     */
    public void saveFirstLaunchFlag(boolean firstLaunch) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("first_launch", firstLaunch);
        editor.apply();
    }

    /**
     * Gets first launch flag.
     */
    public boolean getFirstLaunchFlag() {
        return sharedPreferences.getBoolean("first_launch", true);
    }

}

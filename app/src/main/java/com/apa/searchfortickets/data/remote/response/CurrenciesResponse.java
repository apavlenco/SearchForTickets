package com.apa.searchfortickets.data.remote.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by apavlenco on 8/27/20.
 */
public class CurrenciesResponse {

    @SerializedName("Currencies")
    private List<QuoteCurrency> currencies;

    public List<QuoteCurrency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<QuoteCurrency> currencies) {
        this.currencies = currencies;
    }

}

package com.apa.searchfortickets.data.remote.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by apavlenco on 8/23/20.
 */
public class QuoteSearchResponse {

    @SerializedName("Quotes")
    private List<Quote> quotes;

    @SerializedName("Places")
    private List<QuotePlace> places;

    @SerializedName("Carriers")
    private List<QuoteCarrier> carriers;

    @SerializedName("Currencies")
    private List<QuoteCurrency> currencies;

    public List<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<Quote> quotes) {
        this.quotes = quotes;
    }

    public List<QuotePlace> getPlaces() {
        return places;
    }

    public void setPlaces(List<QuotePlace> places) {
        this.places = places;
    }

    public List<QuoteCarrier> getCarriers() {
        return carriers;
    }

    public void setCarriers(List<QuoteCarrier> carriers) {
        this.carriers = carriers;
    }

    public List<QuoteCurrency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<QuoteCurrency> currencies) {
        this.currencies = currencies;
    }
}

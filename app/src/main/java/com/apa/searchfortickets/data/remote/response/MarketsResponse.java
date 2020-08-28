package com.apa.searchfortickets.data.remote.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by apavlenco on 8/27/20.
 */
public class MarketsResponse {
    @SerializedName("Countries")
    private List<Market> markets;

    public List<Market> getMarkets() {
        return markets;
    }

    public void setMarkets(List<Market> markets) {
        this.markets = markets;
    }
}

package com.apa.searchfortickets.data.remote.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apavlenco on 8/23/20.
 */
public class QuotePlace {

    @SerializedName("PlaceId")
    private long placeId;

    @SerializedName("Name")
    private String name;

    @SerializedName("Type")
    private String type;

    @SerializedName("SkyscannerCode")
    private String skyscannerCode;

    public long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSkyscannerCode() {
        return skyscannerCode;
    }

    public void setSkyscannerCode(String skyscannerCode) {
        this.skyscannerCode = skyscannerCode;
    }
}

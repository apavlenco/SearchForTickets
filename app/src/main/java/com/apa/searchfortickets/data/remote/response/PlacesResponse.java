package com.apa.searchfortickets.data.remote.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by apavlenco on 8/26/20.
 */
public class PlacesResponse {

    @SerializedName("Places")
    private List<Place> places;

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

}

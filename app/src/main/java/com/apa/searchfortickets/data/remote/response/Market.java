package com.apa.searchfortickets.data.remote.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apavlenco on 8/27/20.
 */
public class Market {

    @SerializedName("Code")
    private String code;

    @SerializedName("Name")
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

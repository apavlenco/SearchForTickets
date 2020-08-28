package com.apa.searchfortickets.data.remote.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apavlenco on 8/23/20.
 */
public class QuoteCarrier {

    @SerializedName("CarrierId")
    private long carrierId;

    @SerializedName("Name")
    private String carrierName;

    public long getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(long carrierId) {
        this.carrierId = carrierId;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }
}

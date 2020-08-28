package com.apa.searchfortickets.data.remote.response;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by apavlenco on 8/22/20.
 */
public class LegItem {

    @SerializedName("CarrierIds")
    private long[] carrierIds;

    @SerializedName("OriginId")
    private long originId;

    @SerializedName("DestinationId")
    private long destinationId;

    @SerializedName("DepartureDate")
    private Date departureDate;

    public long[] getCarrierIds() {
        return carrierIds;
    }

    public void setCarrierIds(long[] carrierIds) {
        this.carrierIds = carrierIds;
    }

    public long getOriginId() {
        return originId;
    }

    public void setOriginId(long originId) {
        this.originId = originId;
    }

    public long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(long destinationId) {
        this.destinationId = destinationId;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }
}



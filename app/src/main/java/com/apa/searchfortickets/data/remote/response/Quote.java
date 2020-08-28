package com.apa.searchfortickets.data.remote.response;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by apavlenco on 8/22/20.
 */
public class Quote {

    @SerializedName("QuoteId")
    private long quoteId;

    @SerializedName("MinPrice")
    private double minPrice;

    @SerializedName("Direct")
    private boolean direct;

    @SerializedName("OutboundLeg")
    private LegItem outboundLeg;

    @SerializedName("InboundLeg")
    private LegItem inboundLeg;

    @SerializedName("QuoteDateTime")
    private Date quoteDateTime;

    public long getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(long quoteId) {
        this.quoteId = quoteId;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public boolean isDirect() {
        return direct;
    }

    public void setDirect(boolean direct) {
        this.direct = direct;
    }

    public LegItem getOutboundLeg() {
        return outboundLeg;
    }

    public void setOutboundLeg(LegItem outboundLeg) {
        this.outboundLeg = outboundLeg;
    }

    public LegItem getInboundLeg() {
        return inboundLeg;
    }

    public void setInboundLeg(LegItem inboundLeg) {
        this.inboundLeg = inboundLeg;
    }

    public Date getQuoteDateTime() {
        return quoteDateTime;
    }

    public void setQuoteDateTime(Date quoteDateTime) {
        this.quoteDateTime = quoteDateTime;
    }
}

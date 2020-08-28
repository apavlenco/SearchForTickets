package com.apa.searchfortickets.data.remote.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apavlenco on 8/22/20.
 */
public class QuoteCurrency {

    @SerializedName("Code")
    private String code;

    @SerializedName("Symbol")
    private String symbol;

    @SerializedName("ThousandsSeparator")
    private String thousandsSeparator;

    @SerializedName("DecimalSeparator")
    private String decimalSeparator;

    @SerializedName("SymbolOnLeft")
    private boolean symbolOnLeft;

    @SerializedName("SpaceBetweenAmountAndSymbol")
    private boolean spaceBetweenAmountAndSymbol;

    @SerializedName("RoundingCoefficient")
    private int roundingCoefficient;

    @SerializedName("DecimalDigits")
    private int decimalDigits;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getThousandsSeparator() {
        return thousandsSeparator;
    }

    public void setThousandsSeparator(String thousandsSeparator) {
        this.thousandsSeparator = thousandsSeparator;
    }

    public String getDecimalSeparator() {
        return decimalSeparator;
    }

    public void setDecimalSeparator(String decimalSeparator) {
        this.decimalSeparator = decimalSeparator;
    }

    public boolean isSymbolOnLeft() {
        return symbolOnLeft;
    }

    public void setSymbolOnLeft(boolean symbolOnLeft) {
        this.symbolOnLeft = symbolOnLeft;
    }

    public boolean isSpaceBetweenAmountAndSymbol() {
        return spaceBetweenAmountAndSymbol;
    }

    public void setSpaceBetweenAmountAndSymbol(boolean spaceBetweenAmountAndSymbol) {
        this.spaceBetweenAmountAndSymbol = spaceBetweenAmountAndSymbol;
    }

    public int getRoundingCoefficient() {
        return roundingCoefficient;
    }

    public void setRoundingCoefficient(int roundingCoefficient) {
        this.roundingCoefficient = roundingCoefficient;
    }

    public int getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(int decimalDigits) {
        this.decimalDigits = decimalDigits;
    }
}

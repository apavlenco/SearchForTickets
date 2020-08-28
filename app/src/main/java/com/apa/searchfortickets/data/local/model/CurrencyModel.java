package com.apa.searchfortickets.data.local.model;

/**
 * Created by apavlenco on 8/25/20.
 *
 * Data model for CurrencyAdapter
 */
public class CurrencyModel {

    private String code;
    private String symbol;
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

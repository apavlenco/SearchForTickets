package com.apa.searchfortickets.util;

/**
 * Created by apavlenco on 8/24/20.
 */
public class Constants {

    public final static String BASE_URL = "https://partners.api.skyscanner.net/apiservices/";
    public final static String API_KEY = "prtl6749387986743898559646983194";

    public static final int CONNECT_TIMEOUT_SEC = 30;
    public static final int READ_TIMEOUT_SEC = 30;
    public static final int WRITE_TIMEOUT_SEC = 30;

    public static final int MAX_AGE = 60; //1 minute request considered fresh
    public static final int CACHE_LIFETIEME = 60 * 60 * 24; //1 day cache life
    public static final long CACHE_SIZE = 5 * 1024 * 1024; //5 MB cache
    public static final String HEADER_CACHE_CONTROL = "Cache-Control";
    public static final String HEADER_PRAGMA = "Pragma";
    public static final String HEADER_VARY = "Vary";

}

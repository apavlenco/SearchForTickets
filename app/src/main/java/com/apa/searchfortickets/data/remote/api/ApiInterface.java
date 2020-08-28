package com.apa.searchfortickets.data.remote.api;

import com.apa.searchfortickets.data.remote.response.CurrenciesResponse;
import com.apa.searchfortickets.data.remote.response.MarketsResponse;
import com.apa.searchfortickets.data.remote.response.PlacesResponse;
import com.apa.searchfortickets.data.remote.response.QuoteSearchResponse;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by apavlenco on 8/24/20.
 *
 * API call used
 */
public interface ApiInterface {

    @GET("reference/v1.0/currencies")
    Single<Response<CurrenciesResponse>> getCurrencies(@Query("apiKey") String key);

    @GET("reference/v1.0/countries/{locale}")
    Single<Response<MarketsResponse>> getMarkets(@Path("locale") String locale,
                                                 @Query("apiKey") String key);

    @GET("autosuggest/v1.0/{country}/{currency}/{locale}")
    Single<Response<PlacesResponse>> getPlaces(@Path("country") String country,
                                               @Path("currency") String currency,
                                               @Path("locale") String locale,
                                               @Query("query") String query,
                                               @Query("apiKey") String key);

    @GET("browsequotes/v1.0/{country}/{currency}/{locale}/{originPlace}/{destinationPlace}/{outboundPartialDate}/{inboundPartialDate}")
    Single<Response<QuoteSearchResponse>> getQuotes(@Path("country") String country,
                                                    @Path("currency") String currency,
                                                    @Path("locale") String locale,
                                                    @Path("originPlace") String originPlace,
                                                    @Path("destinationPlace") String destinationPlace,
                                                    @Path("outboundPartialDate") String outboundPartialDate,
                                                    @Path("inboundPartialDate") String inboundPartialDate,
                                                    @Query("apiKey") String key);
}

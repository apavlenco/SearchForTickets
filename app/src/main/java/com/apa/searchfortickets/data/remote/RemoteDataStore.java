package com.apa.searchfortickets.data.remote;

import com.apa.searchfortickets.data.remote.api.ApiInterface;
import com.apa.searchfortickets.data.remote.response.CurrenciesResponse;
import com.apa.searchfortickets.data.remote.response.MarketsResponse;
import com.apa.searchfortickets.data.remote.response.PlacesResponse;
import com.apa.searchfortickets.data.remote.response.QuoteSearchResponse;
import com.apa.searchfortickets.util.Constants;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by apavlenco on 8/24/20.
 *
 * Accessing remote data by API calls
 */
public class RemoteDataStore {

    volatile ApiInterface apiClient;

    public RemoteDataStore(ApiInterface apiClient) {
        this.apiClient = apiClient;
    }

    public Single<CurrenciesResponse> getCurrencies() {
        return createAPIcall(apiClient.getCurrencies(Constants.API_KEY));
    }

    public Single<MarketsResponse> getMarkets(String locale) {
        return createAPIcall(apiClient.getMarkets(locale, Constants.API_KEY));
    }

    public Single<PlacesResponse> getPlaces(String country, String currency,
                                            String locale, String query) {
        return createAPIcall(apiClient.getPlaces(country, currency, locale, query, Constants.API_KEY));
    }

    public Single<QuoteSearchResponse> getQuotes(String country, String currency,
                                                 String locale, String originPlace,
                                                 String destinationPlace,
                                                 String outboundPartialDate,
                                                 String inboundPartialDate) {
        return createAPIcall(apiClient.getQuotes(country, currency, locale, originPlace,
                destinationPlace, outboundPartialDate, inboundPartialDate, Constants.API_KEY));
    }

    /**
     * Generic for creating api calls Single-s
     */
    private <T> Single<T> createAPIcall(Single<Response<T>> single) {
        return Single.create(subscriber -> {
            Timber.i("using apiClient %s", apiClient.toString());
            single
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(response -> {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (!subscriber.isDisposed()) subscriber.onSuccess(response.body());
                            } else {
                                if (!subscriber.isDisposed())
                                    subscriber.onError(new Exception("Body is null"));
                            }
                        } else {
                            if (!subscriber.isDisposed())
                                subscriber.onError(new HttpException(response));
                        }
                    }, throwable -> {
                        throwable.printStackTrace();
                        if (!subscriber.isDisposed()) subscriber.onError(throwable);
                    });
        });
    }


}

package com.apa.searchfortickets.data;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.apa.searchfortickets.data.local.LocalDataStore;
import com.apa.searchfortickets.data.remote.RemoteDataStore;
import com.apa.searchfortickets.data.remote.response.CurrenciesResponse;
import com.apa.searchfortickets.data.remote.response.MarketsResponse;
import com.apa.searchfortickets.data.remote.response.Place;
import com.apa.searchfortickets.data.remote.response.PlacesResponse;
import com.apa.searchfortickets.data.remote.response.QuoteSearchResponse;
import com.apa.searchfortickets.util.Helper;

import org.apache.commons.lang3.StringUtils;

import java.util.Currency;
import java.util.Date;
import java.util.Locale;

import io.reactivex.Single;

/**
 * Created by apavlenco on 8/24/20.
 * <p>
 * General app repository
 */
public class AppRepository {

    private LocalDataStore localDataStore;
    private RemoteDataStore remoteDataStore;
    private Context context;
    private TelephonyManager telephonyManager;

    /**
     * Instantiates a new App repository.
     *
     * @param context         the context
     * @param localDataStore  the local data store
     * @param remoteDataStore the remote data store
     */
    public AppRepository(Context context, LocalDataStore localDataStore,
                         RemoteDataStore remoteDataStore,
                         TelephonyManager telephonyManager) {
        this.context = context;
        this.localDataStore = localDataStore;
        this.remoteDataStore = remoteDataStore;
        this.telephonyManager = telephonyManager;
        setDefaultLocation();

    }

    /**
     * Gets local data store.
     *
     * @return the local data store
     */
    public LocalDataStore getLocalDataStore() {
        return localDataStore;
    }


    public void saveRegion(String region) {
        localDataStore.saveRegion(region);
    }

    public String getRegion() {
        return localDataStore.getRegion();
    }

    public void saveCurrency(String currency) {
        localDataStore.saveCurrency(currency);
    }

    public String getCurrency() {
        return localDataStore.getCurrency();
    }

    public boolean isFirstLaunch() {
        return localDataStore.getFirstLaunchFlag();
    }

    public void confirmFirstLaunch() {
        localDataStore.saveFirstLaunchFlag(false);
    }


    private void setDefaultLocation() {
        if (!isFirstLaunch()) return;
        String iso = telephonyManager.getNetworkCountryIso();
        if (StringUtils.isNotBlank(iso)) {
            Locale currentLocale = new Locale("", iso);
            saveRegion(iso);
            saveCurrency(Currency.getInstance(currentLocale).getCurrencyCode());
        }
        confirmFirstLaunch();

    }

    /*
    API calls below
     */

    public Single<CurrenciesResponse> getCurrencies() {
        return remoteDataStore.getCurrencies();
    }

    public Single<MarketsResponse> getMarkets() {
        String locale = Locale.getDefault().toLanguageTag();
        return remoteDataStore.getMarkets(locale);
    }

    public Single<PlacesResponse> getPlaces(String query) {
        String country = getRegion();
        String currency = getCurrency();
        String locale = Locale.getDefault().toLanguageTag();
        return remoteDataStore.getPlaces(country, currency, locale, query);
    }

    public Single<QuoteSearchResponse> getQuotes(String country, String currency,
                                                 String locale, String originPlace,
                                                 String destinationPlace,
                                                 String outboundPartialDate,
                                                 String inboundPartialDate) {

        return remoteDataStore.getQuotes(country, currency, locale, originPlace,
                destinationPlace, outboundPartialDate, inboundPartialDate);
    }

    public Single<QuoteSearchResponse> getOneDirectionQuotes(Place origin,
                                                             Place destination,
                                                             Date outboundPartialDate) {

        String originPlace = origin.getPlaceId();
        String destinationPlace = destination.getPlaceId();
        String country = getRegion();
        String currency = getCurrency();
        String locale = Locale.getDefault().toLanguageTag();
        String outboundDate = Helper.formatDate(outboundPartialDate, Helper.REQUEST_DATE_FORMAT);
        String inboundPartial = "";

        return getQuotes(country, currency, locale, originPlace, destinationPlace, outboundDate, inboundPartial);
    }

}

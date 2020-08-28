package com.apa.searchfortickets.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.apa.searchfortickets.data.AppRepository;
import com.apa.searchfortickets.data.local.model.QuoteModel;
import com.apa.searchfortickets.data.remote.response.Place;
import com.apa.searchfortickets.data.remote.response.PlacesResponse;
import com.apa.searchfortickets.data.remote.response.Quote;
import com.apa.searchfortickets.data.remote.response.QuoteCarrier;
import com.apa.searchfortickets.data.remote.response.QuoteCurrency;
import com.apa.searchfortickets.data.remote.response.QuotePlace;
import com.apa.searchfortickets.data.remote.response.QuoteSearchResponse;
import com.apa.searchfortickets.ui.BaseViewModel;
import com.apa.searchfortickets.util.Helper;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class SearchViewModel extends BaseViewModel {

    public final MutableLiveData<Place> from = new MutableLiveData<>();
    public final MutableLiveData<Place> to = new MutableLiveData<>();
    public final MutableLiveData<Date> date = new MutableLiveData<>();
    private final AppRepository appRepository;
    private final MutableLiveData<List<QuoteModel>> quotes = new MutableLiveData<>();
    private final MutableLiveData<Boolean> quoteLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<List<Place>> places = new MutableLiveData<>();
    private final MutableLiveData<Boolean> placeLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> placeLoading = new MutableLiveData<>();

    @Inject
    public SearchViewModel(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    LiveData<List<QuoteModel>> getQuotes() {
        return quotes;
    }

    LiveData<List<Place>> getPlaces() {
        return places;
    }

    LiveData<Boolean> getError() {
        return quoteLoadError;
    }

    LiveData<Boolean> getPlaceError() {
        return placeLoadError;
    }

    LiveData<Boolean> getLoading() {
        return loading;
    }

    LiveData<Boolean> getPlaceLoading() {
        return placeLoading;
    }

    LiveData<Place> getFromData() {
        return from;
    }

    LiveData<Place> getToData() {
        return to;
    }

    LiveData<Date> getDate() {
        return date;
    }

    public void dateSelected(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        date.setValue(calendar.getTime());
    }

    public void fromSelected(Place place) {
        from.setValue(place);
    }

    public void toSelected(Place place) {
        to.setValue(place);
    }

    public void requestQuotes() {
        Place origin = from.getValue();
        Place dest = to.getValue();
        Date sDate = date.getValue();

        if (origin != null && dest != null && date != null) {
            requestQuotes(origin, dest, sDate);
        } else {
            //TODO: show dialog to complete fields
        }

    }

    private void requestQuotes(Place origin, Place destination, Date date) {
        loading.setValue(true);
        addRxSubscription(appRepository.getOneDirectionQuotes(origin, destination, date)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<QuoteSearchResponse>() {
                    @Override
                    public void onSuccess(QuoteSearchResponse value) {
                        if (value != null) {
                            succesLoad();
                            quotes.setValue(processResponse(value));

                        } else {
                            errorLoad();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                        errorLoad();
                    }
                }));
    }

    private void succesLoad() {
        quoteLoadError.setValue(false);
        loading.setValue(false);
    }

    private void errorLoad() {
        quoteLoadError.setValue(true);
        loading.setValue(false);
    }

    public void requestLocations(String search) {
        placeLoading.setValue(true);
        addRxSubscription(appRepository.getPlaces(search)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<PlacesResponse>() {
                    @Override
                    public void onSuccess(PlacesResponse value) {
                        if (value != null) {
                            succesPlaceLoad();
                            places.setValue(value.getPlaces());

                        } else {
                            errorPlaceLoad();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                        errorPlaceLoad();
                    }
                }));
    }

    private void succesPlaceLoad() {
        placeLoadError.setValue(false);
        placeLoading.setValue(false);
    }

    private void errorPlaceLoad() {
        if(placeLoadError.getValue()== null || !placeLoadError.getValue()){
            placeLoadError.setValue(true);
            placeLoadError.setValue(false);
        }
        placeLoading.setValue(false);
    }



    private List<QuoteModel> processResponse(QuoteSearchResponse response) {
        List<QuoteModel> quoteModels = new ArrayList<>();

        Map<Long, String> carrierMap = response.getCarriers().stream()
                .collect(Collectors.toMap(QuoteCarrier::getCarrierId, QuoteCarrier::getCarrierName));

        Map<Long, String> placeMap = response.getPlaces().stream()
                .collect(Collectors.toMap(QuotePlace::getPlaceId, QuotePlace::getName));

        NumberFormat numberFormatter;
        QuoteCurrency quoteCurrency = response.getCurrencies().get(0);
        if(quoteCurrency!=null){
            numberFormatter = Helper.getCurrencyFormat(quoteCurrency.getCode(), quoteCurrency.getDecimalDigits());
        } else{
            numberFormatter = Helper.getCurrencyFormat(appRepository.getCurrency(), 2);
        }

        for (Quote quote : response.getQuotes()) {
            QuoteModel model = new QuoteModel();
            model.setId(quote.getQuoteId());
            model.setPrice(numberFormatter.format(quote.getMinPrice()));
            model.setOrigin(placeMap.get(quote.getOutboundLeg().getOriginId()));
            model.setDestination(placeMap.get(quote.getOutboundLeg().getDestinationId()));
            model.setCarrierName(getCarrierNames(quote.getOutboundLeg().getCarrierIds(), carrierMap));
            quoteModels.add(model);
        }

        return quoteModels;

    }

    private String getCarrierNames(long[] carrierIds, Map<Long, String> carriers) {
        String result = "";
        if (carrierIds == null || carrierIds.length == 0) return result;

        StringJoiner joiner = new StringJoiner(",");

        for (int i = 0; i < carrierIds.length; i++) {
            joiner.add(carriers.get(carrierIds[i]));
        }

        result = joiner.toString();
        return result;
    }




}
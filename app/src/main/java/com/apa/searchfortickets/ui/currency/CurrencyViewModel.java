package com.apa.searchfortickets.ui.currency;

import androidx.lifecycle.MutableLiveData;

import com.apa.searchfortickets.data.AppRepository;
import com.apa.searchfortickets.data.local.model.CurrencyModel;
import com.apa.searchfortickets.data.remote.response.CurrenciesResponse;
import com.apa.searchfortickets.data.remote.response.QuoteCurrency;
import com.apa.searchfortickets.ui.BaseViewModel;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by apavlenco on 8/27/20.
 */
public class CurrencyViewModel extends BaseViewModel {

    private final AppRepository appRepository;

    private final MutableLiveData<List<CurrencyModel>> currencies = new MutableLiveData<>();
    private final MutableLiveData<Boolean> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    @Inject
    public CurrencyViewModel(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    public MutableLiveData<List<CurrencyModel>> getCurrencies() {
        return currencies;
    }

    public MutableLiveData<Boolean> getError() {
        return error;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public void setCurrency(CurrencyModel currencyModel) {
        appRepository.saveCurrency(currencyModel.getCode());
    }

    public void requestCurrencies() {
        loading.setValue(true);
        addRxSubscription(appRepository.getCurrencies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<CurrenciesResponse>() {
                    @Override
                    public void onSuccess(CurrenciesResponse value) {
                        if (value != null) {
                            succesLoad();
                            currencies.setValue(processResponse(value));

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

    private List<CurrencyModel> processResponse(CurrenciesResponse response) {
        List<CurrencyModel> quoteModels = new ArrayList<>();

        for (QuoteCurrency quoteCurrency : response.getCurrencies()) {
            CurrencyModel model = new CurrencyModel();
            model.setCode(quoteCurrency.getCode());
            model.setSymbol(quoteCurrency.getSymbol());
            model.setName(Currency.getInstance(quoteCurrency.getCode()).getDisplayName());
            quoteModels.add(model);
        }

        return quoteModels;
    }

    private void succesLoad() {
        error.setValue(false);
        loading.setValue(false);
    }

    private void errorLoad() {
        error.setValue(true);
        loading.setValue(false);
    }
}

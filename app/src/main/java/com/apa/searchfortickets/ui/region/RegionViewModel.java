package com.apa.searchfortickets.ui.region;

import androidx.lifecycle.MutableLiveData;

import com.apa.searchfortickets.data.AppRepository;
import com.apa.searchfortickets.data.local.model.RegionModel;
import com.apa.searchfortickets.data.remote.response.Market;
import com.apa.searchfortickets.data.remote.response.MarketsResponse;
import com.apa.searchfortickets.ui.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by apavlenco on 8/27/20.
 */

public class RegionViewModel extends BaseViewModel {

    private final AppRepository appRepository;

    private final MutableLiveData<List<RegionModel>> regions = new MutableLiveData<>();
    private final MutableLiveData<Boolean> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    @Inject
    public RegionViewModel(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    public MutableLiveData<List<RegionModel>> getRegions() {
        return regions;
    }

    public MutableLiveData<Boolean> getError() {
        return error;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public void setRegion(RegionModel regionModel) {
        appRepository.saveRegion(regionModel.getCode());
    }

    public void requestMarkets() {
        loading.setValue(true);
        addRxSubscription(appRepository.getMarkets()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<MarketsResponse>() {
                    @Override
                    public void onSuccess(MarketsResponse value) {
                        if (value != null) {
                            succesLoad();
                            regions.setValue(processResponse(value));

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

    private List<RegionModel> processResponse(MarketsResponse response) {
        List<RegionModel> regionModels = new ArrayList<>();

        for (Market market : response.getMarkets()) {
            RegionModel model = new RegionModel();
            model.setCode(market.getCode());
            model.setName(market.getName());
            regionModels.add(model);
        }

        return regionModels;
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

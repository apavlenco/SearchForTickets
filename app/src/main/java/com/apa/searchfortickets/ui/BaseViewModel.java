package com.apa.searchfortickets.ui;

import androidx.lifecycle.ViewModel;

import com.apa.searchfortickets.data.AppRepository;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by apavlenco on 8/24/20.
 */
public class BaseViewModel extends ViewModel {

    @Inject
    AppRepository mRepository;

    private CompositeDisposable compositeSubscription = new CompositeDisposable();

    protected void addRxSubscription(Disposable subscription) {
        if (compositeSubscription != null) compositeSubscription.add(subscription);
    }

    protected void clearRxSubscriptions() {
        if (compositeSubscription != null) compositeSubscription.clear();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (compositeSubscription != null) {
            compositeSubscription.clear();
            compositeSubscription = null;
        }
    }
}

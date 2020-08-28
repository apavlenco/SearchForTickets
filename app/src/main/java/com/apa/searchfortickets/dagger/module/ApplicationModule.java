package com.apa.searchfortickets.dagger.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.telephony.TelephonyManager;

import com.apa.searchfortickets.R;
import com.apa.searchfortickets.data.AppRepository;
import com.apa.searchfortickets.data.local.LocalDataStore;
import com.apa.searchfortickets.data.remote.RemoteDataStore;
import com.apa.searchfortickets.data.remote.api.ApiInterface;
import com.apa.searchfortickets.data.remote.api.GsonHelper;
import com.apa.searchfortickets.data.remote.api.OfflineInterceptor;
import com.apa.searchfortickets.data.remote.api.OnlineInterceptor;
import com.apa.searchfortickets.util.Constants;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by apavlenco on 8/24/20.
 */
@Module(includes = ViewModelModule.class)
public class ApplicationModule {

    @Provides
    @Singleton
    AppRepository providesAppRepository(Context context, LocalDataStore localDataStore,
                                        RemoteDataStore remoteDataStore,
                                        TelephonyManager telephonyManager) {
        return new AppRepository(context, localDataStore, remoteDataStore, telephonyManager);
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Context context) {
        return context.getApplicationContext().getSharedPreferences(context.getString(R.string.prefs_name), Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    LocalDataStore providesLocalDataStore(AssetManager assetManager, SharedPreferences sharedPreferences) {
        return new LocalDataStore(assetManager, sharedPreferences);
    }

    @Provides
    AssetManager provideAssetManager(Context context) {
        return context.getAssets();
    }

    @Provides
    @Singleton
    RemoteDataStore providesRemoteDataStore(ApiInterface apiInterface) {
        return new RemoteDataStore(apiInterface);
    }

    @Provides
    @Singleton
    ApiInterface providesApiClient(Retrofit retrofit) {
        return retrofit.create(ApiInterface.class);
    }

    @Provides
    Retrofit getRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonHelper.getGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    OkHttpClient getOkHttpClient(Cache cache, OnlineInterceptor onlineInterceptor,
                                 OfflineInterceptor offlineInterceptor,
                                 HttpLoggingInterceptor httpLoggingInterceptor) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(offlineInterceptor)
                .addNetworkInterceptor(onlineInterceptor)
                .readTimeout(Constants.READ_TIMEOUT_SEC, TimeUnit.SECONDS)
                .connectTimeout(Constants.CONNECT_TIMEOUT_SEC, TimeUnit.SECONDS)
                .writeTimeout(Constants.WRITE_TIMEOUT_SEC, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    @Provides
    HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return httpLoggingInterceptor;
    }

    @Provides
    Cache getCache(Context context) {
        return new Cache(context.getCacheDir(), Constants.CACHE_SIZE);
    }

    @Provides
    OnlineInterceptor getOnlineInterceptor(Context context) {
        return new OnlineInterceptor();
    }

    @Provides
    OfflineInterceptor getOfflineInterceptor(Context context) {
        return new OfflineInterceptor(context);
    }

    @Provides
    TelephonyManager getTelephonyManager(Context context) {
        return (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }
}

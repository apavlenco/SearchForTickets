package com.apa.searchfortickets.data.remote.api;

import android.content.Context;

import com.apa.searchfortickets.util.Constants;
import com.apa.searchfortickets.util.Helper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by apavlenco on 8/26/20.
 *
 * Overriding caching policy headers when not connected to receive cached data
 */
public class OfflineInterceptor implements Interceptor {
    private Context context;

    public OfflineInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (!Helper.isConnected(context)) {
            request = request.newBuilder()
                    .header(Constants.HEADER_PRAGMA, "cache")
                    .header(Constants.HEADER_CACHE_CONTROL, "public, only-if-cached, max-stale=" + Constants.CACHE_LIFETIEME)
                    .build();
        }

        return chain.proceed(request);
    }
}

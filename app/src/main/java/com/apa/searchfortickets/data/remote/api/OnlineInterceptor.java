package com.apa.searchfortickets.data.remote.api;

import com.apa.searchfortickets.util.Constants;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by apavlenco on 8/26/20.
 *
 * Overrides server response headers so that http client automatically caches response
 */
public class OnlineInterceptor implements Interceptor {

    public OnlineInterceptor() {
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        String cacheControl = originalResponse.header(Constants.HEADER_CACHE_CONTROL);
        if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
            return originalResponse.newBuilder()
                    .removeHeader(Constants.HEADER_VARY)
                    .header(Constants.HEADER_PRAGMA, "cache")
                    .header(Constants.HEADER_CACHE_CONTROL, "public, max-age=" + Constants.MAX_AGE)
                    .build();
        } else {
            return originalResponse;
        }

    }


}

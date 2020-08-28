package com.apa.searchfortickets.dagger.module;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

/**
 * Created by apavlenco on 8/24/20.
 */
@Module
public abstract class ContextModule {

    @Binds
    abstract Context provideContext(Application application);
}

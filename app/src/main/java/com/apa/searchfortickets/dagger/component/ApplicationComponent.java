package com.apa.searchfortickets.dagger.component;

import android.app.Application;

import com.apa.searchfortickets.base.MainApplication;
import com.apa.searchfortickets.dagger.module.ActivityBindingModule;
import com.apa.searchfortickets.dagger.module.ApplicationModule;
import com.apa.searchfortickets.dagger.module.ContextModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;

/**
 * Created by apavlenco on 8/24/20.
 */
@Singleton
@Component(modules = {ContextModule.class, ApplicationModule.class, AndroidSupportInjectionModule.class, ActivityBindingModule.class})
public interface ApplicationComponent extends AndroidInjector<DaggerApplication> {

    void inject(MainApplication application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }
}

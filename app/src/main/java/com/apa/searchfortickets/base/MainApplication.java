package com.apa.searchfortickets.base;

import android.util.Log;

import androidx.annotation.NonNull;

import com.apa.searchfortickets.BuildConfig;
import com.apa.searchfortickets.R;
import com.apa.searchfortickets.dagger.component.ApplicationComponent;
import com.apa.searchfortickets.dagger.component.DaggerApplicationComponent;
import com.facebook.stetho.Stetho;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import timber.log.Timber;

/**
 * Created by apavlenco on 8/24/20.
 *
 * Main Application class
 */
public class MainApplication extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        // initialize logger for debug and Crash reports
        initTimber();

        // setup custom fonts
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/avenir_book_01.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        ApplicationComponent component = DaggerApplicationComponent.builder().application(this).build();
        component.inject(this);

        return component;
    }

    private void initTimber() {
        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree() {
                @Nullable
                @Override
                protected String createStackElementTag(@NotNull StackTraceElement element) {
                    return super.createStackElementTag(element) + ":" + element.getLineNumber();
                }
            });
        }
        else {
            // for production builds
            Timber.plant(new CrashReportingTree());
        }
    }


    /**
     * logs important information for crash reporting
     */
    private static class CrashReportingTree extends Timber.Tree {
        @Override protected void log(int priority, String tag, @NonNull String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;// do not log Verbose or Debug statements in crash reporting
            }
            //TODO:log event to Firebase Crashlitics

        }
    }
}

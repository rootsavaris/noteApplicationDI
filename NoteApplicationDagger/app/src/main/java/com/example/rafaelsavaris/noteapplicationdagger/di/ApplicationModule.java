package com.example.rafaelsavaris.noteapplicationdagger.di;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

/**
 * Created by rafael.savaris on 30/01/2018.
 */

@Module
public abstract class ApplicationModule {

    @Binds
    abstract Context bindContext(Application application);

}

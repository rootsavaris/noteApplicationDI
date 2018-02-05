package com.example.rafaelsavaris.noteapplicationdi;

import com.example.rafaelsavaris.noteapplicationdi.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * Created by rafael.savaris on 30/01/2018.
 */

public class NoteApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

}

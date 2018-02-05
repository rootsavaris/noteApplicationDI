package com.example.rafaelsavaris.noteapplicationdi.di;

import android.app.Application;

import com.example.rafaelsavaris.noteapplicationdi.NoteApplication;
import com.example.rafaelsavaris.noteapplicationdi.NotesRepositoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by rafael.savaris on 30/01/2018.
 */

@Singleton
@Component(modules = {ApplicationModule.class, AndroidSupportInjectionModule.class, ActivityBindingModule.class, NotesRepositoryModule.class})
public interface AppComponent extends AndroidInjector<NoteApplication>{

    @Component.Builder
    interface Builer{

        @BindsInstance
        AppComponent.Builer application(Application application);

        AppComponent build();

    }

}

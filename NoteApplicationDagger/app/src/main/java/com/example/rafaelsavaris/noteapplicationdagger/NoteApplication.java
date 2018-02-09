package com.example.rafaelsavaris.noteapplicationdagger;

import com.example.rafaelsavaris.noteapplicationdagger.data.source.NotesRepository;
import com.example.rafaelsavaris.noteapplicationdagger.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * Created by rafael.savaris on 30/01/2018.
 */

public class NoteApplication extends DaggerApplication {

    @Inject
    NotesRepository mNotesRepository;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

    public NotesRepository getNotesRepository(){
        return mNotesRepository;
    }

}

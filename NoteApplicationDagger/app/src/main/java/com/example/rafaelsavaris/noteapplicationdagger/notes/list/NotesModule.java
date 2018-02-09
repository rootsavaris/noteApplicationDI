package com.example.rafaelsavaris.noteapplicationdagger.notes.list;

import com.example.rafaelsavaris.noteapplicationdagger.di.ActivityScoped;
import com.example.rafaelsavaris.noteapplicationdagger.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by rafael.savaris on 30/01/2018.
 */

@Module
public abstract class NotesModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract NotesFragment notesFragment();

    @ActivityScoped
    @Binds
    abstract NotesContract.Presenter notesPresenter(NotesPresenter notesPresenter);

}

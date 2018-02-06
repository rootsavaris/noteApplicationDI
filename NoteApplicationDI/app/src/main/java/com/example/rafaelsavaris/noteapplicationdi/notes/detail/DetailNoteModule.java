package com.example.rafaelsavaris.noteapplicationdi.notes.detail;

import com.example.rafaelsavaris.noteapplicationdi.di.ActivityScoped;
import com.example.rafaelsavaris.noteapplicationdi.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by rafael.savaris on 06/02/2018.
 */
@Module
public abstract class DetailNoteModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract DetailNoteFragment detailNoteFragment();

    @ActivityScoped
    @Binds
    abstract DetailNoteContract.Presenter presenter(DetailNotePresenter detailNotePresenter);

    @Provides
    @ActivityScoped
    static String provideNoteId(DetailNoteActivity detailNoteActivity){
        return detailNoteActivity.getIntent().getStringExtra(DetailNoteActivity.NOTE_ID);
    }

}

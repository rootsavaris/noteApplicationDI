package com.example.rafaelsavaris.noteapplicationdagger.notes.add;

import android.support.annotation.Nullable;


import com.example.rafaelsavaris.noteapplicationdagger.di.ActivityScoped;
import com.example.rafaelsavaris.noteapplicationdagger.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by rafael.savaris on 01/02/2018.
 */
@Module
public abstract class AddEditNoteModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract AddEditNoteFragment addEditNoteFragment();

    @ActivityScoped
    @Binds
    abstract AddEditNoteContract.Presenter presenter(AddEditNotePresenter presenter);

    @Provides
    @ActivityScoped
    @Nullable
    static String provideNoteId(AddEditNoteActivity addEditNoteActivity){
        return addEditNoteActivity.getIntent().getStringExtra(AddEditNoteFragment.NOTE_ID);
    }

    @Provides
    @ActivityScoped
    static boolean provideLoadDataFromRepository(AddEditNoteActivity addEditNoteActivity){
        return addEditNoteActivity.isLoadDataFromRepository();
    }

}

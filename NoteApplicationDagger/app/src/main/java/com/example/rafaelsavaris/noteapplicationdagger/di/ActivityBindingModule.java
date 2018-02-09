package com.example.rafaelsavaris.noteapplicationdagger.di;


import com.example.rafaelsavaris.noteapplicationdagger.notes.add.AddEditNoteActivity;
import com.example.rafaelsavaris.noteapplicationdagger.notes.add.AddEditNoteModule;
import com.example.rafaelsavaris.noteapplicationdagger.notes.detail.DetailNoteActivity;
import com.example.rafaelsavaris.noteapplicationdagger.notes.detail.DetailNoteModule;
import com.example.rafaelsavaris.noteapplicationdagger.notes.list.NotesActivity;
import com.example.rafaelsavaris.noteapplicationdagger.notes.list.NotesModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by rafael.savaris on 31/01/2018.
 */

@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = NotesModule.class)
    abstract NotesActivity notesActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = AddEditNoteModule.class)
    abstract AddEditNoteActivity addEditNoteActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = DetailNoteModule.class)
    abstract DetailNoteActivity detailNoteActivity();

}

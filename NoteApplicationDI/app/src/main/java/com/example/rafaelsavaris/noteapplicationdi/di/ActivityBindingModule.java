package com.example.rafaelsavaris.noteapplicationdi.di;

import com.example.rafaelsavaris.noteapplicationdi.notes.add.AddEditNoteActivity;
import com.example.rafaelsavaris.noteapplicationdi.notes.add.AddEditNoteModule;
import com.example.rafaelsavaris.noteapplicationdi.notes.list.NotesActivity;
import com.example.rafaelsavaris.noteapplicationdi.notes.list.NotesModule;

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

}

package com.example.rafaelsavaris.noteapplicationdagger;

import android.app.Application;
import android.arch.persistence.room.Room;


import com.example.rafaelsavaris.noteapplicationdagger.data.source.Local;
import com.example.rafaelsavaris.noteapplicationdagger.data.source.NotesDatasource;
import com.example.rafaelsavaris.noteapplicationdagger.data.source.Remote;
import com.example.rafaelsavaris.noteapplicationdagger.data.source.local.NoteDao;
import com.example.rafaelsavaris.noteapplicationdagger.data.source.local.NoteDatabase;
import com.example.rafaelsavaris.noteapplicationdagger.data.source.local.NotesLocalDataSource;
import com.example.rafaelsavaris.noteapplicationdagger.data.source.remote.NotesRemoteDataSource;
import com.example.rafaelsavaris.noteapplicationdagger.utils.AppExecutors;
import com.example.rafaelsavaris.noteapplicationdagger.utils.DiskIOThreadExecutor;
import com.example.rafaelsavaris.noteapplicationdagger.utils.MainThreadExecutor;

import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rafael.savaris on 30/01/2018.
 */
@Module
public class NotesRepositoryModule {

    private static final int THREAD_COUNT = 3;

    @Singleton
    @Provides
    @Local
    NotesDatasource provideNotesLocalDataSource(NoteDao noteDao, AppExecutors executors){
        return new NotesLocalDataSource(executors, noteDao);
    }

    @Singleton
    @Provides
    @Remote
    NotesDatasource provideNotesRemoteDataSource(){
        return new NotesRemoteDataSource();
    }

    @Singleton
    @Provides
    NoteDao provideNoteDao(NoteDatabase noteDatabase){
        return noteDatabase.noteDao();
    }

    @Singleton
    @Provides
    NoteDatabase provideDatabase(Application context){
        return Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "NoteDatabase.db").build();
    }

    @Singleton
    @Provides
    AppExecutors provideAppExecutors(){
        return new AppExecutors(new DiskIOThreadExecutor(), Executors.newFixedThreadPool(THREAD_COUNT), new MainThreadExecutor());
    }

}

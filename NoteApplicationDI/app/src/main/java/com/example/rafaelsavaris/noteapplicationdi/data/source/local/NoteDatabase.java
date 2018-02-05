package com.example.rafaelsavaris.noteapplicationdi.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.rafaelsavaris.noteapplicationdi.data.model.Note;

/**
 * Created by rafael.savaris on 04/01/2018.
 */

@Database(exportSchema = false, entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
}

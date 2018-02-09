package com.example.rafaelsavaris.noteapplicationdagger.data.source.local;


import com.example.rafaelsavaris.noteapplicationdagger.data.model.Note;
import com.example.rafaelsavaris.noteapplicationdagger.data.source.NotesDatasource;
import com.example.rafaelsavaris.noteapplicationdagger.utils.AppExecutors;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by rafael.savaris on 18/10/2017.
 */

@Singleton
public class NotesLocalDataSource implements NotesDatasource {

    private NoteDao mNoteDao;

    private AppExecutors mAppExecutors;

    @Inject
    public NotesLocalDataSource(AppExecutors appExecutors, NoteDao noteDao) {
        mAppExecutors = appExecutors;
        mNoteDao = noteDao;
    }

    @Override
    public void getNotes(final LoadNotesCallBack loadNotesCallBack) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                final List<Note> notes = mNoteDao.getNotes();

                mAppExecutors.getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {

                        if (notes.isEmpty()){
                            loadNotesCallBack.onDataNotAvailable();
                        } else{
                            loadNotesCallBack.onNotesLoaded(notes);
                        }

                    }
                });
            }
        };

        mAppExecutors.getDiskIO().execute(runnable);

    }

    @Override
    public void getNote(final String noteId, final GetNoteCallBack getNoteCallBack) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                final Note note = mNoteDao.getNoteById(noteId);

                mAppExecutors.getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {

                        if (note != null){
                            getNoteCallBack.onNoteLoaded(note);
                        } else {
                            getNoteCallBack.onDataNotAvailable();
                        }

                    }
                });

            }
        };

        mAppExecutors.getDiskIO().execute(runnable);

    }

    @Override
    public void deleteAllNotes() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mNoteDao.deleteNotes();
            }
        };

        mAppExecutors.getDiskIO().execute(runnable);

    }

    @Override
    public void saveNote(final Note note) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mNoteDao.insertNote(note);
            }
        };

        mAppExecutors.getDiskIO().execute(runnable);

    }

    @Override
    public void refreshNotes() {
    }

    @Override
    public void markNote(final Note note) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mNoteDao.updateMarked(note.getId(), true);
            }
        };

        mAppExecutors.getDiskIO().execute(runnable);

    }

    @Override
    public void markNote(String noteId) {
    }

    @Override
    public void unMarkNote(final Note note) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mNoteDao.updateMarked(note.getId(), false);
            }
        };

        mAppExecutors.getDiskIO().execute(runnable);

    }

    @Override
    public void unMarkNote(String noteId) {

    }

    @Override
    public void clearMarkedNotes() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mNoteDao.deleteMarkedNotes();
            }
        };

        mAppExecutors.getDiskIO().execute(runnable);

    }

    @Override
    public void deleteNote(final String noteId) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mNoteDao.deleteNoteById(noteId);
            }
        };

        mAppExecutors.getDiskIO().execute(runnable);

    }

}

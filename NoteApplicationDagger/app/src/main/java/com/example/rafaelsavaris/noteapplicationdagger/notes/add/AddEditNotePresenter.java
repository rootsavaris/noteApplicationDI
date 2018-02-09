package com.example.rafaelsavaris.noteapplicationdagger.notes.add;

import android.support.annotation.Nullable;

import com.example.rafaelsavaris.noteapplicationdagger.data.model.Note;
import com.example.rafaelsavaris.noteapplicationdagger.data.source.NotesDatasource;
import com.example.rafaelsavaris.noteapplicationdagger.data.source.NotesRepository;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * Created by rafael.savaris on 01/12/2017.
 */

public class AddEditNotePresenter implements AddEditNoteContract.Presenter, NotesDatasource.GetNoteCallBack {

    private boolean mIsMarked = false;

    private final NotesDatasource mNotesRepository;

    private AddEditNoteContract.View mView;

    private boolean mIsDataMissing;

    private Lazy<Boolean> mIsDataMissingLazy;

    @Nullable
    private String mNoteId;

    @Inject
    AddEditNotePresenter(@Nullable String noteId, NotesRepository notesRepository, Lazy<Boolean> shouldLoadDataFromRepo) {
        mNoteId = noteId;
        mNotesRepository = notesRepository;
        mIsDataMissingLazy = shouldLoadDataFromRepo;
    }

    @Override
    public void takeView(AddEditNoteContract.View view) {

        mView = view;

        mIsDataMissing = mIsDataMissingLazy.get();

        if (!isNewNote() && mIsDataMissing){
            populateNote();
        }

    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void saveNote(String title, String text) {

        if (isNewNote()){
            createNote(title, text);
        } else {
            updateNote(title, text);
        }

    }

    public void populateNote(){
        mNotesRepository.getNote(mNoteId, this);
    }

    public boolean isDataMissing() {
        return mIsDataMissing;
    }

    private boolean isNewNote(){
        return mNoteId == null;
    }

    @Override
    public void onNoteLoaded(Note note) {

        if (mView.isActive()){
            mView.setTitle(note.getTitle());
            mView.setText(note.getText());
            mIsMarked = note.isMarked();
        }

        mIsDataMissing = false;

    }

    @Override
    public void onDataNotAvailable() {

        if (mView.isActive()){
            mView.showEmptyNotesError();
        }

    }

    private void createNote(String title, String text){

        Note note = new Note(title, text);

        if (note.isEmpty()){
            mView.showEmptyNotesError();
        } else {
            mNotesRepository.saveNote(note);
            mView.showNotesList();
        }

    }

    private void updateNote(String title, String text){

        mNotesRepository.saveNote(new Note(title, text, mNoteId, mIsMarked));

        mView.showNotesList();

    }

}

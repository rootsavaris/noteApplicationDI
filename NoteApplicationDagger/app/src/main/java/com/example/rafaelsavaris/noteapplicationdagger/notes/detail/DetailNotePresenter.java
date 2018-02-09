package com.example.rafaelsavaris.noteapplicationdagger.notes.detail;

import com.example.rafaelsavaris.noteapplicationdagger.data.model.Note;
import com.example.rafaelsavaris.noteapplicationdagger.data.source.NotesDatasource;
import com.example.rafaelsavaris.noteapplicationdagger.data.source.NotesRepository;
import com.google.common.base.Strings;

import javax.inject.Inject;

/**
 * Created by rafael.savaris on 02/01/2018.
 */

public class DetailNotePresenter implements DetailNoteContract.Presenter {

    private String mNoteId;

    private final NotesRepository mNotesRepository;

    private DetailNoteContract.View mView;

    @Inject
    public DetailNotePresenter(String noteId, NotesRepository notesRepository) {
        mNoteId = noteId;
        mNotesRepository = notesRepository;
    }

    @Override
    public void takeView(DetailNoteContract.View view){

        mView = view;

        if (Strings.isNullOrEmpty(mNoteId)){
            mView.showMissingNote();
            return;
        }

        mView.setLoadingIndicator(true);
        mNotesRepository.getNote(mNoteId, new NotesDatasource.GetNoteCallBack() {

            @Override
            public void onNoteLoaded(Note note) {

                if (!mView.isActive()){
                    return;
                }

                mView.setLoadingIndicator(false);

                if (note == null){
                    mView.showMissingNote();
                } else {
                    showNote(note);
                }

            }

            @Override
            public void onDataNotAvailable() {

                if (!mView.isActive()){
                    return;
                }

                mView.showMissingNote();

            }
        });

    }

    @Override
    public void dropView() {
        mView = null;
    }

    private void showNote(Note note) {

        String title = note.getTitle();
        String description = note.getText();

        if (Strings.isNullOrEmpty(title)) {
            mView.hideTitle();
        } else {
            mView.showTitle(title);
        }

        if (Strings.isNullOrEmpty(description)) {
            mView.hideText();
        } else {
            mView.showText(description);
        }

        mView.showMarkedStatus(note.isMarked());

    }


    @Override
    public void editNote() {

        if (Strings.isNullOrEmpty(mNoteId)){
            mView.showMissingNote();
            return;
        }

        mView.showEditNote(mNoteId);

    }

    @Override
    public void markNote() {

        if (Strings.isNullOrEmpty(mNoteId)){
            mView.showMissingNote();
            return;
        }

        mNotesRepository.markNote(mNoteId);

        mView.showNoteMarked();

    }

    @Override
    public void unMarkNote() {

        if (Strings.isNullOrEmpty(mNoteId)){
            mView.showMissingNote();
            return;
        }

        mNotesRepository.unMarkNote(mNoteId);

        mView.showNoteUnMarked();

    }

    @Override
    public void deleteNote() {

        if (Strings.isNullOrEmpty(mNoteId)){
            mView.showMissingNote();
            return;
        }

        mNotesRepository.deleteNote(mNoteId);

        mView.showNoteDeleted();

    }

}

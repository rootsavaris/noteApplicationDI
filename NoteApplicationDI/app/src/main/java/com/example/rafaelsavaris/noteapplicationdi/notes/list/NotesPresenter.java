package com.example.rafaelsavaris.noteapplicationdi.notes.list;

import android.app.Activity;

import com.example.rafaelsavaris.noteapplicationdi.data.model.Note;
import com.example.rafaelsavaris.noteapplicationdi.data.source.NotesDatasource;
import com.example.rafaelsavaris.noteapplicationdi.data.source.NotesRepository;
import com.example.rafaelsavaris.noteapplicationdi.di.ActivityScoped;
import com.example.rafaelsavaris.noteapplicationdi.notes.add.AddEditNoteActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by rafael.savaris on 18/10/2017.
 */
@ActivityScoped
public class NotesPresenter implements NotesContract.Presenter {

    private final NotesRepository mRepository;

    private NotesContract.View mView;

    private NotesFilterType mFilterType = NotesFilterType.ALL_NOTES;

    private boolean firstLoad = true;

    @Inject
    NotesPresenter(NotesRepository notesRepository){
        mRepository = notesRepository;
    }

    public void setFilter(NotesFilterType mFilterType){
        this.mFilterType = mFilterType;
    }

    public NotesFilterType getFilter(){
        return this.mFilterType;
    }

    @Override
    public void result(int requestCode, int resultCode) {

        if (AddEditNoteActivity.REQUEST_ADD_NOTE == requestCode && Activity.RESULT_OK == resultCode){
            mView.showSuccessfullySavedMessage();
        }

    }

    @Override
    public void takeView(NotesContract.View view) {
        mView = view;
        loadNotes(false);
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void loadNotes(boolean forceUpdate) {

        loadNotes(forceUpdate || firstLoad, true);

        firstLoad = false;

    }

    @Override
    public void markNote(Note markedNote) {
        mRepository.markNote(markedNote);
        mView.showNoteMarked();
        loadNotes(false, false);
    }

    @Override
    public void unMarkNote(Note markedNote) {
        mRepository.unMarkNote(markedNote);
        mView.showNoteUnMarked();
        loadNotes(false, false);
    }

    @Override
    public void clearMarkedNotes() {
        mRepository.clearMarkedNotes();
        mView.showNotesCleared();
        loadNotes(false, false);
    }

    @Override
    public void addNewNote() {
        mView.showAddNewNote();
    }

    @Override
    public void openNoteDetails(Note note) {
        mView.showNoteDetailUi(note.getId());
    }

    private void loadNotes(boolean forceUpdate, final boolean showLoading){

        if (showLoading){
            mView.setLoadingIndicator(true);
        }

        if (forceUpdate){
            mRepository.refreshNotes();
        }

        mRepository.getNotes(new NotesDatasource.LoadNotesCallBack() {

            @Override
            public void onNotesLoaded(List<Note> notes) {

                List<Note> notesToShow = new ArrayList<>();

                for (Note note : notes){

                    switch (mFilterType){

                        case MARKED_NOTES:

                            if(note.isMarked()){
                                notesToShow.add(note);
                            }

                            break;

                         default:
                             notesToShow.add(note);
                    }

                }

                if (!mView.isActive()){
                    return;
                }

                if (showLoading){
                    mView.setLoadingIndicator(false);
                }

                processNotes(notesToShow);

            }

            @Override
            public void onDataNotAvailable() {

                if (!mView.isActive()){
                    return;
                }

                mView.showLoadingNotesError();

            }

        });

    }

    private void processNotes(List<Note> notes){

        if (notes.isEmpty()){
            processEmptyNotes();
        } else {
            mView.showNotes(notes);
            showFilterLabel();
        }

    }

    private void processEmptyNotes(){

        switch (mFilterType){

            case MARKED_NOTES:
                mView.showNoMarkedNotes();
                break;

            default:
                mView.showNoNotes();
                break;

        }

    }

    private void showFilterLabel(){

        switch (mFilterType){

            case MARKED_NOTES:
                mView.showMarkedFilterLabel();
                break;
            default:
                mView.showAllFilterLabel();
                break;

        }

    }

}

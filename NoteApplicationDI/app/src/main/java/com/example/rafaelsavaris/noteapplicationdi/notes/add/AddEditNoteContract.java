package com.example.rafaelsavaris.noteapplicationdi.notes.add;

import com.example.rafaelsavaris.noteapplicationdi.BasePresenter;
import com.example.rafaelsavaris.noteapplicationdi.BaseView;

/**
 * Created by rafael.savaris on 01/12/2017.
 */

public interface AddEditNoteContract {

    interface Presenter extends BasePresenter<View> {

        void saveNote(String title, String text);

        void populateNote();

        boolean isDataMissing();

    }

    interface View extends BaseView<Presenter> {

        void showEmptyNotesError();

        void showNotesList();

        boolean isActive();

        void setTitle(String title);

        void setText(String text);

    }


}

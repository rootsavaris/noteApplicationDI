package com.example.rafaelsavaris.noteapplicationdagger.notes.add;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.rafaelsavaris.noteapplicationdagger.R;
import com.example.rafaelsavaris.noteapplicationdagger.di.ActivityScoped;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * Created by rafael.savaris on 01/12/2017.
 */

@ActivityScoped
public class AddEditNoteFragment extends DaggerFragment implements AddEditNoteContract.View {

    public static final String NOTE_ID = "NOTE_ID";

    @Inject
    AddEditNoteContract.Presenter mAddEditNotePresenter;

    private TextView mTitle;

    private TextView mText;

    @Inject
    public AddEditNoteFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        mAddEditNotePresenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAddEditNotePresenter.dropView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.add_edit_note_frag, container, false);
        mTitle = root.findViewById(R.id.add_edit_note_title);
        mText = root.findViewById(R.id.add_edit_note_text);
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab_add_note_done);

        fab.setImageResource(R.drawable.ic_done);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddEditNotePresenter.saveNote(mTitle.getText().toString(), mText.getText().toString());
            }
        });

    }

    @Override
    public void showEmptyNotesError() {
        Snackbar.make(mTitle, getString(R.string.empty_note_text), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showNotesList() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    @Override
    public void setText(String text) {
        mText.setText(text);
    }

}

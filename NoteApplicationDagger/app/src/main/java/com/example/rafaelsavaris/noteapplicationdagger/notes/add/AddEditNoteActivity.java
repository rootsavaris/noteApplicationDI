package com.example.rafaelsavaris.noteapplicationdagger.notes.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;


import com.example.rafaelsavaris.noteapplicationdagger.R;
import com.example.rafaelsavaris.noteapplicationdagger.utils.ActivityUtils;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

/**
 * Created by rafael.savaris on 01/12/2017.
 */

public class AddEditNoteActivity extends DaggerAppCompatActivity {

    public static final String SHOULD_LOAD_DATA_FROM_REPO_KEY = "SHOULD_LOAD_DATA_FROM_REPO_KEY";

    public static final int REQUEST_ADD_NOTE = 1;

    private ActionBar mActionBar;

    @Inject
    AddEditNotePresenter mAddEditTaskPresenter;

    @Inject
    AddEditNoteFragment addEditNoteFragmentProvider;

    @Inject
    @Nullable
    String mNoteId;

    boolean shouldLoadDataFromRepo = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_edit_note_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);

        AddEditNoteFragment addEditNoteFragment = (AddEditNoteFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        setToolbarTitle(mNoteId);

        if (addEditNoteFragment == null) {

            addEditNoteFragment = addEditNoteFragmentProvider;

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    addEditNoteFragment, R.id.contentFrame);
        }


        if (savedInstanceState != null) {
            shouldLoadDataFromRepo = savedInstanceState.getBoolean(SHOULD_LOAD_DATA_FROM_REPO_KEY);
        }

    }

    private void setToolbarTitle(@Nullable String noteId) {

        if(noteId == null) {
            mActionBar.setTitle(R.string.add_note);
        } else {
            mActionBar.setTitle(R.string.edit_note);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save the state so that next time we know if we need to refresh com.example.android.architecture.blueprints.todoapp.data.
        outState.putBoolean(SHOULD_LOAD_DATA_FROM_REPO_KEY, mAddEditTaskPresenter.isDataMissing());
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    boolean isLoadDataFromRepository(){
        return shouldLoadDataFromRepo;
    }

}

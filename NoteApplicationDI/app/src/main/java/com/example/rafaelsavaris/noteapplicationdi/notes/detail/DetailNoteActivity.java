package com.example.rafaelsavaris.noteapplicationdi.notes.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.rafaelsavaris.noteapplicationdi.R;
import com.example.rafaelsavaris.noteapplicationdi.data.source.NotesRepository;
import com.example.rafaelsavaris.noteapplicationdi.utils.ActivityUtils;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

/**
 * Created by rafael.savaris on 01/12/2017.
 */

public class DetailNoteActivity extends DaggerAppCompatActivity {

    public static final String NOTE_ID = "NOTE_ID";

    @Inject
    DetailNoteFragment detailNoteFragmentProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.detail_note_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        DetailNoteFragment detailNoteFragment = (DetailNoteFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (detailNoteFragment == null) {

            detailNoteFragment = detailNoteFragmentProvider;

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    detailNoteFragment, R.id.contentFrame);
        }

    }

    public DetailNoteActivity() {
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}

package com.example.rafaelsavaris.noteapplicationdi.notes.list;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.rafaelsavaris.noteapplicationdi.R;
import com.example.rafaelsavaris.noteapplicationdi.data.source.NotesRepository;
import com.example.rafaelsavaris.noteapplicationdi.utils.ActivityUtils;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;


public class NotesActivity extends DaggerAppCompatActivity {

    private static final String CURRENT_FILTER_KEY = "CURRENT_FILTER_KEY";

    @Inject
    NotesPresenter notesPresenter;

    @Inject
    Lazy<NotesFragment> notesFragmentProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.notes_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        NotesFragment notesFragment = (NotesFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (notesFragment == null) {

            notesFragment = notesFragmentProvider.get();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), notesFragment, R.id.contentFrame);

        }

        if (savedInstanceState != null){

            NotesFilterType notesFilterType = (NotesFilterType) savedInstanceState.getSerializable(CURRENT_FILTER_KEY);

            notesPresenter.setFilter(notesFilterType);

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putSerializable(CURRENT_FILTER_KEY, notesPresenter.getFilter());

        super.onSaveInstanceState(outState);

    }
}

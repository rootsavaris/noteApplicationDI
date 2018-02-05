package com.example.rafaelsavaris.noteapplicationdi;

import com.example.rafaelsavaris.noteapplicationdi.notes.list.NotesContract;

/**
 * Created by rafael.savaris on 20/10/2017.
 */

public interface BasePresenter<T> {

    void takeView(T view);

    void dropView();

}

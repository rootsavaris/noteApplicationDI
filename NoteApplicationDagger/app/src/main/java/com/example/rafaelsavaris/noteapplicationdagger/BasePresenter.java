package com.example.rafaelsavaris.noteapplicationdagger;

/**
 * Created by rafael.savaris on 20/10/2017.
 */

public interface BasePresenter<T> {

    void takeView(T view);

    void dropView();

}

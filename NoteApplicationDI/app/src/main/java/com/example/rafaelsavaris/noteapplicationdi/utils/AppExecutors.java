package com.example.rafaelsavaris.noteapplicationdi.utils;

import android.support.annotation.VisibleForTesting;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by rafael.savaris on 04/01/2018.
 */

public class AppExecutors {

    private static final int THREAD_COUNT = 3;

    private final Executor mDiskIO;

    private final Executor mNetWorkIO;

    private final Executor mMainThread;

    public AppExecutors(Executor diskIO, Executor netWorkIO, Executor mainThread) {
        mDiskIO = diskIO;
        mNetWorkIO = netWorkIO;
        mMainThread = mainThread;
    }

    public Executor getDiskIO() {
        return mDiskIO;
    }

    public Executor getNetWorkIO() {
        return mNetWorkIO;
    }

    public Executor getMainThread() {
        return mMainThread;
    }
}

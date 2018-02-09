package com.example.rafaelsavaris.noteapplicationdagger.notes.detail;

import android.content.Intent;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.rafaelsavaris.noteapplicationdagger.R;
import com.example.rafaelsavaris.noteapplicationdagger.data.model.Note;
import com.example.rafaelsavaris.noteapplicationdagger.data.source.remote.MockRemoteDataSource;
import com.example.rafaelsavaris.noteapplicationdagger.notes.add.AddEditNoteFragment;
import com.example.rafaelsavaris.noteapplicationdagger.utils.TestUtils;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

/**
 * Created by rafael.savaris on 22/01/2018.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DetailNoteScreenTest {

    private final String TITLE = "title";
    private final String TEXT = "text";

    @Rule
    public ActivityTestRule<DetailNoteActivity> detailNoteActivityActivityTestRule = new ActivityTestRule<DetailNoteActivity>(DetailNoteActivity.class, true, false);

    @Test
    public void noteDetails_DisplayedUi(){
        loadNote();

        onView(withId(R.id.note_detail_title)).check(matches(withText(TITLE)));
        onView(withId(R.id.note_detail_text)).check(matches(withText(TEXT)));
        onView(withId(R.id.note_detail_marked)).check(matches(not(isChecked())));

    }

    @Test
    public void markedNoteDetails_DisplayedUi(){
        loadMarkedNote();

        onView(withId(R.id.note_detail_title)).check(matches(withText(TITLE)));
        onView(withId(R.id.note_detail_text)).check(matches(withText(TEXT)));
        onView(withId(R.id.note_detail_marked)).check(matches(isChecked()));

    }

    @Test
    public void orientationChange_menuAndNotePersist(){

        loadNote();

        onView(withId(R.id.menu_delete)).check(matches(isDisplayed()));

        TestUtils.rotateOrientation(detailNoteActivityActivityTestRule.getActivity());

        onView(withId(R.id.note_detail_title)).check(matches(withText(TITLE)));
        onView(withId(R.id.note_detail_text)).check(matches(withText(TEXT)));

        onView(withId(R.id.menu_delete)).check(matches(isDisplayed()));

    }

    private void loadNote(){

        Note note = new Note(TITLE, TEXT, false);

        launchNewActivity(note);

    }

    private void loadMarkedNote(){

        Note note = new Note(TITLE, TEXT, true);

        launchNewActivity(note);

    }

    private void launchNewActivity(Note note){

        new MockRemoteDataSource().addNotes(note);

        Intent intent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), DetailNoteActivity.class);

        intent.putExtra(DetailNoteActivity.NOTE_ID, note.getId());

        detailNoteActivityActivityTestRule.launchActivity(intent);

    }

}

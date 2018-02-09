package com.example.rafaelsavaris.noteapplicationdagger.notes.list;

import android.app.Application;
import android.content.Intent;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.rafaelsavaris.noteapplicationdagger.NoteApplication;
import com.example.rafaelsavaris.noteapplicationdagger.R;
import com.example.rafaelsavaris.noteapplicationdagger.data.model.Note;
import com.example.rafaelsavaris.noteapplicationdagger.data.source.remote.MockRemoteDataSource;
import com.example.rafaelsavaris.noteapplicationdagger.notes.add.AddEditNoteActivity;
import com.example.rafaelsavaris.noteapplicationdagger.notes.add.AddEditNoteFragment;
import com.example.rafaelsavaris.noteapplicationdagger.utils.TestUtils;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.AllOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.not;

/**
 * Created by rafael.savaris on 22/01/2018.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class NotesScreenTest {

    private final String TITLE = "title";

    private final String TEXT = "text";

    private String TITLE2 = "title2";

    private String TEXT2 = "text2";


    @Rule
    public ActivityTestRule<NotesActivity> noteActivityActivityTestRule = new ActivityTestRule<NotesActivity>(NotesActivity.class){

        @Override
        protected void beforeActivityLaunched() {
            super.beforeActivityLaunched();
            ((NoteApplication) InstrumentationRegistry.getTargetContext().getApplicationContext()).getNotesRepository().deleteAllNotes();
        }
    };

    private Matcher<View> withItemText(final String itemText){

        return new TypeSafeMatcher<View>() {

            @Override
            protected boolean matchesSafely(View item) {
                return allOf(
                        isDescendantOfA(isAssignableFrom(ListView.class)), withText(itemText)).matches(item);

            }

            @Override
            public void describeTo(Description description) {
            }
        };

    }

    @Test
    public void clickAddNoteButton_opensAddNoteUi(){

        onView(withId(R.id.fab_add_note)).perform(click());

        onView(withId(R.id.add_edit_note_title)).check(matches(isDisplayed()));

    }

    @Test
    public void addNoteToNotesList() throws Exception{

        createNote(TITLE, TEXT);

        onView(withItemText(TITLE)).check(matches(isDisplayed()));

    }

    @Test
    public void editNote(){

        createNote(TITLE, TEXT);

        onView(withText(TITLE)).perform(click());

        onView(withId(R.id.fab_edit_note)).perform(click());

        onView(withId(R.id.add_edit_note_title)).perform(replaceText(TITLE2), closeSoftKeyboard());

        onView(withId(R.id.add_edit_note_text)).perform(replaceText(TEXT2), closeSoftKeyboard());

        onView(withId(R.id.fab_add_note_done)).perform(click());

        onView(withItemText(TITLE2)).check(matches(isDisplayed()));

        onView(withItemText(TITLE)).check(doesNotExist());

    }

    @Test
    public void markNote(){

        viewAllNotes();

        createNote(TITLE, TEXT);

        clickCheckBoxForNote(TITLE);

        viewAllNotes();

        onView(withItemText(TITLE)).check(matches(isDisplayed()));

        viewMarkedNotes();

        onView(withItemText(TITLE)).check(matches(isDisplayed()));

    }


    @Test
    public void unMarkNote(){

        viewAllNotes();

        createNote(TITLE, TEXT);

        clickCheckBoxForNote(TITLE);

        clickCheckBoxForNote(TITLE);

        viewAllNotes();

        onView(withItemText(TITLE)).check(matches(isDisplayed()));

        viewMarkedNotes();

        onView(withItemText(TITLE)).check(matches(not(isDisplayed())));

    }

    @Test
    public void showAllNotes(){

        createNote(TITLE, TEXT);

        createNote(TITLE2, TEXT2);

        onView(withItemText(TITLE)).check(matches(isDisplayed()));

        onView(withItemText(TITLE2)).check(matches(isDisplayed()));

    }

    @Test
    public void showMarkedNotes(){

        createNote(TITLE, TEXT);

        clickCheckBoxForNote(TITLE);

        viewMarkedNotes();

        onView(withItemText(TITLE)).check(matches(isDisplayed()));

    }

    @Test
    public void clearMarkedNotes() {

        viewAllNotes();

        createNote(TITLE, TEXT);

        clickCheckBoxForNote(TITLE);

        openActionBarOverflowOrOptionsMenu(getTargetContext());

        onView(withText(R.string.menu_clear)).perform(click());

        onView(withItemText(TITLE)).check(matches(not(isDisplayed())));

    }

    @Test
    public void createOneNote_deleteNote(){

        viewAllNotes();

        createNote(TITLE, TEXT);

        onView(withText(TITLE)).perform(click());

        onView(withId(R.id.menu_delete)).perform(click());

        viewAllNotes();

        onView(withText(TITLE)).check(matches(not(isDisplayed())));

    }

    @Test
    public void createTwoNotes_deleteOneNote(){

        viewAllNotes();

        createNote(TITLE, TEXT);

        createNote(TITLE2, TEXT2);

        onView(withText(TITLE2)).perform(click());

        onView(withId(R.id.menu_delete)).perform(click());

        viewAllNotes();

        onView(withText(TITLE2)).check(doesNotExist());

        onView(withText(TITLE)).check(matches(isDisplayed()));

    }

    @Test
    public void markNoteOnDetailScreen_noteIsMarkedInList(){

        viewAllNotes();

        createNote(TITLE, TEXT);

        onView(withText(TITLE)).perform(click());

        onView(withId(R.id.note_detail_marked)).perform(click());

        onView(withContentDescription(getToolbarNavigationContentDescription())).perform(click());

        onView(AllOf.allOf(withId(R.id.mark), hasSibling(withText(TITLE)))).check(matches(isChecked()));

    }

    @Test
    public void unMarkNoteOnDetailScreen_noteIsUnmarkedInList(){

        viewAllNotes();

        createNote(TITLE, TEXT);

        clickCheckBoxForNote(TITLE);

        onView(withText(TITLE)).perform(click());

        onView(withId(R.id.note_detail_marked)).perform(click());

        onView(withContentDescription(getToolbarNavigationContentDescription())).perform(click());

        onView(AllOf.allOf(withId(R.id.mark), hasSibling(withText(TITLE)))).check(matches(not(isChecked())));

    }

    @Test
    public void markNoteAndUnMarkOnDetailScreen_noteIsUnmarkInList(){

        viewAllNotes();

        createNote(TITLE, TEXT);

        onView(withText(TITLE)).perform(click());

        onView(withId(R.id.note_detail_marked)).perform(click());

        onView(withId(R.id.note_detail_marked)).perform(click());

        onView(withContentDescription(getToolbarNavigationContentDescription())).perform(click());

        onView(AllOf.allOf(withId(R.id.mark), hasSibling(withText(TITLE)))).check(matches(not(isChecked())));

    }

    @Test
    public void orientationChange_FilterMarkedPersists(){

        createNote(TITLE, TEXT);

        clickCheckBoxForNote(TITLE);

        viewMarkedNotes();

        onView(withText(TITLE)).check(matches(isDisplayed()));

        TestUtils.rotateOrientation(noteActivityActivityTestRule.getActivity());

        onView(withText(TITLE)).check(matches(isDisplayed()));

        onView(withText(R.string.label_marked)).check(matches(isDisplayed()));

    }

    @Test
    public void orientationChange_DuringEdit_ChangePersists(){

        createNote(TITLE, TEXT);

        onView(withText(TITLE)).perform(click());

        onView(withId(R.id.fab_edit_note)).perform(click());

        onView(withId(R.id.add_edit_note_title)).perform(replaceText(TITLE2), closeSoftKeyboard());

        TestUtils.rotateOrientation(TestUtils.getCurrentActivity());

        onView(withId(R.id.add_edit_note_title)).check(matches(withText(TITLE2)));

    }

    @Test
    public void orientationChange_DuringEdit_NoDuplicate(){

        createNote(TITLE, TEXT);

        onView(withText(TITLE)).perform(click());

        onView(withId(R.id.fab_edit_note)).perform(click());

        TestUtils.rotateOrientation(TestUtils.getCurrentActivity());

        onView(withId(R.id.add_edit_note_title)).perform(replaceText(TITLE2), closeSoftKeyboard());

        onView(withId(R.id.add_edit_note_text)).perform(replaceText(TEXT2), closeSoftKeyboard());

        onView(withId(R.id.fab_add_note_done)).perform(click());

        onView(withItemText(TITLE2)).check(matches(isDisplayed()));

        onView(withItemText(TITLE)).check(doesNotExist());

    }


    private void createNote(String title, String text){

        onView(withId(R.id.fab_add_note)).perform(click());

        onView(withId(R.id.add_edit_note_title)).perform(typeText(title), closeSoftKeyboard());

        onView(withId(R.id.add_edit_note_text)).perform(typeText(text), closeSoftKeyboard());

        onView(withId(R.id.fab_add_note_done)).perform(click());

    }

    private void viewAllNotes(){
        onView(withId(R.id.menu_filter)).perform(click());
        onView(withText(R.string.nav_all)).perform(click());
    }

    private void viewMarkedNotes(){
        onView(withId(R.id.menu_filter)).perform(click());
        onView(withText(R.string.nav_marked)).perform(click());
    }

    private void clickCheckBoxForNote(String title){
        onView(AllOf.allOf(withId(R.id.mark), hasSibling(withText(title)))).perform(click());
    }

    private String getToolbarNavigationContentDescription(){
        return TestUtils.getToolbarNavigationContentDescription(noteActivityActivityTestRule.getActivity(), R.id.toolbar);
    }


}

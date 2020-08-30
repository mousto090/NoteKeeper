package com.example.notekeeper;

import android.os.Build;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;


import com.example.notekeeper.models.Course;
import com.example.notekeeper.models.DataManager;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

@RunWith(AndroidJUnit4.class)
public class NoteCreationTest {

    static DataManager sDataManager;
    @Rule
    public ActivityTestRule<NoteListActivity> mNoteListActivityActivityTestRule =
            new ActivityTestRule<>(NoteListActivity.class);

    @BeforeClass
    public static void setup() {
        sDataManager = DataManager.getInstance();
    }

    @Test
    public void createNewNote() {
        final Course course = sDataManager.getCourse("java_lang");
        String noteTitle = "Test note title";
        String noteText = "This is the note body of our test note";

        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.spinner_courses)).perform(click());
        onData(allOf(instanceOf(Course.class), equalTo(course))).perform(click());
        onView(withId(R.id.edit_text_note_title)).perform(typeText(noteTitle));
        onView(withId(R.id.edit_text_note_text)).perform(typeText(noteText), closeSoftKeyboard());
//        closeSoftKeyboard();
        pressBack();
    }
}
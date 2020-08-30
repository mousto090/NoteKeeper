package com.example.notekeeper;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.notekeeper.models.DataManager;
import com.example.notekeeper.models.Note;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

@RunWith(AndroidJUnit4.class)
public class NoteListActivityTest {
    private final String TAG = getClass().getSimpleName();
    static DataManager sDataManager;

    @Rule
    public ActivityTestRule<NoteListActivity> mActivityTestRule = new ActivityTestRule<>(NoteListActivity.class);

    @BeforeClass
    public static void setup() {
        sDataManager = DataManager.getInstance();
    }

    @Test
    public void editNote() {
        final Note note = sDataManager.getNotes().get(0);
        onData(allOf(instanceOf(Note.class), equalTo(note))).perform(click());
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getContext());
        onView(withText(R.string.action_cancel)).perform(click());

    }
}
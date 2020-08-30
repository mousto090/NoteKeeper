package com.example.notekeeper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.Until;

import com.example.notekeeper.models.DataManager;
import com.example.notekeeper.models.Note;

import org.junit.Before;
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
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class NoteListActivityUIAutomatorTest {

    private final String TAG = getClass().getSimpleName();
    private final String APP_PACKAGE = getClass().getPackage().getName();
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String STRING_TO_BE_TYPED = "UiAutomator";
    private UiDevice mDevice;

    private static DataManager sDataManager;

    @Rule
    public ActivityTestRule<NoteListActivity> mActivityTestRule = new ActivityTestRule<>(NoteListActivity.class);

    @BeforeClass
    public static void setUp() {
        sDataManager = DataManager.getInstance();
    }


    @Before
    public void startAppFromHomeScreen() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mDevice.pressHome();

        final String launcherPackage = mDevice.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        Context context = ApplicationProvider.getApplicationContext();
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(APP_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        mDevice.wait(Until.hasObject(By.pkg(APP_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void editNote() throws InterruptedException {
        final Note note = sDataManager.getNotes().get(0);
        onData(allOf(instanceOf(Note.class), equalTo(note))).perform(click());
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getContext());
        onView(withText(R.string.action_send_email)).perform(click());

        mDevice.pressBack();
        mDevice.pressBack();
        mDevice.wait(Until.gone(By.text("Compose")), LAUNCH_TIMEOUT);

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getContext());
        onView(withText(R.string.action_cancel)).perform(click());
    }

}
package com.example.notekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.example.notekeeper.models.Course;
import com.example.notekeeper.models.DataManager;
import com.example.notekeeper.models.Note;
import com.example.notekeeper.models.viewModels.NoteActivityViewModel;

import java.util.List;

public class NoteActivity extends AppCompatActivity {

    protected final String TAG = getClass().getSimpleName();
    private static final int POSITION_NOTE_SET = -1;
    private Spinner mSpinnerCourses;
    private EditText mEditTextNoteTitle;
    private EditText mEditTextNoteText;

    private NoteActivityViewModel mNoteActivityViewModel;
    private boolean mIsNewNote;
    private Note mNote;
    private Toolbar mToolbar;
    private boolean mIsCancelling;
    private int mNewNotePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        initializeViews();
        setSupportActionBar(mToolbar);

        List<Course> courses = DataManager.getInstance().getCourses();
        ArrayAdapter<Course> adapterCourses = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courses);
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCourses.setAdapter(adapterCourses);

        //Create view model provider to store state
        ViewModelProvider viewModelProvider = new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));
        mNoteActivityViewModel = viewModelProvider.get(NoteActivityViewModel.class);

        if(mNoteActivityViewModel.isNewlyCreatedActivity() &&  null != savedInstanceState) {
            mNoteActivityViewModel.restoreState(savedInstanceState);
        }
        mNoteActivityViewModel.setNewlyCreatedActivity(false);

        readDisplayStateValues();
        if(null == savedInstanceState) {
            saveOriginalNoteValues();
        }
    }

    private void initializeViews() {
        mToolbar = findViewById(R.id.toolbar);
        mSpinnerCourses = findViewById(R.id.spinner_courses);
        mEditTextNoteText = findViewById(R.id.edit_text_note_text);
        mEditTextNoteTitle = findViewById(R.id.edit_text_note_title);
    }

    private void readDisplayStateValues() {
        DataManager dm = DataManager.getInstance();
        Intent intent = getIntent();
        int position = intent.getIntExtra(NoteListActivity.NOTE_POSITION, POSITION_NOTE_SET);
        mIsNewNote = position == POSITION_NOTE_SET;
        if(mIsNewNote) {
            // create new note
            mNewNotePosition = dm.createNewNote();
            mNote = dm.getNotes().get(mNewNotePosition);
            return;
        }

        mNote = dm.getNotes().get(position);
        int courseIndex = dm.getCourses().indexOf(mNote.getCourse());
        mSpinnerCourses.setSelection(courseIndex);
        mEditTextNoteText.setText(mNote.getText());
        mEditTextNoteTitle.setText(mNote.getTitle());
    }

    private void saveNote() {
        mNote.setCourse((Course)mSpinnerCourses.getSelectedItem());
        mNote.setTitle(mEditTextNoteTitle.getText().toString());
        mNote.setText(mEditTextNoteText.getText().toString());
    }


    private void saveOriginalNoteValues() {
        if(mIsNewNote) {
            return;
        }
        mNoteActivityViewModel.setOriginalCourseId(mNote.getCourse().getCourseId());
        mNoteActivityViewModel.setOriginalNoteTitle(mNote.getTitle());
        mNoteActivityViewModel.setOriginalNoteText(mNote.getText());
    }

    private void storePreviousNoteValues() {
        Course course = DataManager.getInstance().getCourse(mNoteActivityViewModel.getOriginalCourseId());
        mNote.setCourse(course);
        mNote.setTitle(mNoteActivityViewModel.getOriginalNoteTitle());
        mNote.setText(mNoteActivityViewModel.getOriginalNoteText());
    }


    /**
     * Create and start implicit intent to send course as an email
     */
    private void sendEmail() {
        Course course = (Course) mSpinnerCourses.getSelectedItem();
        String subject = mEditTextNoteTitle.getText().toString();
        String text = "Checkout what I learned in the Pluralsight course \"" + course.getTitle() + "\" "
                + mEditTextNoteText.getText();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
                .putExtra(Intent.EXTRA_TEXT, text)
                .setType("message/rfc822");

        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mIsCancelling) {
            if(mIsNewNote) {
                DataManager.getInstance().removeNote(mNewNotePosition);
            }else {
                storePreviousNoteValues();
            }
        }else {
            saveNote();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(null != outState) {
            mNoteActivityViewModel.saveState(outState);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send_email) {
            sendEmail();
            return true;
        }

        if (id == R.id.action_cancel) {
            mIsCancelling = true;
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}

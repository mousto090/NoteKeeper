package com.example.notekeeper;

import android.content.Intent;
import android.os.Bundle;

import com.example.notekeeper.models.DataManager;
import com.example.notekeeper.models.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    protected final String TAG = getClass().getSimpleName();
    public static final String NOTE_POSITION = "com.example.notekeeper.NOTE_INFO_POSITION";
    private FloatingActionButton mFab;
    private Toolbar mToolbar;
    private ListView mListViewNotes;
    private ArrayAdapter<Note> mAadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        initializeViews();
        setSupportActionBar(mToolbar);

        mFab.setOnClickListener(View -> {
            Intent intent = new Intent(this, NoteActivity.class);
            startActivity(intent);
        });
        initializeDisplayContent();
    }

    private void initializeViews() {
        mToolbar = findViewById(R.id.toolbar);
        mFab = findViewById(R.id.fab);
        mListViewNotes = findViewById(R.id.listview_notes);
    }

    private void initializeDisplayContent() {
        List<Note> notes = DataManager.getInstance().getNotes();
        mAadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        mListViewNotes.setAdapter(mAadapter);
        mListViewNotes.setOnItemClickListener((adapterView, view, position, id)->{
            Intent intent = new Intent(this, NoteActivity.class);
            intent.putExtra(NOTE_POSITION, position);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //update listview when new note is added
        mAadapter.notifyDataSetChanged();
    }
}

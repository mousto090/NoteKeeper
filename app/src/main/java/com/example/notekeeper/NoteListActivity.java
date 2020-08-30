package com.example.notekeeper;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notekeeper.adapters.NoteRecyclerAdapter;
import com.example.notekeeper.models.DataManager;
import com.example.notekeeper.models.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    protected final String TAG = getClass().getSimpleName();
    public static final String NOTE_POSITION = "com.example.notekeeper.NOTE_INFO_POSITION";
    private FloatingActionButton mFab;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private NoteRecyclerAdapter mNoteRecyclerAdapter;

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
        mRecyclerView = findViewById(R.id.list_notes);
    }

    private void initializeDisplayContent() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        List<Note> notes = DataManager.getInstance().getNotes();
        mNoteRecyclerAdapter = new NoteRecyclerAdapter(this, notes);
        mRecyclerView.setAdapter(mNoteRecyclerAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //update listview when new note is added
        mNoteRecyclerAdapter.notifyDataSetChanged();
    }
}

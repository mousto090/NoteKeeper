package com.example.notekeeper.ui.note;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notekeeper.R;
import com.example.notekeeper.adapters.NoteRecyclerAdapter;
import com.example.notekeeper.models.DataManager;

public class NoteFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();
    private NoteViewModel mNoteViewModel;
    private RecyclerView mRecyclerView;
    private View mRoot;
    private NoteRecyclerAdapter mNoteRecyclerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_note, container, false);
        initializeViews();
//        mHomeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        mNoteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
//        final TextView textView = root.findViewById(R.id.text_home);
//        mHomeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        initializeDisplayContent();
        return mRoot;
    }

    @Override
    public void onResume() {
        super.onResume();
        mNoteRecyclerAdapter.notifyDataSetChanged();
    }

    private void initializeViews() {
        mRecyclerView = mRoot.findViewById(R.id.list_notes);
    }

    private void initializeDisplayContent() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mNoteRecyclerAdapter = new NoteRecyclerAdapter(getContext(), DataManager.getInstance().getNotes());
        mRecyclerView.setAdapter(mNoteRecyclerAdapter);
    }
}
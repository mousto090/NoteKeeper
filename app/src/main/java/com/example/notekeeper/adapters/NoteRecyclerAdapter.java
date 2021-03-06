package com.example.notekeeper.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notekeeper.NoteActivity;
import com.example.notekeeper.NoteListActivity;
import com.example.notekeeper.R;
import com.example.notekeeper.models.Note;

import java.util.List;


public class NoteRecyclerAdapter extends RecyclerView.Adapter<NoteRecyclerAdapter.ViewHolder> {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final List<Note> mNotes;

    public NoteRecyclerAdapter(Context context, List<Note> notes) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        mNotes = notes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_note_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = mNotes.get(position);
        holder.mTextViewCourse.setText(note.getCourse().getTitle());
        holder.mTextViewTitle.setText(note.getTitle());
        holder.mCurrentPosition = position;
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView mTextViewCourse;
        public final TextView mTextViewTitle;
        public int mCurrentPosition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewCourse = itemView.findViewById(R.id.text_course);
            mTextViewTitle = itemView.findViewById(R.id.text_title);
            itemView.setOnClickListener((view) -> {
                Intent intent = new Intent(mContext, NoteActivity.class);
                intent.putExtra(NoteListActivity.NOTE_POSITION, mCurrentPosition);
                mContext.startActivity(intent);
            });
        }
    }
}

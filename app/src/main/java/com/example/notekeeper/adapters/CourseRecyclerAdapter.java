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
import com.example.notekeeper.models.Course;
import com.example.notekeeper.models.Note;

import java.util.List;


public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter.ViewHolder> {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final List<Course> mCourses;

    public CourseRecyclerAdapter(Context context, List<Course> courses) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        mCourses = courses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_course_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Course course = mCourses.get(position);
        holder.mTextViewCourse.setText(course.getTitle());
        holder.mCurrentPosition = position;
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView mTextViewCourse;
        public int mCurrentPosition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewCourse = itemView.findViewById(R.id.text_course);
//            itemView.setOnClickListener((view) -> {
//                Intent intent = new Intent(mContext, NoteActivity.class);
//                intent.putExtra(NoteListActivity.NOTE_POSITION, mCurrentPosition);
//                mContext.startActivity(intent);
//            });
        }
    }
}

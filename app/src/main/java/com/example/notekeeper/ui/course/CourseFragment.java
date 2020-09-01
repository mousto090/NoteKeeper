package com.example.notekeeper.ui.course;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notekeeper.R;
import com.example.notekeeper.adapters.CourseRecyclerAdapter;
import com.example.notekeeper.models.DataManager;

public class CourseFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();
    private CourseViewModel mCourseViewModel;
    private RecyclerView mRecyclerView;
    private View mRoot;
    private CourseRecyclerAdapter mCourseRecyclerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        mRoot = inflater.inflate(R.layout.fragment_course, container, false);
        initializeViews();
//        final TextView textView = root.findViewById(R.id.text_gallery);
//        mGalleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        Log.d(TAG, "onCreateView: ");
        initializeDisplayContent();
        return mRoot;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCourseRecyclerAdapter.notifyDataSetChanged();
    }

    private void initializeViews() {
        mRecyclerView = mRoot.findViewById(R.id.list_courses);
    }

    private void initializeDisplayContent() {
        //mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.course_grid_span)));
        mCourseRecyclerAdapter = new CourseRecyclerAdapter(getContext(), DataManager.getInstance().getCourses());
        mRecyclerView.setAdapter(mCourseRecyclerAdapter);
    }
}
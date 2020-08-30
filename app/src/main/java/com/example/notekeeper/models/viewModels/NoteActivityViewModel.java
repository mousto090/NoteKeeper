package com.example.notekeeper.models.viewModels;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

public class NoteActivityViewModel extends ViewModel {
    private String mOriginalCourseId;
    private String mOriginalNoteTitle;
    private String mOriginalNoteText;

    private boolean mIsNewlyCreatedActivity = true;

    private static final String ORIGINAL_NOTE_COURSE_ID = "com.example.notekeeper.ORIGINAL_NOTE_COURSE_ID";
    private static final String ORIGINAL_NOTE_TITLE = "com.example.notekeeper.ORIGINAL_NOTE_TITLE";
    private static final String ORIGINAL_NOTE_TEXT = "com.example.notekeeper.ORIGINAL_NOTE_TEXT";

    public String getOriginalCourseId() {
        return mOriginalCourseId;
    }

    public void setOriginalCourseId(String mOriginalCourseId) {
        this.mOriginalCourseId = mOriginalCourseId;
    }

    public String getOriginalNoteTitle() {
        return mOriginalNoteTitle;
    }

    public void setOriginalNoteTitle(String mOriginalNoteTitle) {
        this.mOriginalNoteTitle = mOriginalNoteTitle;
    }

    public String getOriginalNoteText() {
        return mOriginalNoteText;
    }

    public void setOriginalNoteText(String mOriginalNoteText) {
        this.mOriginalNoteText = mOriginalNoteText;
    }

    public boolean isNewlyCreatedActivity() {
        return mIsNewlyCreatedActivity;
    }

    public void setNewlyCreatedActivity(boolean mIsNewlyCreatedActivity) {
        this.mIsNewlyCreatedActivity = mIsNewlyCreatedActivity;
    }

    public void saveState(Bundle outState) {
        outState.putString(ORIGINAL_NOTE_COURSE_ID, mOriginalCourseId);
        outState.putString(ORIGINAL_NOTE_TITLE, mOriginalNoteTitle);
        outState.putString(ORIGINAL_NOTE_TEXT, mOriginalNoteText);
    }

    public void restoreState(Bundle inState) {
        setOriginalCourseId(inState.getString(ORIGINAL_NOTE_COURSE_ID));
        setOriginalNoteTitle(inState.getString(ORIGINAL_NOTE_TITLE));
        setOriginalNoteText(inState.getString(ORIGINAL_NOTE_TEXT));
    }

    @Override
    public String toString() {
        return "NoteActivityViewModel{" +
                "mOriginalCourseId='" + mOriginalCourseId + '\'' +
                ", mOriginalNoteTitle='" + mOriginalNoteTitle + '\'' +
                ", mOriginalNoteText='" + mOriginalNoteText + '\'' +
                ", mIsNewlyCreatedActivity=" + mIsNewlyCreatedActivity +
                '}';
    }
}

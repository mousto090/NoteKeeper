package com.example.notekeeper.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Module implements Parcelable {

    private final String mModuleId;
    private final String mTitle;
    private boolean mIsComplete = false;

    public static final Creator<Module> CREATOR = new Creator<Module>() {
        @Override
        public Module createFromParcel(Parcel source) {
            return new Module(source);
        }

        @Override
        public Module[] newArray(int size) {
            return new Module[size];
        }
    };


    public Module(String mModuleId, String mTitle) {
        this(mModuleId, mTitle, false);
    }

    public Module(String mModuleId, String mTitle, boolean mIsComplete) {
        this.mModuleId = mModuleId;
        this.mTitle = mTitle;
        this.mIsComplete = mIsComplete;
    }

    private Module(Parcel source) {
        mModuleId = source.readString();
        mTitle = source.readString();
        mIsComplete = source.readByte() == 1;
    }

    public String getModuleId() {
        return mModuleId;
    }

    public String getTitle() {
        return mTitle;
    }

    public boolean isComplete() {
        return mIsComplete;
    }

    public void setComplete(boolean mIsComplete) {
        this.mIsComplete = mIsComplete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Module that = (Module) o;

        return mModuleId.equals(that.mModuleId);
    }

    @Override
    public int hashCode() {
        return mModuleId.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mModuleId);
        dest.writeString(mTitle);
        dest.writeByte((byte)(mIsComplete ? 1 : 0));
    }
}

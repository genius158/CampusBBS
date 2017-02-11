package com.yan.campusbbs.rxbusaction;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yan on 2017/2/8.
 */

public class ActionTabShow implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public ActionTabShow() {
    }

    protected ActionTabShow(Parcel in) {
    }

    public static final Creator<ActionTabShow> CREATOR = new Creator<ActionTabShow>() {
        @Override
        public ActionTabShow createFromParcel(Parcel source) {
            return new ActionTabShow(source);
        }

        @Override
        public ActionTabShow[] newArray(int size) {
            return new ActionTabShow[size];
        }
    };
}

package com.yan.campusbbs.rxbusaction;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yan on 2017/2/8.
 */

public class ActionFloatingButton implements Parcelable {
    public boolean isFloatingShow = true;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public ActionFloatingButton() {
    }

    protected ActionFloatingButton(Parcel in) {
    }

    public static final Creator<ActionFloatingButton> CREATOR = new Creator<ActionFloatingButton>() {
        @Override
        public ActionFloatingButton createFromParcel(Parcel source) {
            return new ActionFloatingButton(source);
        }

        @Override
        public ActionFloatingButton[] newArray(int size) {
            return new ActionFloatingButton[size];
        }
    };
}

package com.yan.campusbbs.rxbusaction;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yan on 2017/2/8.
 */

public class ActionMainActivityShowComplete implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public ActionMainActivityShowComplete() {
    }

    protected ActionMainActivityShowComplete(Parcel in) {
    }

    public static final Creator<ActionMainActivityShowComplete> CREATOR = new Creator<ActionMainActivityShowComplete>() {
        @Override
        public ActionMainActivityShowComplete createFromParcel(Parcel source) {
            return new ActionMainActivityShowComplete(source);
        }

        @Override
        public ActionMainActivityShowComplete[] newArray(int size) {
            return new ActionMainActivityShowComplete[size];
        }
    };
}

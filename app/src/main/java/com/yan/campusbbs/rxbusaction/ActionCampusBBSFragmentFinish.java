package com.yan.campusbbs.rxbusaction;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yan on 2017/2/8.
 */

public class ActionCampusBBSFragmentFinish implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public ActionCampusBBSFragmentFinish() {
    }

    protected ActionCampusBBSFragmentFinish(Parcel in) {
    }

    public static final Creator<ActionCampusBBSFragmentFinish> CREATOR = new Creator<ActionCampusBBSFragmentFinish>() {
        @Override
        public ActionCampusBBSFragmentFinish createFromParcel(Parcel source) {
            return new ActionCampusBBSFragmentFinish(source);
        }

        @Override
        public ActionCampusBBSFragmentFinish[] newArray(int size) {
            return new ActionCampusBBSFragmentFinish[size];
        }
    };
}

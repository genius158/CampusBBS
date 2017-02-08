package com.yan.campusbbs.rxbusaction;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yan on 2017/2/8.
 */

public class ActionPagerToCampusBBS implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public ActionPagerToCampusBBS() {
    }

    protected ActionPagerToCampusBBS(Parcel in) {
    }

    public static final Creator<ActionPagerToCampusBBS> CREATOR = new Creator<ActionPagerToCampusBBS>() {
        @Override
        public ActionPagerToCampusBBS createFromParcel(Parcel source) {
            return new ActionPagerToCampusBBS(source);
        }

        @Override
        public ActionPagerToCampusBBS[] newArray(int size) {
            return new ActionPagerToCampusBBS[size];
        }
    };
}

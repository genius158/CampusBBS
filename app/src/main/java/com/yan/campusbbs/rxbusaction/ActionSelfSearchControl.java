package com.yan.campusbbs.rxbusaction;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yan on 2017/2/8.
 */

public class ActionSelfSearchControl implements Parcelable {
    public boolean isShow;

    public ActionSelfSearchControl(boolean isShow) {
        this.isShow = isShow;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public ActionSelfSearchControl() {
    }

    protected ActionSelfSearchControl(Parcel in) {
    }

    public static final Creator<ActionSelfSearchControl> CREATOR = new Creator<ActionSelfSearchControl>() {
        @Override
        public ActionSelfSearchControl createFromParcel(Parcel source) {
            return new ActionSelfSearchControl(source);
        }

        @Override
        public ActionSelfSearchControl[] newArray(int size) {
            return new ActionSelfSearchControl[size];
        }
    };
}

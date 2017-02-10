package com.yan.campusbbs.rxbusaction;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yan on 2017/2/8.
 */

public class ActionImageControl implements Parcelable {
    public boolean isImgShow = true;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isImgShow ? (byte) 1 : (byte) 0);
    }

    public ActionImageControl() {
    }

    public ActionImageControl(boolean isImgShow) {
        this.isImgShow = isImgShow;
    }

    protected ActionImageControl(Parcel in) {
        this.isImgShow = in.readByte() != 0;
    }

    public static final Creator<ActionImageControl> CREATOR = new Creator<ActionImageControl>() {
        @Override
        public ActionImageControl createFromParcel(Parcel source) {
            return new ActionImageControl(source);
        }

        @Override
        public ActionImageControl[] newArray(int size) {
            return new ActionImageControl[size];
        }
    };
}

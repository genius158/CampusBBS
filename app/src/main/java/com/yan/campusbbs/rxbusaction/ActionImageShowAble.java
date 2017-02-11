package com.yan.campusbbs.rxbusaction;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yan on 2017/2/8.
 */

public class ActionImageShowAble implements Parcelable {
    public boolean isImgShow = true;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isImgShow ? (byte) 1 : (byte) 0);
    }

    public ActionImageShowAble() {
    }

    public ActionImageShowAble(boolean isImgShow) {
        this.isImgShow = isImgShow;
    }

    protected ActionImageShowAble(Parcel in) {
        this.isImgShow = in.readByte() != 0;
    }

    public static final Creator<ActionImageShowAble> CREATOR = new Creator<ActionImageShowAble>() {
        @Override
        public ActionImageShowAble createFromParcel(Parcel source) {
            return new ActionImageShowAble(source);
        }

        @Override
        public ActionImageShowAble[] newArray(int size) {
            return new ActionImageShowAble[size];
        }
    };
}

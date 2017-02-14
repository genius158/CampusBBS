package com.yan.campusbbs.rxbusaction;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yan on 2017/2/8.
 */

public class ActionPagerTabClose implements Parcelable {


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public ActionPagerTabClose() {
    }

    protected ActionPagerTabClose(Parcel in) {
    }

    public static final Creator<ActionPagerTabClose> CREATOR = new Creator<ActionPagerTabClose>() {
        @Override
        public ActionPagerTabClose createFromParcel(Parcel source) {
            return new ActionPagerTabClose(source);
        }

        @Override
        public ActionPagerTabClose[] newArray(int size) {
            return new ActionPagerTabClose[size];
        }
    };
}

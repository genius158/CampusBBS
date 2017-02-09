package com.yan.campusbbs.rxbusaction;

import android.os.Parcel;
import android.os.Parcelable;

import com.yan.campusbbs.R;

/**
 * Created by yan on 2017/2/8.
 */

public class ActionChangeSkin implements Parcelable {
    public static final int[] colorPrimaryId = new int[]{
            R.color.colorPrimary,
            R.color.colorPrimary2
    };

    public static final int[] colorPrimaryDarkId = new int[]{
            R.color.colorPrimaryDark,
            R.color.colorPrimaryDark2
    };
    public static final int[] colorAccentId = new int[]{
            R.color.colorAccent,
            R.color.colorAccent2
    };

    private int skinIndex = 0;

    public ActionChangeSkin(int index) {
        skinIndex = index;
    }

    public int getColorPrimaryId() {
        return colorPrimaryId[skinIndex];
    }

    public int getColorPrimaryDarkId() {
        return colorPrimaryDarkId[skinIndex];
    }

    public int getColorAccentId() {
        return colorAccentId[skinIndex];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.skinIndex);
    }

    protected ActionChangeSkin(Parcel in) {
        this.skinIndex = in.readInt();
    }

    public static final Parcelable.Creator<ActionChangeSkin> CREATOR = new Parcelable.Creator<ActionChangeSkin>() {
        @Override
        public ActionChangeSkin createFromParcel(Parcel source) {
            return new ActionChangeSkin(source);
        }

        @Override
        public ActionChangeSkin[] newArray(int size) {
            return new ActionChangeSkin[size];
        }
    };
}

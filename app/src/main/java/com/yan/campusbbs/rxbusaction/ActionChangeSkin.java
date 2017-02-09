package com.yan.campusbbs.rxbusaction;

import android.os.Parcel;
import android.os.Parcelable;

import com.yan.campusbbs.config.AppSkinConfig;

/**
 * Created by yan on 2017/2/8.
 */

public class ActionChangeSkin implements Parcelable {


    private int skinIndex = 0;

    public ActionChangeSkin(int index) {
        skinIndex = index;
    }

    public int getColorPrimaryId() {
        return AppSkinConfig.COLOR_PRIMARY_ID[skinIndex];
    }

    public int getColorPrimaryDarkId() {
        return AppSkinConfig.COLOR_PRIMARY_DARK_ID[skinIndex];
    }

    public int getColorAccentId() {
        return AppSkinConfig.COLOR_ACCENT_ID[skinIndex];
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

package com.yan.campusbbs.module.campusbbs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.yan.campusbbs.module.AppBarBehavior;

import java.util.List;

/**
 * Created by yan on 2017/2/9.
 */

public class CampusAppBarBehavior extends AppBarBehavior {

    public CampusAppBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setViewList(List<View> followViews) {
        appBarHelper.setViewList(followViews);
    }

}

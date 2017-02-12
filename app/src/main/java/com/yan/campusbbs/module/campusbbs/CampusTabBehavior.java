package com.yan.campusbbs.module.campusbbs;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.yan.campusbbs.module.AppBarBehavior;
import com.yan.campusbbs.module.AppBarHelper;

import java.util.List;

/**
 * Created by yan on 2017/2/8.
 */

public class CampusTabBehavior extends AppBarBehavior {
    private Context context;

    public CampusTabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

    }

    public void addAppBar(View appBar) {
        appBarHelper.addBar(context, appBar);
    }
}
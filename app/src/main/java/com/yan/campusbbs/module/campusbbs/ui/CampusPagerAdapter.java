package com.yan.campusbbs.module.campusbbs.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.yan.campusbbs.module.AppBarBehavior;
import com.yan.campusbbs.module.CommonPagerAdapter;
import com.yan.campusbbs.module.campusbbs.ui.common.CampusAppHelperAdd;
import com.yan.campusbbs.module.campusbbs.ui.common.CampusTabPagerFragment;

import java.util.List;

/**
 * Created by yan on 2017/2/8.
 */

public class CampusPagerAdapter extends CommonPagerAdapter implements CampusAppHelperAdd {
    private AppBarBehavior tabBehavior;

    public CampusPagerAdapter(FragmentManager fm, AppBarBehavior tabBehavior, List<Fragment> fragmentList, String[] titles) {
        super(fm, fragmentList, titles);
        this.tabBehavior = tabBehavior;
        initAppHelperView(fm, fragmentList);
    }

    private void initAppHelperView(FragmentManager fm, List<Fragment> fragmentList) {
        for (Fragment fragment : fragmentList) {
            if (fragment instanceof CampusTabPagerFragment) {
                ((CampusTabPagerFragment) fragment).setCampusAppHelperAdd(this);
            }
        }
        List<Fragment> tempFragments = fm.getFragments();
        if (tempFragments != null) {
            for (Fragment fragment : tempFragments) {
                if (fragment instanceof CampusTabPagerFragment) {
                    ((CampusTabPagerFragment) fragment).setCampusAppHelperAdd(this);
                }
            }
        }
    }


    @Override
    public void appHelperAdd(View appBar) {
        tabBehavior.addAppBar(appBar);
    }
}

package com.yan.campusbbs.module.campusbbs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yan.campusbbs.module.selfcenter.SelfCenterFragment;

/**
 * Created by Administrator on 2017/2/8.
 */
public class CampusBBSAdapter extends FragmentPagerAdapter {
    private String[] titles;

    public CampusBBSAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return SelfCenterFragment.newInstance();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position % titles.length];
    }

    @Override
    public int getCount() {
        return titles.length;
    }
}

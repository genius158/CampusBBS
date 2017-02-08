package com.yan.campusbbs.module;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by yan on 2017/2/8.
 */

public class CommonPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private String[] titles;

    public CommonPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    public CommonPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] titles) {
        this(fm, fragmentList);
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position % fragmentList.size());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (titles != null) ? titles[position % titles.length] : null;
    }

    @Override
    public int getCount() {
        return (titles != null) ? titles.length : fragmentList.size();
    }
}

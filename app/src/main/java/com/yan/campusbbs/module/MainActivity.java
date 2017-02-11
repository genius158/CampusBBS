package com.yan.campusbbs.module;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseActivity;
import com.yan.campusbbs.module.campusbbs.CampusBBSFragment;
import com.yan.campusbbs.module.filemanager.FileManagerFragment;
import com.yan.campusbbs.module.filemanager.FileManagerPresenter;
import com.yan.campusbbs.module.filemanager.FileManagerPresenterModule;
import com.yan.campusbbs.module.selfcenter.SelfCenterFragment;
import com.yan.campusbbs.module.selfcenter.SelfCenterPresenter;
import com.yan.campusbbs.module.selfcenter.SelfCenterPresenterModule;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.rxbusaction.ActionMainActivityShowComplete;
import com.yan.campusbbs.rxbusaction.ActionPagerToCampusBBS;
import com.yan.campusbbs.setting.ImageControl;
import com.yan.campusbbs.setting.SettingHelper;
import com.yan.campusbbs.setting.SettingModule;
import com.yan.campusbbs.util.SPUtils;
import com.yan.campusbbs.util.sort.SortUtils;
import com.yan.campusbbs.util.RxBus;
import com.yan.campusbbs.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @Inject
    SelfCenterPresenter selfCenterPresenter;

    @Inject
    FileManagerPresenter fileManagerPresenter;

    @Inject
    SettingHelper changeSkinHelper;
    @Inject
    SPUtils spUtils;
    @Inject
    ImageControl imageControl;

    @Inject
    RxBus rxBus;

    @Inject
    ToastUtils toastUtils;

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    List<Fragment> fragments;
    CommonPagerAdapter commonPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragment();

        imageControl.frescoInit();

        initNavigationBar();

        settingInit();

        rxBus.post(new ActionMainActivityShowComplete());
    }

    @Override
    protected SPUtils sPUtils() {
        return spUtils;
    }

    private void initNavigationBar() {
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        BadgeItem numberBadgeItem = new BadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.crFF0000)
                .setText("5");

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, getString(R.string.self_center))
                        .setBadgeItem(numberBadgeItem))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, getString(R.string.campus_bbs)))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, getString(R.string.file_center)))
                .setActiveColor(R.color.colorPrimary)
                .setInActiveColor(R.color.crABABAB)
                .setFirstSelectedPosition(0)
                .initialise();

        bottomNavigationBar.setAnimationDuration(100);
        bottomNavigationBar.setTabSelectedListener(getOnTabSelectedListener());
    }

    private void initFragment() {
        if (getSupportFragmentManager().getFragments() == null) {
            fragments = new ArrayList<>();
            fragments.add(SelfCenterFragment.newInstance());
            fragments.add(CampusBBSFragment.newInstance());
            fragments.add(FileManagerFragment.newInstance());
        } else {
            fragments = getSupportFragmentManager().getFragments();
            if (fragments.size() < 3) {
                resetFragments();
            }
        }
        SortUtils.sort(fragments);

        DaggerModuleComponent.builder().applicationComponent(
                ((ApplicationCampusBBS) getApplication()).getApplicationComponent())
                .selfCenterPresenterModule(new SelfCenterPresenterModule((SelfCenterFragment) fragments.get(0)))
                .fileManagerPresenterModule(new FileManagerPresenterModule((FileManagerFragment) fragments.get(2)))
                .settingModule(new SettingModule(this, compositeDisposable))
                .build().inject(this);
        commonPagerAdapter = new CommonPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(commonPagerAdapter);
        viewPager.clearOnPageChangeListeners();
        viewPager.addOnPageChangeListener(getPageChangeListener());
    }

    private BottomNavigationBar.OnTabSelectedListener getOnTabSelectedListener() {
        return new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                bottomNavigationBar.show();
                viewPager.setCurrentItem(position);
                switch (position) {
                    case 0:
                        bottomNavigationBar.setAutoHideEnabled(true);
                        break;
                    case 1:
                        bottomNavigationBar.setAutoHideEnabled(false);
                        rxBus.post(new ActionPagerToCampusBBS());
                        break;
                    case 2:
                        bottomNavigationBar.setAutoHideEnabled(false);
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        };
    }

    private ViewPager.OnPageChangeListener getPageChangeListener() {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationBar.selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        super.changeSkin(actionChangeSkin);

        bottomNavigationBar.clearAll();
        BadgeItem numberBadgeItem = new BadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.crFF0000)
                .setText("5");

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, getString(R.string.self_center))
                        .setBadgeItem(numberBadgeItem))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, getString(R.string.campus_bbs)))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, getString(R.string.file_center)))
                .setActiveColor(actionChangeSkin.getColorPrimaryId())
                .setInActiveColor(R.color.crABABAB)
                .setFirstSelectedPosition(viewPager.getCurrentItem())
                .initialise();
    }

    private void resetFragments() {
        boolean[] fragmentNullIndex = new boolean[3];
        for (Fragment fragment : fragments) {
            if (fragment instanceof SelfCenterFragment) {
                fragmentNullIndex[0] = true;
            } else if (fragment instanceof CampusBBSFragment) {
                fragmentNullIndex[1] = true;
            } else if (fragment instanceof FileManagerFragment) {
                fragmentNullIndex[2] = true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (!fragmentNullIndex[i]) {
                switch (i) {
                    case 0:
                        fragments.add(SelfCenterFragment.newInstance());
                        break;
                    case 1:
                        fragments.add(CampusBBSFragment.newInstance());
                        break;
                    case 2:
                        fragments.add(FileManagerFragment.newInstance());
                        break;
                }
            }
        }
        if (viewPager.getAdapter() != null) {
            viewPager.getAdapter().notifyDataSetChanged();
        }
    }

    long lastBackPressedTime;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastBackPressedTime < 1000) {
            super.onBackPressed();
        } else {
            lastBackPressedTime = System.currentTimeMillis();
            toastUtils.showShort(getString(R.string.more_pressed_exit));
        }
    }
}

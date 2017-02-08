package com.yan.campusbbs.module;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.module.campusbbs.CampusBBSFragment;
import com.yan.campusbbs.module.campusbbs.CampusBBSPresenter;
import com.yan.campusbbs.module.campusbbs.CampusBBSPresenterModule;
import com.yan.campusbbs.module.filemanager.FileManagerFragment;
import com.yan.campusbbs.module.filemanager.FileManagerPresenter;
import com.yan.campusbbs.module.filemanager.FileManagerPresenterModule;
import com.yan.campusbbs.module.selfcenter.SelfCenterFragment;
import com.yan.campusbbs.module.selfcenter.SelfCenterPresenter;
import com.yan.campusbbs.module.selfcenter.SelfCenterPresenterModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Inject
    SelfCenterPresenter selfCenterPresenter;

    @Inject
    CampusBBSPresenter campusBBSPresenter;

    @Inject
    FileManagerPresenter fileManagerPresenter;

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initFragment();
        initNavigationBar();

    }

    private void initNavigationBar() {
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        BadgeItem numberBadgeItem = new BadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.crFF0000)
                .setText("5");

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "个人中心")
                        .setBadgeItem(numberBadgeItem))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "学园论坛"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "资源中心"))
                .setActiveColor(R.color.colorPrimary)
                .setInActiveColor(R.color.crABABAB)
                .setFirstSelectedPosition(0)
                .initialise();

        bottomNavigationBar.setAnimationDuration(100);
        bottomNavigationBar.setTabSelectedListener(onTabSelectedListener);
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(SelfCenterFragment.newInstance());
        fragments.add(CampusBBSFragment.newInstance());
        fragments.add(FileManagerFragment.newInstance());

        DaggerModuleComponent.builder().applicationComponent(
                ((ApplicationCampusBBS) getApplication()).getApplicationComponent())
                .selfCenterPresenterModule(new SelfCenterPresenterModule((SelfCenterFragment) fragments.get(0)))
                .campusBBSPresenterModule(new CampusBBSPresenterModule((CampusBBSFragment) fragments.get(1)))
                .fileManagerPresenterModule(new FileManagerPresenterModule((FileManagerFragment) fragments.get(2)))
                .build().inject(this);

        viewPager.setAdapter(new CommonPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.addOnPageChangeListener(pageChangeListener);

    }

    private BottomNavigationBar.OnTabSelectedListener onTabSelectedListener =
            new BottomNavigationBar.OnTabSelectedListener() {
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

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
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

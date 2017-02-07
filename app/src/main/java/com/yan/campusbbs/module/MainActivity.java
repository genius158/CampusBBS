package com.yan.campusbbs.module;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.module.selfcenter.DaggerSelfCenterComponent;
import com.yan.campusbbs.module.selfcenter.SelfCenterFragment;
import com.yan.campusbbs.module.selfcenter.SelfCenterPresenter;
import com.yan.campusbbs.module.selfcenter.SelfCenterPresenterModule;
import com.yan.campusbbs.util.ActivityUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.content_layout)
    FrameLayout contentLayout;

    @Inject
    SelfCenterPresenter selfCenterPresenter;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        SelfCenterFragment selfCenterFragment = (SelfCenterFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content_layout);
        if (selfCenterFragment == null) {
            selfCenterFragment = SelfCenterFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    selfCenterFragment, R.id.content_layout);
        }

        DaggerSelfCenterComponent.builder()
                .selfCenterPresenterModule(new SelfCenterPresenterModule(selfCenterFragment))
                .applicationComponent(((ApplicationCampusBBS) getApplication()).getApplicationComponent())
                .build()
                .inject(this);

        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        BadgeItem numberBadgeItem = new BadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.crFF0000)
                .setText("5");

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Home")
                        .setBadgeItem(numberBadgeItem))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Books"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Music"))
                .setActiveColor(R.color.cr8B8B8B)
                .setInActiveColor(R.color.cr999999)
                .setFirstSelectedPosition(0)
                .initialise();

        bottomNavigationBar.setAnimationDuration(100);
        bottomNavigationBar.setTabSelectedListener(onTabSelectedListener);

    }

    BottomNavigationBar.OnTabSelectedListener onTabSelectedListener = new BottomNavigationBar.OnTabSelectedListener() {
        @Override
        public void onTabSelected(int position) {

        }

        @Override
        public void onTabUnselected(int position) {

        }

        @Override
        public void onTabReselected(int position) {

        }
    };
}

package com.yan.campusbbs.module;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.content_layout)
    FrameLayout contentLayout;

    @Inject
    SelfCenterPresenter selfCenterPresenter;

    @Inject
    CampusBBSPresenter campusBBSPresenter;

    @Inject
    FileManagerPresenter fileManagerPresenter;

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

    Fragment[] fragments;

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
                .setActiveColor(R.color.cr8B8B8B)
                .setInActiveColor(R.color.cr999999)
                .setFirstSelectedPosition(0)
                .initialise();

        bottomNavigationBar.setAnimationDuration(100);
        bottomNavigationBar.setTabSelectedListener(onTabSelectedListener);
    }

    private void initFragment() {
        fragments = new Fragment[3];
        fragments[0] = getSupportFragmentManager().findFragmentById(R.id.content_layout);
        fragments[1] = getSupportFragmentManager().findFragmentById(R.id.content_layout);
        fragments[2] = getSupportFragmentManager().findFragmentById(R.id.content_layout);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (fragments[0] == null) {
            fragments[0] = SelfCenterFragment.newInstance();
            fragmentTransaction.add(R.id.content_layout, fragments[0]).show(fragments[0]);
        }

        if (fragments[1] == null) {
            fragments[1] = CampusBBSFragment.newInstance();
            fragmentTransaction.add(R.id.content_layout, fragments[1]).hide(fragments[1]);
        }
        if (fragments[2] == null) {
            fragments[2] = FileManagerFragment.newInstance();
            fragmentTransaction.add(R.id.content_layout, fragments[2]).hide(fragments[2]);
        }
        fragmentTransaction.commit();

        DaggerModuleComponent.builder().applicationComponent(
                ((ApplicationCampusBBS) getApplication()).getApplicationComponent())
                .selfCenterPresenterModule(new SelfCenterPresenterModule((SelfCenterFragment) fragments[0]))
                .campusBBSPresenterModule(new CampusBBSPresenterModule((CampusBBSFragment) fragments[1]))
                .fileManagerPresenterModule(new FileManagerPresenterModule((FileManagerFragment) fragments[2]))
                .build().inject(this);

    }

    private BottomNavigationBar.OnTabSelectedListener onTabSelectedListener =
            new BottomNavigationBar.OnTabSelectedListener() {
                @Override
                public void onTabSelected(int position) {
                    bottomNavigationBar.show();
                    switch (position) {
                        case 0:
                            getSupportFragmentManager().beginTransaction().show(fragments[0])
                                    .hide(fragments[1])
                                    .hide(fragments[2])
                                    .commit();

                            bottomNavigationBar.setAutoHideEnabled(true);
                            break;
                        case 1:
                            getSupportFragmentManager().beginTransaction().show(fragments[1])
                                    .hide(fragments[0])
                                    .hide(fragments[2])
                                    .commit();

                            bottomNavigationBar.setAutoHideEnabled(false);
                            break;
                        case 2:
                            getSupportFragmentManager().beginTransaction().show(fragments[2])
                                    .hide(fragments[0])
                                    .hide(fragments[1])
                                    .commit();

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

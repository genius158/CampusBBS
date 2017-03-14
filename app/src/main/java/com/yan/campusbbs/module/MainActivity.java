package com.yan.campusbbs.module;

import android.Manifest;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.gordonwong.materialsheetfab.DimOverlayFrameLayout;
import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseActivity;
import com.yan.campusbbs.module.campusbbs.ui.CampusBBSFragment;
import com.yan.campusbbs.module.filemanager.FileManagerFragment;
import com.yan.campusbbs.module.search.SearchActivity;
import com.yan.campusbbs.module.selfcenter.SelfCenterFragment;
import com.yan.campusbbs.module.setting.ImageControl;
import com.yan.campusbbs.module.setting.SettingHelper;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.rxbusaction.ActionFloatingButton;
import com.yan.campusbbs.rxbusaction.ActionMainActivityShowComplete;
import com.yan.campusbbs.rxbusaction.ActionPagerTabClose;
import com.yan.campusbbs.rxbusaction.ActionTabShow;
import com.yan.campusbbs.util.AnimationUtils;
import com.yan.campusbbs.util.RxBus;
import com.yan.campusbbs.util.SPUtils;
import com.yan.campusbbs.util.ToastUtils;
import com.yan.campusbbs.widget.FloatingButton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends BaseActivity {
    private static final String BUNDLE_VIEW_PAGER_PAGE = "viewPagerPage";
    private static final String BUNDLE_FAB_IS_SHOW = "fabShow";

    @Inject
    SettingHelper changeSkinHelper;
    @Inject
    SPUtils spUtils;
    @Inject
    ImageControl imageControl;
    @Inject
    AnimationUtils animationUtils;
    @Inject
    RxBus rxBus;
    @Inject
    ToastUtils toastUtils;

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.fab)
    FloatingButton fab;
    @BindView(R.id.overlay)
    DimOverlayFrameLayout overlay;
    @BindView(R.id.fab_sheet)
    CardView fabSheet;
    @BindView(R.id.floating_button_container)
    LinearLayout floatingButtonContainer;
    @BindView(R.id.btn_search)
    ImageView btnSearch;

    List<Fragment> fragments;
    @BindView(R.id.btn_search_layout)
    FrameLayout btnSearchLayout;

    private MaterialSheetFab materialSheetFab;

    private boolean isReLoad = false;

    float[] fabShowAnimationValue = new float[1];
    private ValueAnimator fabShowAnimation;
    private ValueAnimator fabHideAnimation;

    private boolean isFabShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();

        reLoadData(savedInstanceState);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        rxBus.post(new ActionMainActivityShowComplete());

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
//                        Toast.makeText(getBaseContext(), "true", Toast.LENGTH_SHORT).show();
                    } else {
//                        Toast.makeText(getBaseContext(), "false", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_VIEW_PAGER_PAGE, viewPager.getCurrentItem());
        outState.putBoolean(BUNDLE_FAB_IS_SHOW, isFabShow);
    }

    private void reLoadData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.getInt(BUNDLE_VIEW_PAGER_PAGE, 0) > 0) {
                isReLoad = true;
            }

            isFabShow = savedInstanceState.getBoolean(BUNDLE_FAB_IS_SHOW, false);

            if (isFabShow) {
                fab.postDelayed(() -> {
                    fab.setVisibility(View.VISIBLE);
                    fab.setAlpha(1f);
                }, 150);
            }
        }
    }

    private void init() {
        initFragment();
        imageControl.frescoInit();

        initNavigationBar();
        rxActionInit();
    }

    private void rxActionInit() {
        addDisposable(rxBus.getEvent(ActionFloatingButton.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(actionFloating -> {
                    if (actionFloating.isScrollDown) {
                        if (isFabShow) return;
                        isFabShow = true;
                        fab.setVisibility(View.VISIBLE);
                        if (fab.getAlpha() == 0f) {
                            fab.setAlpha(1f);
                        }
                        if (fabHideAnimation != null && fabHideAnimation.isRunning()) {
                            fabHideAnimation.cancel();
                        }
                        if (fabShowAnimation == null) {
                            fabShowAnimation = getFabShowAnimation();
                        } else {
                            fabShowAnimation.setFloatValues(fabShowAnimationValue[0], 1);
                        }
                        fabShowAnimation.start();

                    } else {
                        if (!isFabShow) return;
                        isFabShow = false;

                        if (fabShowAnimation != null && fabShowAnimation.isRunning()) {
                            fabShowAnimation.cancel();
                        }
                        if (fabHideAnimation == null) {
                            fabHideAnimation = getFabHideAnimation();
                        } else {
                            fabHideAnimation.setFloatValues(fabShowAnimationValue[0]
                                    , 0);
                        }
                        fabHideAnimation.start();
                    }
                }));
    }

    private ValueAnimator getFabShowAnimation() {
        return animationUtils.createAnimation(
                fab, AnimationUtils.AnimationType.SCALE, 200,
                new LinearInterpolator()
                , null
                , fabShowAnimationValue
                , 0f
                , 1f
        );
    }

    private ValueAnimator getFabHideAnimation() {
        return animationUtils.createAnimation(
                fab, AnimationUtils.AnimationType.SCALE, 200,
                new LinearInterpolator()
                , new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (fabShowAnimationValue[0] == 0) {
                            fab.setVisibility(View.GONE);
                        }
                    }
                }
                , fabShowAnimationValue
                , 1f
                , 0f
        );
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
        fragments = new ArrayList<>();
        SelfCenterFragment selfCenterFragment = SelfCenterFragment.newInstance();
        fragments.add(selfCenterFragment);
        CampusBBSFragment campusBBSFragment = CampusBBSFragment.newInstance();
        campusBBSFragment.setMainSearch(btnSearchLayout);
        fragments.add(campusBBSFragment);
        fragments.add(FileManagerFragment.newInstance());
        if (getSupportFragmentManager().getFragments() != null) {
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                if (fragment instanceof CampusBBSFragment) {
                    ((CampusBBSFragment) fragment).setMainSearch(btnSearchLayout);
                }
            }
        }
        DaggerModuleComponent.builder().applicationComponent(
                ((ApplicationCampusBBS) getApplication()).getApplicationComponent())
                .settingModule(new SettingModule(this, compositeDisposable))
                .build().inject(this);

        CommonPagerAdapter commonPagerAdapter =
                new CommonPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(commonPagerAdapter);
        commonPagerAdapter.notifyDataSetChanged();
        viewPager.clearOnPageChangeListeners();
        viewPager.addOnPageChangeListener(getPageChangeListener());
    }

    private void setupFab(ActionChangeSkin actionChangeSkin) {
        int sheetColor = ContextCompat.getColor(this, R.color.crFEFEFE);
        int fabColor = ContextCompat.getColor(this, actionChangeSkin.getColorPrimaryId());

        materialSheetFab = new MaterialSheetFab(fab, fabSheet, overlay, sheetColor, fabColor);
        materialSheetFab.setEventListener(new MaterialSheetFabEventListener() {
            @Override
            public void onShowSheet() {

            }

            @Override
            public void onHideSheet() {
            }
        });
        fab.setAlpha(0f);
        fab.postDelayed(() -> {
            fab.setVisibility(View.GONE);
        }, 100);
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

    private ActionFloatingButton actionFloating;

    private ViewPager.OnPageChangeListener getPageChangeListener() {
        return new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset < 0.2) {
                    btnSearch.setScaleX(1 - positionOffset);
                    btnSearch.setScaleY(1 - positionOffset);
                    if (position == 0) {
                        if (btnSearch.getAlpha() != 0f) {
                            btnSearch.setAlpha(0f);
                        }
                    } else {
                        btnSearch.setScaleX(1 - positionOffset);
                        btnSearch.setScaleY(1 - positionOffset);
                        btnSearch.setAlpha(1 - positionOffset * 5f);
                    }
                } else if (positionOffset > 0.8) {
                    btnSearch.setScaleX(positionOffset);
                    btnSearch.setScaleY(positionOffset);
                    btnSearch.setAlpha((positionOffset - 0.8f) * 5f);
                } else if (positionOffset >= 0.2 && positionOffset <= 0.8) {
                    btnSearch.setAlpha(0f);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (actionFloating == null) {
                    actionFloating = new ActionFloatingButton();
                }
                actionFloating.isScrollDown = false;

                switch (position) {
                    case 0:
                        break;
                    case 1:
                        if (!isReLoad) {
                            rxBus.post(new ActionTabShow());
                            actionFloating.isScrollDown = true;
                        }
                        break;
                    case 2:
                        break;
                }
                btnSearchLayout.setY(0);

                rxBus.post(actionFloating);
                bottomNavigationBar.selectTab(position);
                rxBus.post(new ActionPagerTabClose());
                isReLoad = false;

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        };
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        super.changeSkin(actionChangeSkin);

        fab.setBackgroundTintList(ColorStateList.valueOf(
                ContextCompat.getColor(getBaseContext(), actionChangeSkin.getColorPrimaryId())
        ));

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            fab.setBackgroundColor(
                    ContextCompat.getColor(getBaseContext(), actionChangeSkin.getColorPrimaryId())
            );
        }
        setupFab(actionChangeSkin);

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

    private long lastBackPressedTime;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastBackPressedTime < 1000) {
            super.onBackPressed();
        } else {
            lastBackPressedTime = System.currentTimeMillis();
            toastUtils.showShort(getString(R.string.more_pressed_exit));
        }
    }


    @Override
    protected SPUtils sPUtils() {
        return spUtils;
    }

    @OnClick(R.id.btn_search)
    public void onClick() {
        startActivity(new Intent(getBaseContext(), SearchActivity.class));
    }

    @OnClick({R.id.tv_learn, R.id.tv_life, R.id.tv_job, R.id.tv_other})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_learn:
                break;
            case R.id.tv_life:
                break;
            case R.id.tv_job:
                break;
            case R.id.tv_other:
                break;
        }
        toastUtils.showShort(view.getId() + "");
        materialSheetFab.hideSheet();
    }
}

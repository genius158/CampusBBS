package com.yan.campusbbs.module;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.gordonwong.materialsheetfab.DimOverlayFrameLayout;
import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener;
import com.nineoldandroids.animation.ValueAnimator;
import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseActivity;
import com.yan.campusbbs.module.campusbbs.CampusBBSFragment;
import com.yan.campusbbs.module.filemanager.FileManagerFragment;
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
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends BaseActivity {
    private static final String VIEW_PAGER_PAGE = "viewPagerPage";

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

    List<Fragment> fragments;
    private boolean isReLoad = false;
    private MaterialSheetFab materialSheetFab;


    float[] fabShowAnimationValue = new float[1];
    private ValueAnimator fabShowAnimation;
    private ValueAnimator fabHideAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        imageControl.frescoInit();
        settingInit();

        if (savedInstanceState != null) {
            if (savedInstanceState.getInt(VIEW_PAGER_PAGE, 0) > 0) {
                isReLoad = true;
            }
        }
        rxBus.post(new ActionMainActivityShowComplete());

    }

    private void init() {
        initFragment();
        initNavigationBar();
        RxActionInit();
    }

    private void RxActionInit() {
        addDisposable(rxBus.getEvent(ActionFloatingButton.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(actionFloating -> {
                    if (actionFloating.isFloatingShow) {
                        fab.setOnTouchListener((v, event) -> false);
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
                        fab.setOnTouchListener((v, event) -> true);

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
                , null
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
        fragments.add(SelfCenterFragment.newInstance());
        fragments.add(CampusBBSFragment.newInstance());
        fragments.add(FileManagerFragment.newInstance());

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
        fab.setOnTouchListener((v, event) -> true);
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

            }

            @Override
            public void onPageSelected(int position) {
                if (actionFloating == null) {
                    actionFloating = new ActionFloatingButton();
                    actionFloating.isFloatingShow = false;

                }
                switch (position) {
                    case 0:
                        rxBus.post(actionFloating);
                        break;
                    case 1:
                        if (!isReLoad) {
                            rxBus.post(new ActionTabShow());
                        }
                        break;
                    case 2:
                        rxBus.post(actionFloating);
                        break;
                }

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

        ViewCompat.setBackgroundTintList(fab, ColorStateList.valueOf(
                ContextCompat.getColor(getBaseContext(), actionChangeSkin.getColorPrimaryId())
        ));
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(VIEW_PAGER_PAGE, viewPager.getCurrentItem());
    }

    @Override
    protected SPUtils sPUtils() {
        return spUtils;
    }

}

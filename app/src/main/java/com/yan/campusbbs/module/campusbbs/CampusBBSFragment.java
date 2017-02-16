package com.yan.campusbbs.module.campusbbs;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseSettingControlFragment;
import com.yan.campusbbs.module.campusbbs.job.JobFragment;
import com.yan.campusbbs.module.campusbbs.life.LifeFragment;
import com.yan.campusbbs.module.campusbbs.other.OthersFragment;
import com.yan.campusbbs.module.campusbbs.study.StudyFragment;
import com.yan.campusbbs.module.setting.SettingHelper;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.rxbusaction.ActionPagerTabClose;
import com.yan.campusbbs.rxbusaction.ActionTabShow;
import com.yan.campusbbs.util.RxBus;
import com.yan.campusbbs.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
public class CampusBBSFragment extends BaseSettingControlFragment {
    private static final String BUNDLE_TAB_IS_SHOW = "tabSelectIsShow";
    private static final String VIEW_PAGER_PAGE = "viewPagerPage";

    private final String[] pagerTitles;

    @BindView(R.id.tabs)
    TabLayout indicator;
    @BindView(R.id.pager)
    ViewPager viewPager;

    @Inject
    RxBus rxBus;
    @Inject
    SPUtils spUtils;
    @Inject
    SettingHelper changeSkinHelper;

    @BindView(R.id.tab_campus_container)
    CardView tabContainer;

    private List<Fragment> fragments;
    private boolean isReLoad = false;

    private CampusAppBarBehavior appBarBehavior;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_campus_bbs, container, false);
        ButterKnife.bind(this, view);
        daggerInject();
        init();
        initRxBusAction();
        return view;
    }

    @Override
    protected void onLoadLazy(Bundle reLoadBundle) {
        Log.e("onLoadLazy", "CampusBBSLoadLazy:" + reLoadBundle);
    }

    @Override
    protected void onSaveArguments(Bundle bundle) {
        super.onSaveArguments(bundle);
        bundle.putBoolean(BUNDLE_TAB_IS_SHOW, appBarBehavior.isShow());
        bundle.putInt(VIEW_PAGER_PAGE, viewPager.getCurrentItem());

    }

    @Override
    protected void onReloadArguments(Bundle bundle) {
        super.onReloadArguments(bundle);
        if (!bundle.getBoolean(BUNDLE_TAB_IS_SHOW, false)) {
            appBarBehavior.setTagHide(getContext());
            tabContainer.setY(-getResources().getDimension(R.dimen.action_bar_height));
        }
        if (bundle.getInt(VIEW_PAGER_PAGE, 0) > 0) {
            isReLoad = true;
        }

    }

    public CampusBBSFragment() {
        pagerTitles = new String[4];
    }

    private void init() {
        pagerTitles[0] = getString(R.string.campus_bbs_study);
        pagerTitles[1] = getString(R.string.campus_bbs_life);
        pagerTitles[2] = getString(R.string.campus_bbs_job);
        pagerTitles[3] = getString(R.string.campus_bbs_more);

        fragments = new ArrayList<>();

        fragments.add(StudyFragment.newInstance());
        fragments.add(LifeFragment.newInstance());
        fragments.add(JobFragment.newInstance());
        fragments.add(OthersFragment.newInstance());

        CoordinatorLayout.LayoutParams lp =
                (CoordinatorLayout.LayoutParams) tabContainer.getLayoutParams();
        appBarBehavior = (CampusAppBarBehavior) lp.getBehavior();
        appBarBehavior.setAppBar(tabContainer);
        appBarBehavior.setRxBus(rxBus);

        CampusPagerAdapter adapter =
                new CampusPagerAdapter(getChildFragmentManager(), appBarBehavior, fragments, pagerTitles);

        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        viewPager.addOnPageChangeListener(onPageChangeListener);
        indicator.setupWithViewPager(viewPager);
    }

    private void daggerInject() {
        DaggerCampusBBSComponent.builder()
                .applicationComponent(((ApplicationCampusBBS) getActivity()
                        .getApplication())
                        .getApplicationComponent())
                .settingModule(new SettingModule(this, compositeDisposable))
                .build().inject(this);
    }

    public void initRxBusAction() {
        addDisposable(rxBus.getEvent(ActionTabShow.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(actionTabShow -> {
                            if (appBarBehavior != null) {
                                appBarBehavior.show();
                            }
                        }
                ));
    }

    public static CampusBBSFragment newInstance() {
        return new CampusBBSFragment();
    }

    @Override
    protected SPUtils sPUtils() {
        return spUtils;
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        super.changeSkin(actionChangeSkin);
        tabContainer.setCardBackgroundColor(
                ContextCompat.getColor(getContext(), actionChangeSkin.getColorPrimaryId())
        );
    }


    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (!isReLoad) {
                rxBus.post(new ActionTabShow());
                rxBus.post(new ActionPagerTabClose());
            }
            isReLoad = false;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}

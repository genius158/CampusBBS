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
import com.yan.campusbbs.module.CommonPagerAdapter;
import com.yan.campusbbs.module.campusbbs.job.Job;
import com.yan.campusbbs.module.campusbbs.job.JobPresenter;
import com.yan.campusbbs.module.campusbbs.job.JobPresenterModule;
import com.yan.campusbbs.module.campusbbs.life.Life;
import com.yan.campusbbs.module.campusbbs.life.LifePresenter;
import com.yan.campusbbs.module.campusbbs.life.LifePresenterModule;
import com.yan.campusbbs.module.campusbbs.other.Others;
import com.yan.campusbbs.module.campusbbs.study.Study;
import com.yan.campusbbs.module.campusbbs.study.StudyPresenter;
import com.yan.campusbbs.module.campusbbs.study.StudyPresenterModule;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.rxbusaction.ActionPagerToCampusBBS;
import com.yan.campusbbs.setting.SettingHelper;
import com.yan.campusbbs.setting.SettingModule;
import com.yan.campusbbs.util.SPUtils;
import com.yan.campusbbs.util.sort.Sort;
import com.yan.campusbbs.util.sort.SortUtils;
import com.yan.campusbbs.util.RxBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
public class CampusBBS extends BaseSettingControlFragment implements FollowViewsAdd, Sort {
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
    StudyPresenter studyPresenter;
    @Inject
    LifePresenter lifePresenter;
    @Inject
    JobPresenter jobPresenter;
    @Inject
    SettingHelper changeSkinHelper;

    @BindView(R.id.tab_campus_container)
    CardView tabContainer;

    private CampusAppBarBehavior behavior;
    private CommonPagerAdapter adapter;

    private List<Fragment> fragments;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_campus_bbs, container, false);
        ButterKnife.bind(this, view);
        init();
        initRxBusAction();
        return view;
    }

    @Override
    protected void onLoadLazy() {
        Log.e("onLoadLazy", "CampusBBSLoadLazy");
    }

    @Override
    protected void onSaveArguments(Bundle bundle) {
        super.onSaveArguments(bundle);

    }

    @Override
    protected void onReloadArguments(Bundle bundle) {
        super.onReloadArguments(bundle);

    }

    public CampusBBS() {
        pagerTitles = new String[4];
    }

    private void init() {
        pagerTitles[0] = getString(R.string.campus_bbs_study);
        pagerTitles[1] = getString(R.string.campus_bbs_life);
        pagerTitles[2] = getString(R.string.campus_bbs_job);
        pagerTitles[3] = getString(R.string.campus_bbs_more);

        if (getChildFragmentManager().getFragments() == null) {
            fragments = new ArrayList<>();
            fragments.add(Study.newInstance());
            fragments.add(Life.newInstance());
            fragments.add(Job.newInstance());
            fragments.add(Others.newInstance());
        } else {
            fragments = getChildFragmentManager().getFragments();
            if (fragments.size() < 4) {
                resetFragments();
            }
        }
        SortUtils.sort(fragments);

        daggerInject(fragments);

        ((Study) fragments.get(0)).setFollowAdd(this);
        ((Life) fragments.get(1)).setFollowAdd(this);
        ((Job) fragments.get(2)).setFollowAdd(this);

        adapter = new CommonPagerAdapter(getChildFragmentManager(), fragments, pagerTitles);
        viewPager.setAdapter(adapter);
        indicator.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(getOnPageChangeListener());

        CoordinatorLayout.LayoutParams lp =
                (CoordinatorLayout.LayoutParams) tabContainer.getLayoutParams();
        behavior = (CampusAppBarBehavior) lp.getBehavior();
    }

    private void daggerInject(List<Fragment> fragments) {
        DaggerCampusBBSComponent.builder()
                .applicationComponent(((ApplicationCampusBBS) getActivity()
                        .getApplication())
                        .getApplicationComponent())
                .studyPresenterModule(new StudyPresenterModule((Study) fragments.get(0)))
                .lifePresenterModule(new LifePresenterModule((Life) fragments.get(1)))
                .jobPresenterModule(new JobPresenterModule((Job) fragments.get(2)))
                .settingModule(new SettingModule(this, compositeDisposable))
                .build().inject(this);
    }

    public void initRxBusAction() {
        addDisposable(rxBus.getEvent(ActionPagerToCampusBBS.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pagerToCampusBBS -> {
                            behavior.show();
                        }
                ));
    }

    public static CampusBBS newInstance() {
        return new CampusBBS();
    }

    @Override
    public void addFollowView(View followView) {
        behavior.addFollowView(followView);
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

    private void resetFragments() {
        boolean[] fragmentNullIndex = new boolean[4];
        for (Fragment fragment : fragments) {
            if (fragment instanceof Study) {
                fragmentNullIndex[0] = true;
            } else if (fragment instanceof Life) {
                fragmentNullIndex[1] = true;
            } else if (fragment instanceof Job) {
                fragmentNullIndex[2] = true;
            } else if (fragment instanceof Others) {
                fragmentNullIndex[3] = true;
            }
        }
        for (int i = 0; i < 4; i++) {
            if (!fragmentNullIndex[i]) {
                switch (i) {
                    case 0:
                        fragments.add(Study.newInstance());
                        break;
                    case 1:
                        fragments.add(Life.newInstance());
                        break;
                    case 2:
                        fragments.add(Job.newInstance());
                        break;
                    case 3:
                        fragments.add(Others.newInstance());
                        break;
                }
            }
        }
        if (viewPager.getAdapter() != null) {
            viewPager.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public int getIndex() {
        return 1;
    }

    private ViewPager.OnPageChangeListener getOnPageChangeListener() {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                behavior.show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
    }
}

package com.yan.campusbbs.module.campusbbs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseFragment;
import com.yan.campusbbs.config.SharedPreferenceConfig;
import com.yan.campusbbs.module.CommonPagerAdapter;
import com.yan.campusbbs.module.campusbbs.job.JobFragment;
import com.yan.campusbbs.module.campusbbs.job.JobPresenter;
import com.yan.campusbbs.module.campusbbs.job.JobPresenterModule;
import com.yan.campusbbs.module.campusbbs.life.LifeFragment;
import com.yan.campusbbs.module.campusbbs.life.LifePresenter;
import com.yan.campusbbs.module.campusbbs.life.LifePresenterModule;
import com.yan.campusbbs.module.campusbbs.other.OthersFragment;
import com.yan.campusbbs.module.campusbbs.study.StudyFragment;
import com.yan.campusbbs.module.campusbbs.study.StudyPresenter;
import com.yan.campusbbs.module.campusbbs.study.StudyPresenterModule;
import com.yan.campusbbs.rxbusaction.ActionCampusBBSFragmentFinish;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.rxbusaction.ActionPagerToCampusBBS;
import com.yan.campusbbs.util.ChangeSkinHelper;
import com.yan.campusbbs.util.ChangeSkinModule;
import com.yan.campusbbs.util.FragmentSort;
import com.yan.campusbbs.util.FragmentSortUtils;
import com.yan.campusbbs.util.IChangeSkin;
import com.yan.campusbbs.util.RxBus;
import com.yan.campusbbs.util.SPUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static android.content.Context.MODE_PRIVATE;

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
public class CampusBBSFragment extends BaseFragment implements IFollowViewsAdd, IChangeSkin, FragmentSort {
    private final String[] CONTENT = new String[]{"学习", "生活", "工作", "更多"};

    @BindView(R.id.tabs)
    TabLayout indicator;
    @BindView(R.id.pager)
    ViewPager viewPager;

    @Inject
    RxBus rxBus;
    @Inject
    StudyPresenter studyPresenter;
    @Inject
    LifePresenter lifePresenter;
    @Inject
    JobPresenter jobPresenter;
    @Inject
    ChangeSkinHelper changeSkinHelper;

    @BindView(R.id.tab_campus_container)
    CardView tabContainer;

    private CampusAppBarBehavior behavior;

    private List<View> followViews;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
        initRxBusAction();
        skinInit();
        setRetainInstance(true);
    }

    @Override
    public void onDestroy() {
        rxBus.post(new ActionCampusBBSFragmentFinish());
        super.onDestroy();
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_campus_bbs, container, false);
    }

    protected void skinInit() {
        changeSkin(new ActionChangeSkin(
                SPUtils.getInt(getContext(), MODE_PRIVATE
                        , SharedPreferenceConfig.SHARED_PREFERENCE
                        , SharedPreferenceConfig.SKIN_INDEX, 0)
        ));
    }

    List<Fragment> fragments;

    private void init() {
        followViews = new ArrayList<>();

        if (getChildFragmentManager().getFragments() == null) {
            fragments = new ArrayList<>();
            fragments.add(StudyFragment.newInstance());
            fragments.add(LifeFragment.newInstance());
            fragments.add(JobFragment.newInstance());
            fragments.add(OthersFragment.newInstance());
        } else {
            fragments = getChildFragmentManager().getFragments();
            if (fragments.size() < 4) {
                resetFragments();
            }
        }
        FragmentSortUtils.fragmentsSort(fragments);

        daggerInject(fragments);
        ((StudyFragment) fragments.get(0)).setFollowAdd(this);
        ((LifeFragment) fragments.get(1)).setFollowAdd(this);
        ((JobFragment) fragments.get(2)).setFollowAdd(this);

        CommonPagerAdapter adapter = new CommonPagerAdapter(getChildFragmentManager(), fragments, CONTENT);
        viewPager.setAdapter(adapter);
        indicator.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(onPageChangeListener);

        CoordinatorLayout.LayoutParams lp =
                (CoordinatorLayout.LayoutParams) tabContainer.getLayoutParams();
        behavior = (CampusAppBarBehavior) lp.getBehavior();

    }


    private void daggerInject(List<Fragment> fragments) {
        DaggerCampusBBSComponent.builder()
                .applicationComponent(((ApplicationCampusBBS) getActivity()
                        .getApplication())
                        .getApplicationComponent())
                .studyPresenterModule(new StudyPresenterModule((StudyFragment) fragments.get(0)))
                .lifePresenterModule(new LifePresenterModule((LifeFragment) fragments.get(1)))
                .jobPresenterModule(new JobPresenterModule((JobFragment) fragments.get(2)))
                .changeSkinModule(new ChangeSkinModule(this, compositeDisposable))
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

    public static CampusBBSFragment newInstance() {
        return new CampusBBSFragment();
    }

    public CampusBBSFragment() {
    }

    @Override
    public void addFollowView(View followView) {
        followViews.add(followView);
        behavior.setViewList(followViews);
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        tabContainer.setCardBackgroundColor(
                ContextCompat.getColor(getContext(), actionChangeSkin.getColorPrimaryId())
        );
    }

    private void resetFragments() {
        boolean[] fragmentNullIndex = new boolean[4];
        for (Fragment fragment : fragments) {
            if (fragment instanceof StudyFragment) {
                fragmentNullIndex[0] = true;
            } else if (fragment instanceof LifeFragment) {
                fragmentNullIndex[1] = true;
            } else if (fragment instanceof JobFragment) {
                fragmentNullIndex[2] = true;
            } else if (fragment instanceof OthersFragment) {
                fragmentNullIndex[3] = true;
            }
        }
        for (int i = 0; i < 4; i++) {
            if (!fragmentNullIndex[i]) {
                switch (i) {
                    case 0:
                        fragments.add(StudyFragment.newInstance());
                        break;
                    case 1:
                        fragments.add(LifeFragment.newInstance());
                        break;
                    case 2:
                        fragments.add(JobFragment.newInstance());
                        break;
                    case 3:
                        fragments.add(OthersFragment.newInstance());
                        break;
                }
            }
        }
    }

    @Override
    public int getIndex() {
        return 1;
    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
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

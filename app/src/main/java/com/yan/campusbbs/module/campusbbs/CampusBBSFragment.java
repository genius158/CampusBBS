package com.yan.campusbbs.module.campusbbs;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseFragment;
import com.yan.campusbbs.module.CommonPagerAdapter;
import com.yan.campusbbs.module.campusbbs.study.StudyFragment;
import com.yan.campusbbs.module.campusbbs.study.StudyPresenter;
import com.yan.campusbbs.module.campusbbs.study.StudyPresenterModule;
import com.yan.campusbbs.module.selfcenter.SelfCenterFragment;
import com.yan.campusbbs.rxbusaction.ActionCampusBBSFragmentFinish;
import com.yan.campusbbs.rxbusaction.ActionPagerToCampusBBS;
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
public class CampusBBSFragment extends BaseFragment implements FollowViewsAdd {
    private final String[] CONTENT = new String[]{"学习", "生活", "工作", "更多"};

    @BindView(R.id.tabs)
    TabLayout indicator;
    @BindView(R.id.pager)
    ViewPager viewPager;

    @Inject
    RxBus rxBus;
    @Inject
    StudyPresenter studyPresenter;
    @BindView(R.id.tab_campus_container)
    CardView tabContainer;

    private View root;
    private CampusAppBarBehavior behavior;

    private List<View> followViews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_campus_bbs, container, false);
            ButterKnife.bind(this, root);
            init();
        } else {
            if (root.getParent() != null) {
                ((ViewGroup) root.getParent()).removeView(root);
            }
        }
        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onDestroy() {
        rxBus.post(new ActionCampusBBSFragmentFinish());
        super.onDestroy();
    }

    private void init() {
        followViews = new ArrayList<>();

        List<Fragment> fragments = new ArrayList<>();
        StudyFragment studyFragment = StudyFragment.newInstance();
        fragments.add(studyFragment);
        fragments.add(SelfCenterFragment.newInstance());
        fragments.add(SelfCenterFragment.newInstance());
        fragments.add(SelfCenterFragment.newInstance());

        daggerInject(fragments);

        CommonPagerAdapter adapter = new CommonPagerAdapter(getChildFragmentManager(), fragments, CONTENT);
        viewPager.setAdapter(adapter);
        indicator.setupWithViewPager(viewPager);

        CoordinatorLayout.LayoutParams lp =
                (CoordinatorLayout.LayoutParams) tabContainer.getLayoutParams();
        behavior = (CampusAppBarBehavior) lp.getBehavior();
        studyFragment.setFollowView(this);
        initRxBusAction();
    }

    private void daggerInject(List<Fragment> fragments) {
        DaggerCampusBBSComponent.builder()
                .applicationComponent(((ApplicationCampusBBS) getActivity()
                        .getApplication())
                        .getApplicationComponent())
                .studyPresenterModule(new StudyPresenterModule((StudyFragment) fragments.get(0)))
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
}

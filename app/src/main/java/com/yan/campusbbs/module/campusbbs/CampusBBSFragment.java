package com.yan.campusbbs.module.campusbbs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yan.campusbbs.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
public class CampusBBSFragment extends Fragment implements CampusBBSContract.View {
    private final String[] CONTENT = new String[]{"学习", "生活", "工作", "更多"};

    @BindView(R.id.tabs)
    TabLayout indicator;
    @BindView(R.id.pager)
    ViewPager viewPager;
    private CampusBBSContract.Presenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_campus_bbs, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentPagerAdapter adapter = new CampusBBSAdapter(getChildFragmentManager(), CONTENT);
        viewPager.setAdapter(adapter);
        indicator.setupWithViewPager(viewPager);

        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    public static CampusBBSFragment newInstance() {
        return new CampusBBSFragment();
    }

    public CampusBBSFragment() {
    }

    @Override
    public void setPresenter(@NonNull CampusBBSContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

}

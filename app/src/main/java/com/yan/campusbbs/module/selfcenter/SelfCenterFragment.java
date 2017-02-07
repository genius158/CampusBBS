package com.yan.campusbbs.module.selfcenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yan.adapter.CustomAdapter;
import com.yan.campusbbs.R;
import com.yan.campusbbs.module.AdapterHelper;
import com.yan.campusbbs.widget.refresh.PtrClassicFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrFrameLayout;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
public class SelfCenterFragment extends Fragment implements SelfCenterContract.View {
    List<String> strings;
    CustomAdapter adapter;
    @BindView(R.id.home_hot_rv)
    RecyclerView homeHotRv;
    @BindView(R.id.store_house_ptr_frame)
    PtrClassicFrameLayout storeHousePtrFrame;

    private SelfCenterContract.Presenter mPresenter;

    public static SelfCenterFragment newInstance() {
        return new SelfCenterFragment();
    }

    public SelfCenterFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull SelfCenterContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        strings = new ArrayList<>();
        strings.add("sdfasdf");
        strings.add("sdfasdf");
        strings.add("sdfasdf");
        strings.add("sdfasdf");
        strings.add("sdfasdf");
        adapter = AdapterHelper.getAdapter(getContext(), strings);
        homeHotRv.setLayoutManager(new LinearLayoutManager(getContext()));
        homeHotRv.setAdapter(adapter);
        storeHousePtrFrame.setLastUpdateTimeRelateObject(this);
        storeHousePtrFrame.setPtrHandler(defaultHandler);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_self_center, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    PtrClassicFrameLayout.PtrDefaultHandler defaultHandler = new PtrClassicFrameLayout.PtrDefaultHandler() {
        public void onRefreshBegin(PtrFrameLayout frame) {
            strings.clear();
            strings.add("sdfsdf1111111111111111111111111");
            strings.add("sdfsdf1111111111111111111111111");
            strings.add("sdfsdf1111111111111111111111111");
            strings.add("sdfsdf1111111111111111111111111");
            strings.add("sdfsdf1111111111111111111111111");
            strings.add("sdfsdf1111111111111111111111111");
            strings.add("sdfsdf1111111111111111111111111");
            adapter.notifyDataSetChanged();
            storeHousePtrFrame.refreshFinish();
        }
    };
}

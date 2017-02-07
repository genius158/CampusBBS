package com.yan.campusbbs.module;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

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
    }

}

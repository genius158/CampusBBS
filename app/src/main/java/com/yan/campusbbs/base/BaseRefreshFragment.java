package com.yan.campusbbs.base;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;

import com.yan.campusbbs.R;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;

/**
 * Created by yan on 2017/2/8.
 */

public abstract class BaseRefreshFragment extends BaseSettingControlFragment implements SwipeRefreshLayout.OnRefreshListener {

    protected abstract SwipeRefreshLayout swipeRefreshLayout();

    @Override
    public void activityCreated() {
        initRefreshLayout();
        super.activityCreated();
    }

    private void initRefreshLayout() {
        swipeRefreshLayout().setProgressViewOffset(true,
                (int) (getResources().getDimension(R.dimen.action_bar_height) * 1.5)
                , (int) getResources().getDimension(R.dimen.action_bar_height) * 3);
        swipeRefreshLayout().setProgressBackgroundColorSchemeColor(
                ContextCompat.getColor(getContext(), R.color.colorAccent)
        );
        swipeRefreshLayout().setColorSchemeColors(
                ContextCompat.getColor(getContext(), R.color.crFEFEFE)
        );
        swipeRefreshLayout().setOnRefreshListener(this);
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        super.changeSkin(actionChangeSkin);
        if (swipeRefreshLayout() != null) {
            swipeRefreshLayout()
                    .setProgressBackgroundColorSchemeColor(ContextCompat
                            .getColor(getContext(), actionChangeSkin.getColorAccentId()));
        }
    }

    @Override
    public void onRefresh() {

    }
}

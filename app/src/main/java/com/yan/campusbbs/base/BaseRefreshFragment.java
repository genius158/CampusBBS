package com.yan.campusbbs.base;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;

import com.yan.campusbbs.R;
import com.yan.campusbbs.config.SharedPreferenceConfig;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.skin.ChangeSkin;
import com.yan.campusbbs.util.SPUtils;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2017/2/8.
 */

public abstract class BaseRefreshFragment extends BaseSettingFragment implements ChangeSkin, SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;

    public void attach(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
        initRefreshLayout();
    }

    public void initRefreshLayout() {
        swipeRefreshLayout.setProgressViewOffset(true,
                (int) (getResources().getDimension(R.dimen.action_bar_height) * 1.5)
                , (int) getResources().getDimension(R.dimen.action_bar_height) * 3);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(
                ContextCompat.getColor(getContext(), R.color.colorAccent)
        );
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getContext(), R.color.crFEFEFE)
        );
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout
                    .setProgressBackgroundColorSchemeColor(ContextCompat
                            .getColor(getContext(), actionChangeSkin.getColorAccentId()));
        }
    }

    @Override
    public void onRefresh() {

    }
}

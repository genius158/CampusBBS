package com.yan.campusbbs.module.selfcenter.ui.selfmore;

import android.os.Build;
import android.support.v4.content.ContextCompat;

import com.yan.campusbbs.R;
import com.yan.campusbbs.module.selfcenter.ui.friendpage.FriendPageActivity;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;

import java.util.List;

/**
 * Created by yan on 2017/4/25.
 */

public class SelfMainPageMoreActivity extends FriendPageActivity {

    @Override
    public void dataSuccess(List<DataMultiItem> dataMultiItems) {
        if (pageNo == 1) {
            if (dataMultiItems.size() > 3) {
                adapter.setEnableLoadMore(true);
                adapter.setOnLoadMoreListener(
                        () -> {
                            mPresenter.getSelfTopicMore(++pageNo);
                        });
            }
            swipeRefreshLayout.setRefreshing(false);
            this.dataMultiItems.clear();
            this.dataMultiItems.addAll(dataMultiItems);
            adapter.notifyDataSetChanged();
            return;
        }
        adapter.loadMoreComplete();
        if (dataMultiItems == null || dataMultiItems.isEmpty()) {
            adapter.setEnableLoadMore(false);
        }
        int tempSize = this.dataMultiItems.size();
        this.dataMultiItems.addAll(dataMultiItems);
        adapter.notifyItemRangeInserted(tempSize, this.dataMultiItems.size() - tempSize);
    }

    @Override
    protected void dataInit() {
        mPresenter.getSelfTopicMore(pageNo);
        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(true));
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        mPresenter.getSelfTopicMore(pageNo);
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(
                    ContextCompat.getColor(getBaseContext()
                            , actionChangeSkin.getColorPrimaryDarkId())
            );
        }
        title.setText("个人动态");
        commonAppBar.setCardBackgroundColor(
                ContextCompat.getColor(this, actionChangeSkin.getColorPrimaryId())
        );
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            commonAppBar.setBackgroundColor(
                    ContextCompat.getColor(this, actionChangeSkin.getColorPrimaryId())
            );
        }

        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.crFEFEFE)
        );
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat
                .getColor(this, actionChangeSkin.getColorAccentId()));
    }
}

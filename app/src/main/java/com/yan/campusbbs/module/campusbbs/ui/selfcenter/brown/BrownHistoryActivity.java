package com.yan.campusbbs.module.campusbbs.ui.selfcenter.brown;

import android.os.Build;
import android.support.v4.content.ContextCompat;

import com.yan.campusbbs.R;
import com.yan.campusbbs.config.CacheConfig;
import com.yan.campusbbs.module.campusbbs.data.TopicDetailData;
import com.yan.campusbbs.module.common.data.TopicCacheData;
import com.yan.campusbbs.module.selfcenter.data.BrownHistroyTopicData;
import com.yan.campusbbs.module.selfcenter.ui.friendpage.FriendPageActivity;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.utils.ACache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yan on 2017/4/25.
 */

public class BrownHistoryActivity extends FriendPageActivity {

    @Override
    public void dataSuccess(List<DataMultiItem> dataMultiItems) {
        this.dataMultiItems.clear();
        this.dataMultiItems.addAll(dataMultiItems);
        Collections.reverse(this.dataMultiItems);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void dataInit() {
        swipeRefreshLayout.setEnabled(false);

        TopicCacheData cacheData = (TopicCacheData) ACache.get(getBaseContext()).getAsObject(CacheConfig.TOPIC_CACHE);
        if (cacheData != null) {
            List<DataMultiItem> dataMultiItems = new ArrayList<>();
            for (TopicDetailData.DataBean.TopicDetailInfoBean.UserTopicInfoBean infoBean : cacheData.topicDetailDatas) {
                dataMultiItems.add(new BrownHistroyTopicData(infoBean));
            }
            dataSuccess(dataMultiItems);
        } else {
            toastUtils.showShort("数据为空");
        }
    }

    @Override
    public void onRefresh() {
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(
                    ContextCompat.getColor(getBaseContext()
                            , actionChangeSkin.getColorPrimaryDarkId())
            );
        }
        title.setText("浏览记录");
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

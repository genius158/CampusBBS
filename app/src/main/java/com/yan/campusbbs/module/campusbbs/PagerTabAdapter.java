package com.yan.campusbbs.module.campusbbs;

import android.content.Context;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yan.campusbbs.R;
import com.yan.campusbbs.util.RxBus;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by yan on 2017/2/7.
 */

public class PagerTabAdapter extends BaseQuickAdapter<String> {

    @Inject
    public PagerTabAdapter(List<String> data, Context context, RxBus rxBus) {
        super(R.layout.study_pager_tab_item, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String str) {
        baseViewHolder.setText(R.id.tv_string, str);
    }
    /*     if (position == 0) {
                                SPUtils.putInt(context, MODE_PRIVATE, SharedPreferenceConfig.SHARED_PREFERENCE, SharedPreferenceConfig.SKIN_INDEX, 0);
                                rxBus.post(new ActionChangeSkin(0));
                            } else if (position == 1) {
                                SPUtils.putInt(context, MODE_PRIVATE, SharedPreferenceConfig.SHARED_PREFERENCE, SharedPreferenceConfig.SKIN_INDEX, 1);
                                rxBus.post(new ActionChangeSkin(1));
                            } else if (position == 2) {
                                SPUtils.putInt(context, MODE_PRIVATE, SharedPreferenceConfig.SHARED_PREFERENCE, SharedPreferenceConfig.SKIN_INDEX, 2);
                                rxBus.post(new ActionChangeSkin(2));
                            } else if (position == 3) {
                                SPUtils.putInt(context, MODE_PRIVATE, SharedPreferenceConfig.SHARED_PREFERENCE, SharedPreferenceConfig.SKIN_INDEX, 3);
                                rxBus.post(new ActionChangeSkin(3));
                            }*/
}
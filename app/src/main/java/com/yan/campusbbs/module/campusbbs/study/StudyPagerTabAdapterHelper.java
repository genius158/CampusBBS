package com.yan.campusbbs.module.campusbbs.study;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yan.adapter.CustomAdapter;
import com.yan.adapter.CustomAdapterItem;
import com.yan.campusbbs.R;
import com.yan.campusbbs.module.StringViewHolder;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.RxBus;
import com.yan.campusbbs.util.SPUtils;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by yan on 2017/2/7.
 */

public class StudyPagerTabAdapterHelper {

    public static CustomAdapter getAdapter(final Context context, List<String> data, RxBus rxBus) {
        return new CustomAdapter(data)
                .addAdapterItem(new CustomAdapterItem<StringViewHolder, String>() {
                    @Override
                    public Class dataType() {
                        return String.class;
                    }

                    @Override
                    public StringViewHolder viewHolder(ViewGroup parent) {
                        return new StringViewHolder(
                                LayoutInflater.from(context).inflate(R.layout.study_pager_tab_item, parent, false)
                        );
                    }

                    @Override
                    public void bindData(StringViewHolder holder, String item, int position) {
                        holder.textView.setText(item);
                        holder.textView.setOnClickListener(view -> {
                            if (position == 0) {
                                SPUtils.putInt(context, MODE_PRIVATE, SPUtils.SHARED_PREFERENCE, SPUtils.SKIN_INDEX, 0);
                                rxBus.post(new ActionChangeSkin(0));
                            } else if (position == 1) {
                                SPUtils.putInt(context, MODE_PRIVATE, SPUtils.SHARED_PREFERENCE, SPUtils.SKIN_INDEX, 1);
                                rxBus.post(new ActionChangeSkin(1));
                            }
                        });
                    }
                });
    }
}

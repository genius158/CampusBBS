package com.yan.campusbbs.module.selfcenter.adapterholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yan.adapter.CustomAdapter;
import com.yan.adapter.CustomAdapterItem;
import com.yan.adapter.StateAdapterItem;
import com.yan.campusbbs.R;
import com.yan.campusbbs.module.StringViewHolder;

import java.util.List;

/**
 * Created by yan on 2017/2/7.
 */

public class SelfCenterAdapterHelper {

    public static CustomAdapter getAdapter(final Context context, List<String> data) {
        return new CustomAdapter(data)
                .addAdapterItem(new CustomAdapterItem<StringViewHolder, String>() {
                    @Override
                    public Class dataType() {
                        return String.class;
                    }

                    @Override
                    public StringViewHolder viewHolder(ViewGroup parent) {
                        return new StringViewHolder(
                                LayoutInflater.from(context).inflate(R.layout.string_data, parent, false)
                        );
                    }

                    @Override
                    public void bindData(StringViewHolder holder, String item, int position) {
                        holder.textView.setText(item);
                    }
                })
                .addAdapterItem(new StateAdapterItem(StateAdapterItem.HEADER) {
                    @Override
                    public RecyclerView.ViewHolder viewHolder(ViewGroup parent) {
                        StringViewHolder holderString = new StringViewHolder(
                                LayoutInflater.from(context).inflate(R.layout.string_data, parent, false));
                        holderString.textView.setBackgroundResource(R.mipmap.ic_launcher);
                        ViewGroup.LayoutParams layoutParams = holderString.textView.getLayoutParams();
                        layoutParams.height = 800;
                        holderString.textView.setLayoutParams(layoutParams);

                        return holderString;
                    }
                });
    }
}

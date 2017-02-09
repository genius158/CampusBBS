package com.yan.campusbbs.module.selfcenter.adapterholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yan.adapter.CustomAdapter;
import com.yan.adapter.CustomAdapterItem;
import com.yan.adapter.StateAdapterItem;
import com.yan.campusbbs.R;

import java.util.List;

/**
 * Created by yan on 2017/2/7.
 */

public class SelfCenterAdapterHelper {

    public static CustomAdapter getAdapter(final Context context, List<String> data) {
        return new CustomAdapter(data)
                .addAdapterItem(new CustomAdapterItem<StringViewHolderString, String>() {
                    @Override
                    public Class dataType() {
                        return String.class;
                    }

                    @Override
                    public StringViewHolderString viewHolder(ViewGroup parent) {
                        return new StringViewHolderString(
                                LayoutInflater.from(context).inflate(R.layout.string_data, parent, false)
                        );
                    }

                    @Override
                    public void bindData(StringViewHolderString holder, String item, int position) {
                        holder.textView.setText(item);
                    }
                })
                .addAdapterItem(new StateAdapterItem(StateAdapterItem.HEADER) {
                    @Override
                    public RecyclerView.ViewHolder viewHolder(ViewGroup parent) {
                        StringViewHolderString holderString = new StringViewHolderString(
                                LayoutInflater.from(context).inflate(R.layout.string_data, parent, false));
                        holderString.textView.setBackgroundResource(R.color.colorPrimary);

                        return holderString;
                    }
                });
    }
}

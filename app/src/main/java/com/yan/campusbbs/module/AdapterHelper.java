package com.yan.campusbbs.module;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yan.adapter.CustomAdapter;
import com.yan.adapter.CustomAdapterItem;
import com.yan.campusbbs.R;

import java.util.List;

/**
 * Created by yan on 2017/2/7.
 */

public class AdapterHelper {

    public static CustomAdapter getAdapter(final Context context, List<String> data) {
        return new CustomAdapter(data)
                .addAdapterItem(new CustomAdapterItem<ViewHolderString, String>() {
                    @Override
                    public Class dataType() {
                        return String.class;
                    }

                    @Override
                    public ViewHolderString viewHolder(ViewGroup parent) {
                        return new ViewHolderString(
                                LayoutInflater.from(context).inflate(R.layout.string_data, parent, false)
                        );
                    }

                    @Override
                    public void bindData(ViewHolderString holder, String item, int position) {
                        holder.textView.setText(item);
                    }
                });
    }
}

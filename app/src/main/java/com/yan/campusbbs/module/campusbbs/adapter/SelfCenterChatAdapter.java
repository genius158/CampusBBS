package com.yan.campusbbs.module.campusbbs.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.yan.campusbbs.R;
import com.yan.campusbbs.module.campusbbs.data.SelfCenterChatData;
import com.yan.campusbbs.module.selfcenter.ui.friendpage.FriendPageActivity;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.utils.TimeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by yan on 2017/3/13.
 */

public class SelfCenterChatAdapter extends BaseMultiItemQuickAdapter<DataMultiItem, BaseViewHolder> {

    public static final int ITEM_TYPE_CHAT_SELF = 1;
    public static final int ITEM_TYPE_CHAT_OTHER = 2;
    private Context context;

    @Inject
    public SelfCenterChatAdapter(List<DataMultiItem> data, Context context) {
        super(data);
        this.context = context;
        addItemType(ITEM_TYPE_CHAT_SELF, R.layout.activity_bbs_self_item_chat_self);
        addItemType(ITEM_TYPE_CHAT_OTHER, R.layout.activity_bbs_self_item_chat_other);
    }

    @Override
    protected void convert(BaseViewHolder holder, DataMultiItem item) {
        SelfCenterChatData chatData = (SelfCenterChatData) item.data;

        switch (holder.getItemViewType()) {
            case ITEM_TYPE_CHAT_SELF:
            case ITEM_TYPE_CHAT_OTHER:
                ((SimpleDraweeView) holder.getView(R.id.sdv_head))
                        .setImageURI(chatData.userProfile.faceUrl);
                (holder.getView(R.id.sdv_head)).setOnClickListener(v -> {
                    String signStr = chatData.userProfile.selfSignature;
                    boolean isJson;
                    try {
                        new JsonParser().parse(signStr);
                        isJson = true;
                    } catch (JsonParseException e) {
                        isJson = false;
                    }
                    if (isJson){
                        Log.e(TAG, "convert: " + signStr);
                        try {
                            JSONObject jsonObject = new JSONObject(signStr);
                            context.startActivity(new Intent(context, FriendPageActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    .putExtra("userId", jsonObject.getString("userId"))
                            );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                holder.setText(R.id.tv_text, chatData.text);
                holder.setText(R.id.tv_time, TimeUtils.getTime(chatData.time));

                break;
        }
    }
}

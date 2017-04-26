package com.yan.campusbbs.module.campusbbs.ui.selfcenter.chat;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMMessage;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;
import com.yan.campusbbs.module.ImManager;

import java.util.List;

import javax.inject.Inject;

public class ChatPresenter implements ChatContract.Presenter {
    private static final String TAG = "ChatPresenter";

    private ChatContract.View view;
    private Context context;
    private String userId;

    private TIMUserProfile otherProfile;
    private TIMUserProfile selfProfile;

    private TIMConversation conversation;
    private TIMMessage lastMessage;

    @Inject
    public ChatPresenter(Context context, ChatContract.View view, String userId) {
        this.view = view;
        this.context = context;
        this.userId = userId;
        init();
    }

    private void init() {
        conversation = ImManager.getImManager().getTIM().getConversation(
                TIMConversationType.C2C,
                userId);

        ImManager.getImManager().getUsersProfile(otherInfoCallBack, userId);
    }

    private TIMValueCallBack<TIMUserProfile> selfProfileCallBack = new TIMValueCallBack<TIMUserProfile>() {
        @Override
        public void onError(int i, String s) {
            Log.e(TAG, "onError: " + s);
        }

        @Override
        public void onSuccess(TIMUserProfile userProfile) {
            selfProfile = userProfile;
            conversation.getMessage(15, null, new TIMValueCallBack<List<TIMMessage>>() {
                @Override
                public void onError(int i, String s) {
                    Log.e(TAG, "get message error" + s);
                }

                @Override
                public void onSuccess(List<TIMMessage> timMessages) {
                    view.setData(timMessages);
                    if (timMessages != null
                            && !timMessages.isEmpty()) {
                        ChatPresenter.this.lastMessage = timMessages.get(timMessages.size() - 1);
                    }
                }
            });
        }
    };
    private TIMValueCallBack<List<TIMUserProfile>> otherInfoCallBack = new TIMValueCallBack<List<TIMUserProfile>>() {
        @Override
        public void onError(int i, String s) {
            Log.e(TAG, "onError: " + s);
        }

        @Override
        public void onSuccess(List<TIMUserProfile> timUserProfiles) {
            for (TIMUserProfile userProfile : timUserProfiles) {
                otherProfile = userProfile;
                String other;
                if (!TextUtils.isEmpty(otherProfile.getNickName())) {
                    other = otherProfile.getNickName();
                } else {
                    other = otherProfile.getIdentifier();
                }
                view.setTitle(other);
            }
        }
    };

    @Override
    public void start() {

    }


    @Override
    public void initData() {
        ImManager.getImManager().getSelfProfile(selfProfileCallBack);
    }

    @Override
    public void getMoreChatData() {
        conversation.getMessage(15, lastMessage, new TIMValueCallBack<List<TIMMessage>>() {
            @Override
            public void onError(int i, String s) {
                Log.e(TAG, "get message error" + s);
            }

            @Override
            public void onSuccess(List<TIMMessage> timMessages) {
                view.setLoadMoreData(timMessages);
                if (timMessages != null
                        && !timMessages.isEmpty()) {
                    ChatPresenter.this.lastMessage = timMessages.get(timMessages.size() - 1);
                }
            }
        });
    }

    @Override
    public void getLatestData() {
        conversation.getMessage(1, null, new TIMValueCallBack<List<TIMMessage>>() {
            @Override
            public void onError(int i, String s) {
                Log.e(TAG, "get message error" + s);
            }

            @Override
            public void onSuccess(List<TIMMessage> timMessages) {
                view.addLatestData(timMessages.get(0));
            }
        });
    }

    @Override
    public void setReadMessage() {
        conversation.setReadMessage();
    }

    @Override
    public TIMUserProfile getSelfProfile() {
        return selfProfile;
    }

    @Override
    public TIMUserProfile getOtherProfile() {
        return otherProfile;
    }
}

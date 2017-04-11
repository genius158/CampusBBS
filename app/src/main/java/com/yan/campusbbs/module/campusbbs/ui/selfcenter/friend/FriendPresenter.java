package com.yan.campusbbs.module.campusbbs.ui.selfcenter.friend;

import android.content.Context;
import android.util.Log;

import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMTextElem;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;
import com.yan.campusbbs.module.ImManager;
import com.yan.campusbbs.module.campusbbs.data.SelfCenterFriendData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FriendPresenter implements FriendContract.Presenter {
    private static final String TAG = "FriendPresenter";

    private FriendContract.View view;
    private Context context;

    private List<TIMUserProfile> friendUserProfiles;

    @Inject
    public FriendPresenter(Context context, FriendContract.View view) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void start() {

    }

    private TIMValueCallBack<List<TIMUserProfile>> timValueCallBack = new TIMValueCallBack<List<TIMUserProfile>>() {
        @Override
        public void onError(int i, String s) {
            view.error(s);
        }

        @Override
        public void onSuccess(List<TIMUserProfile> timUserProfiles) {
            friendUserProfiles = timUserProfiles;
            for (TIMUserProfile userProfile : timUserProfiles) {
                view.addFriends(new SelfCenterFriendData(userProfile, userProfile.getSelfSignature(), false)
                        .setTimestamp(0));
            }
            initConversation();
        }
    };

    private void initConversation() {
        List<TIMConversation> list = TIMManager.getInstance().getConversionList();
        List<TIMConversation> result = new ArrayList<>();
        if (list.isEmpty()) {
            view.update();
        }
        for (TIMConversation conversation : list) {
            if (conversation.getType() != TIMConversationType.C2C
                    ) continue;
            result.add(conversation);
            conversation.getMessage(1, null, new TIMValueCallBack<List<TIMMessage>>() {
                @Override
                public void onError(int i, String s) {
                    Log.e(TAG, "get message error" + s);
                }

                @Override
                public void onSuccess(List<TIMMessage> timMessages) {
                    for (TIMMessage msg : timMessages) {
                        TIMUserProfile senderProfile = msg.getSenderProfile();
                        String sender = msg.getSender();
                        for (int i = 0; i < msg.getElementCount(); ++i) {
                            TIMElem elem = msg.getElement(i);
                            TIMElemType elemType = elem.getType();
                            if (elemType == TIMElemType.Text) {
                                TIMTextElem textElem = (TIMTextElem) elem;
                                for (TIMUserProfile userProfile : friendUserProfiles) {
                                    if (msg.isSelf()) {
                                        String peer = msg.getConversation().getPeer();
                                        setDataFromSelf(peer, textElem.getText());
                                        break;
                                    } else if (userProfile.getIdentifier().equals(sender)) {
                                        view.addConversationData(new SelfCenterFriendData(senderProfile, textElem.getText(), false)
                                                .setTimestamp(msg.timestamp()));
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    view.update();
                }
            });
        }
    }


    private void setDataFromSelf(String peer, String text) {

        ImManager.getImManager().getUsersProfile(new TIMValueCallBack<List<TIMUserProfile>>() {
            @Override
            public void onError(int i, String s) {
                Log.e(TAG, "onError: " + s);
            }

            @Override
            public void onSuccess(List<TIMUserProfile> timUserProfiles) {
                for (TIMUserProfile userProfile : timUserProfiles) {
                    view.addConversationData(new SelfCenterFriendData(userProfile, text, true));
                    view.update();
                }
            }
        }, peer);

    }

    @Override
    public void getConversation() {
        ImManager.getImManager().getFriendList(timValueCallBack);
    }
}

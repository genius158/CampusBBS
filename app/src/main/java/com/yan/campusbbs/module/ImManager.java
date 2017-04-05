package com.yan.campusbbs.module;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.config.CacheConfig;
import com.yan.campusbbs.module.selfcenter.data.LoginInfoData;
import com.yan.campusbbs.util.ACache;
import com.yan.campusbbs.util.SPUtils;
import com.yan.campusbbs.util.ToastUtils;

import java.util.ArrayList;

import imsdk.data.IMMyself;
import imsdk.data.IMSDK;
import imsdk.data.recentcontacts.IMMyselfRecentContacts;
import imsdk.data.relations.IMMyselfRelations;

/**
 * Created by Administrator on 2017/4/5 0005.
 */

public class ImManager {
    private static final String TAG = "ImManager";

    private static ImManager imManager;

    private Context context;
    private ToastUtils toastUtils;

    public static ImManager getImManager() {
        return imManager;
    }

    public static ImManager install(Context context, ToastUtils toastUtils, SPUtils spUtils) {
        if (imManager == null) {
            return imManager = new ImManager(context, toastUtils, spUtils);
        }
        return imManager;
    }

    // 1. 获取最近联系人
    public ArrayList<String> getUsersList() {
        return IMMyselfRecentContacts.getUsersList();
    }

    // 1. 获取好友列表（得到 CustomUserID 列表，本地数据，同步）
    public ArrayList<String> getFriendsList() {
        return IMMyselfRelations.getFriendsList();
    }

    public String getUser(int index) {
        return IMMyselfRecentContacts.getUser(index);
    }

    public long getUnreadChatMessageCount() {
        return IMMyselfRecentContacts.getUnreadChatMessageCount();
    }

    public boolean removeUser(String userId) {
        return IMMyselfRecentContacts.removeUser(userId);
    }

    public boolean isMyFriend(String userId) {
        return IMMyselfRelations.isMyFriend(userId);
    }

    public void sendFriendRequest(String userId, IMMyself.OnActionListener onActionListener) {
        IMMyselfRelations.sendFriendRequest("你好，在吗？", userId, 10, onActionListener);
    }


    public void removeUserFromFriendsList(String userId) {
        // 5. 移除好友
        IMMyselfRelations.removeUserFromFriendsList(userId, 10, new IMMyself.OnActionListener() {
            @Override
            public void onSuccess() {
                // 移除执行成功（注意！移除和添加不一样，移除不需要对方授权）
            }

            @Override
            public void onFailure(String error) {
                // 移除失败
            }
        });
    }


    public void rejectToFriendRequest(String reason, String userId) {
        // 7. 发送请求回执之拒绝加为好友
        IMMyselfRelations.rejectToFriendRequest(reason, userId, 10, new IMMyself.OnActionListener() {
            @Override
            public void onSuccess() {
                // 回执发送成功
            }

            @Override
            public void onFailure(String error) {
                // 回执发送失败
            }
        });

    }

    public void rejectToFriendRequest(String reason, String userId, IMMyself.OnActionListener onActionListener) {
        // 7. 发送请求回执之拒绝加为好友
        IMMyselfRelations.rejectToFriendRequest(reason, userId, 10, onActionListener);
    }

    public void setOnRelationsEventListener(IMMyselfRelations.OnRelationsEventListener onRelationsEventListener) {
        IMMyselfRelations.setOnRelationsEventListener(onRelationsEventListener);
    }

    public void setOnDataChangedListener( ) {
        IMMyselfRelations.setOnDataChangedListener(new IMSDK.OnDataChangedListener() {
            @Override
            public void onDataChanged() {
                Log.e(TAG, "onDataChanged: " );
            }
        });
    }

    public void setOnRelationsEventListener() {
        IMMyselfRelations.setOnRelationsEventListener(new IMMyselfRelations.OnRelationsEventListener() {
            @Override
            public void onInitialized() {
                Log.e(TAG, "onInitialized: ");
            }

            @Override
            public void onReceiveFriendRequest(String s, String s1, long l) {
                Log.e(TAG, "onReceiveFriendRequest: " + s + "      " + s1);
            }

            @Override
            public void onReceiveAgreeToFriendRequest(String s, long l) {
                Log.e(TAG, "onReceiveAgreeToFriendRequest: " + s + "    ");
            }

            @Override
            public void onReceiveRejectToFriendRequest(String s, String s1, long l) {
                Log.e(TAG, "onReceiveRejectToFriendRequest: " + s + "   " + s1);
            }

            @Override
            public void onBuildFriendshipWithUser(String s, long l) {
                Log.e(TAG, "onBuildFriendshipWithUser: " + s);
            }
        });
    }

    public void removeUserFromFriendsList(String userId, IMMyself.OnActionListener onActionListener) {
        // 5. 移除好友
        IMMyselfRelations.removeUserFromFriendsList(userId, 10, onActionListener);
    }


    public void sendFriendRequest(String userId) {
        IMMyselfRelations.sendFriendRequest("你好，在吗？", userId, 10, new IMMyself.OnActionListener() {
            @Override
            public void onSuccess() {
                Log.e(TAG, "onSuccess: ");
                // 发送成功（注意！不是添加成功）
            }

            @Override
            public void onFailure(String error) {
                Log.e(TAG, "onFailure: " + error);
                // 发送失败
            }
        });
    }

    public void agreeToFriendRequest(String userId) {
        // 6. 发送请求回执之同意加为好友
        IMMyselfRelations.agreeToFriendRequest(userId, 10, new IMMyself.OnActionListener() {
            @Override
            public void onSuccess() {
                Log.e(TAG, "onSuccess: ");
                // 发送成功（注意！不是添加成功）
            }

            @Override
            public void onFailure(String error) {
                Log.e(TAG, "onFailure: " + error);
                // 发送失败
            }
        });
    }

    public void agreeToFriendRequest(String userId, IMMyself.OnActionListener onActionListener) {
        // 6. 发送请求回执之同意加为好友
        IMMyselfRelations.agreeToFriendRequest(userId, 10, onActionListener);
    }

    public boolean clearUnreadChatMessage() {
        return IMMyselfRecentContacts.clearUnreadChatMessage();
    }

    public boolean clearUnreadChatMessage(String userId) {
        return IMMyselfRecentContacts.clearUnreadChatMessage(userId);
    }

    private ImManager(Context context, ToastUtils toastUtils, SPUtils spUtils) {
        this.context = context;
        this.toastUtils = toastUtils;
        init();
    }

    private void init() {
        // 设置监听器
        IMMyself.setOnReceivedMessageListener(new IMMyself.OnReceivedMessageListener() {

            @Override
            public void onReceivedText(String s, String s1, String s2, long l) {
                toastUtils.showUIShort("s:" + s + "   s1:" + s1 + "   s2:" + s2 + "    l:" + l);
                Log.e(TAG, "onReceivedText: " + "time:" + s + "   text:" + s1
                        + "   fromUserId:" + s2 + "    l:" + l);
            }

            @Override
            public void onReceivedBitmap(String s, String s1, long l) {

            }

            @Override
            public void onReceivedBitmapProgress(double v, String s, String s1, long l) {

            }

            @Override
            public void onReceivedBitmapFinish(Uri uri, String s, String s1, long l) {

            }

            @Override
            public void onReceivedAudio(String s, String s1, long l) {

            }

            @Override
            public void onReceivedAudioProgress(double v, String s, String s1, long l) {

            }

            @Override
            public void onReceivedAudioFinish(Uri uri, String s, String s1, long l) {

            }
        });

        // 监听最近联系人列表变化
        IMMyselfRecentContacts.setOnDataChangedListener(new IMSDK.OnDataChangedListener() {
            @Override
            public void onDataChanged() {
                Log.e(TAG, "onDataChanged: ");
            }
        });

        IMMyself.setOnReceivedSystemMessageListener(new IMMyself.OnReceivedSystemMessageListener() {
            @Override
            public void onReceivedSystemText(String s, long l) {
                Log.e(TAG, "onReceivedSystemText: " + s + "  l:" + l);
            }
        });


        // 1. 获取是否已初始化
// 只有在返回为 true 后，调用 IMMyselfRelations 的数据接口（比如好友列表）才有数据返回
        boolean isInitialized = IMMyselfRelations.isInitialized();

// 2. 监听初始化通知
        IMMyselfRelations.setOnRelationsEventListener(new IMMyselfRelations.OnRelationsEventListener() {
            @Override
            public void onInitialized() {
                Log.e(TAG, "onInitialized: ");
                // 初始化回调。 当前回调表示服务器和本地的数据已经初始化。
            }

            @Override
            public void onReceiveFriendRequest(String text, String fromCustomUserID, long serverSendTime) {
                Log.e(TAG, "onReceiveFriendRequest: " + text + "   fromCustomUserID:" + fromCustomUserID);
            }

            @Override
            public void onReceiveAgreeToFriendRequest(String fromCustomUserID, long serverSendTime) {
                Log.e(TAG, "onReceiveAgreeToFriendRequest: " + " fromCustomUserID:" + fromCustomUserID);

            }

            @Override
            public void onReceiveRejectToFriendRequest(String reason, String fromCustomUserID, long serverSendTime) {
                Log.e(TAG, "onReceiveRejectToFriendRequest: " + "reason:" + reason + " fromCustomUserID:" + fromCustomUserID);
            }

            @Override
            public void onBuildFriendshipWithUser(String customUserID, long serverSendTime) {
                Log.e(TAG, "onBuildFriendshipWithUser: " + "customUserID:" + customUserID);
            }
        });
    }

    public void login() {
        if (ACache.get(context).getAsObject(CacheConfig.USER_INFO) != null
                && !TextUtils.isEmpty(((ApplicationCampusBBS) (context.getApplicationContext())).getSessionId())) {
            LoginInfoData loginInfoData = (LoginInfoData) ACache.get(context).getAsObject(CacheConfig.USER_INFO);
            IMMyself.setCustomUserID(loginInfoData.getData().getUserInfo().getUserAccount());
            IMMyself.setPassword(loginInfoData.getData().getUserInfo().getUserPassword());
            // 执行该代码则会执行自动登录，并监听登录状态。

            IMMyself.login(true, 5, new IMMyself.OnActionListener() {
                @Override
                public void onSuccess() {
                    Log.e(TAG, "onSuccess: " + "一键登录成功");

                    toastUtils.showUIShort("一键登录成功");
                }

                @Override
                public void onFailure(String error) {
                    if (error.equals("Timeout")) {
                        error = "一键登录超时";
                    } else if (error.equals("Wrong Password")) {
                        error = "密码错误";
                    }
                    Log.e(TAG, "onFailure: " + error);
                    toastUtils.showUIShort(error);
                }
            });

        }
    }

    public void sendText(String text, String friendId) {
        // 动态配置超时时长
        IMMyself.sendText(text, friendId, (text.length() / 400 + 1) * 5, new IMMyself.OnActionListener() {
            @Override
            public void onSuccess() {
                Log.e(TAG, "onSuccess: " + "发送成功:");
            }

            @Override
            public void onFailure(String error) {
                toastUtils.showUIShort("发送失败");
                Log.e(TAG, "onFailure: " + "发送失败:" + error);
            }
        });
    }

}

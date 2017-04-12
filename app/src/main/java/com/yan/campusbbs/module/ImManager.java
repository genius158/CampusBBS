package com.yan.campusbbs.module;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.TIMAddFriendRequest;
import com.tencent.TIMCallBack;
import com.tencent.TIMConnListener;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMFriendAddResponse;
import com.tencent.TIMFriendGroup;
import com.tencent.TIMFriendResponseType;
import com.tencent.TIMFriendResult;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMFriendshipProxyListener;
import com.tencent.TIMFriendshipProxyStatus;
import com.tencent.TIMLogLevel;
import com.tencent.TIMLogListener;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMMessageListener;
import com.tencent.TIMSNSChangeInfo;
import com.tencent.TIMSNSSystemElem;
import com.tencent.TIMSNSSystemType;
import com.tencent.TIMTextElem;
import com.tencent.TIMUser;
import com.tencent.TIMUserProfile;
import com.tencent.TIMUserStatusListener;
import com.tencent.TIMValueCallBack;
import com.yan.campusbbs.R;
import com.yan.campusbbs.config.CacheConfig;
import com.yan.campusbbs.module.campusbbs.data.SelfCenterMessageCacheData;
import com.yan.campusbbs.module.campusbbs.data.SelfCenterMessageData;
import com.yan.campusbbs.module.campusbbs.ui.selfcenter.chat.NotifyChatActivity;
import com.yan.campusbbs.module.campusbbs.ui.selfcenter.friend.FriendsActivity;
import com.yan.campusbbs.module.campusbbs.ui.selfcenter.message.MessageActivity;
import com.yan.campusbbs.module.common.data.UserProfile;
import com.yan.campusbbs.util.ACache;
import com.yan.campusbbs.util.RegExpUtils;
import com.yan.campusbbs.util.RxBus;

import java.util.ArrayList;
import java.util.List;

import tencent.tls.platform.TLSAccountHelper;
import tencent.tls.platform.TLSErrInfo;
import tencent.tls.platform.TLSLoginHelper;
import tencent.tls.platform.TLSPwdLoginListener;
import tencent.tls.platform.TLSPwdRegListener;
import tencent.tls.platform.TLSUserInfo;

/**
 * Created by yan on 2017/4/9.
 * 即时通讯
 */

public class ImManager {
    private static final String TAG = "ImManager";

    private static final int SDK_APP_ID = 1400028200;
    private static final int ACCOUNT_TYPE = 11891;
    private static final String APP_VER = "1.0";

    private String userSig;
    private String identifier;

    private static ImManager imManager;
    private Context context;
    private TLSAccountHelper accountHelper;
    private TLSLoginHelper loginHelper;

    private RxBus rxBus;

    private boolean isLogin;

    public static void init(Context context, RxBus rxBus) {
        imManager = new ImManager(context, rxBus);
    }

    public TIMManager getTIM() {
        return TIMManager.getInstance();
    }

    public static ImManager getImManager() {
        return imManager;
    }

    public void sendText(String peer, String text) {

        TIMConversation conversation = TIMManager.getInstance().getConversation(
                TIMConversationType.C2C,
                peer);

        TIMMessage msg = new TIMMessage();

        TIMTextElem elem = new TIMTextElem();
        elem.setText(text);

        if (msg.addElement(elem) != 0) {
            Log.e(TAG, "addElement failed");
            return;
        }

        conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {
            @Override
            public void onError(int code, String desc) {
                Log.e(TAG, "send message failed. code: " + code + " errmsg: " + desc);
            }

            @Override
            public void onSuccess(TIMMessage msg) {//发送消息成功
                rxBus.post(new Action.ActionGetChatMessage(peer));
            }
        });
    }

    public void initStorage() {
        TIMUser user = new TIMUser();
        user.setIdentifier(identifier);

        TIMManager.getInstance().initStorage(
                SDK_APP_ID,
                user,
                userSig,
                new TIMCallBack() {

                    @Override
                    public void onSuccess() {
                        Log.e(TAG, "init success");
                    }

                    @Override
                    public void onError(int code, String desc) {
                        Log.e(TAG, "init failed. code: " + code + " errmsg: " + desc);
                    }
                });
    }

    public void login() {
        TIMUser user = new TIMUser();
        user.setIdentifier(identifier);

        TIMManager.getInstance().login(
                SDK_APP_ID,
                user,
                userSig,
                new TIMCallBack() {

                    @Override
                    public void onSuccess() {
                        Log.e(TAG, "login success");
                        rxBus.post(new Action.ActionImLogin());
//                        initStorage();
                        isLogin = true;
                    }

                    @Override
                    public void onError(int code, String desc) {
                        Log.e(TAG, "login failed. code: " + code + " errmsg: " + desc);
                    }
                });
    }

    public void logout() {
        TIMManager.getInstance().logout(new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                Log.e(TAG, "logout failed. code: " + code + " errmsg: " + desc);
            }

            @Override
            public void onSuccess() {
                //登出成功
            }
        });
    }

    private ImManager(Context context, RxBus rxBus) {
        this.context = context;
        this.rxBus = rxBus;
        getTIM().init(this.context);
        getTIM().disableCrashReport();
        getTIM().setLogLevel(TIMLogLevel.ERROR);
        accountHelper = TLSAccountHelper.getInstance();
        accountHelper = TLSAccountHelper.getInstance()
                .init(context, SDK_APP_ID, ACCOUNT_TYPE, APP_VER);
        loginHelper = TLSLoginHelper.getInstance()
                .init(context, SDK_APP_ID, ACCOUNT_TYPE, APP_VER);

        listenerInit();
    }

    private void listenerInit() {
        //设置消息监听器，收到新消息时，通过此监听器回调
        getTIM().addMessageListener(new TIMMessageListener() {
            //消息监听器
            @Override
            public boolean onNewMessages(List<TIMMessage> list) {//收到新消息
                for (TIMMessage msg : list) {
                    TIMUserProfile senderProfile = msg.getSenderProfile();
                    String sender = msg.getSender();

                    long timestamp = msg.timestamp();
                    for (int i = 0; i < msg.getElementCount(); ++i) {
                        TIMElem elem = msg.getElement(i);
                        //获取当前元素的类型
                        TIMElemType elemType = elem.getType();
                        Log.e(TAG, "elem type: " + elemType.name());
                        if (elemType == TIMElemType.Text) {
                            //处理文本消息
                            TIMTextElem textElem = (TIMTextElem) elem;
                            saveAsMessage(sender, senderProfile, textElem, timestamp);
                        }

                        if (elemType == TIMElemType.SNSTips) {
                            TIMSNSSystemElem systemElem = (TIMSNSSystemElem) elem;
                            TIMSNSSystemType timsnsSystemType = systemElem.getSubType();
                            if (timsnsSystemType == TIMSNSSystemType.TIM_SNS_SYSTEM_ADD_FRIEND) {
                                addNewFriendMessage(sender, timestamp);
                            }
                        }
                    }
                }
                return false;
            }
        });

        getTIM().setConnectionListener(new TIMConnListener() {
            @Override
            public void onConnected() {
                Log.e(TAG, "connected");
            }

            @Override
            public void onDisconnected(int code, String desc) {
                Log.e(TAG, "disconnected");
            }

            @Override
            public void onWifiNeedAuth(String s) {

            }
        });

        getTIM().setLogListener(new TIMLogListener() {
            @Override
            public void log(int level, String TAG, String msg) {
            }
        });

        getTIM().setUserStatusListener(new TIMUserStatusListener() {
            @Override
            public void onForceOffline() {
                //被踢下线
            }

            @Override
            public void onUserSigExpired() {
                //票据过期，需要换票后重新登录
            }
        });


        getTIM().setFriendshipProxyListener(new TIMFriendshipProxyListener() {
            /**
             *  收到代理状态变更通知
             *
             *  @param timFriendshipProxyStatus 当前状态
             */
            @Override
            public void OnProxyStatusChange(TIMFriendshipProxyStatus timFriendshipProxyStatus) {

            }

            /**
             *  添加好友通知
             *
             *  @param list 好友列表，详见{@see TIMUserProfile}
             */
            @Override
            public void OnAddFriends(List<TIMUserProfile> list) {
                Log.e(TAG, "OnAddFriends: ");
            }

            /**
             *  删除好友通知
             *
             *  @param list 用户id列表
             */
            @Override
            public void OnDelFriends(List<String> list) {

            }

            /**
             *  好友资料更新通知
             *
             *  @param list 资料列表,详见{@see TIMUserProfile}
             */
            @Override
            public void OnFriendProfileUpdate(List<TIMUserProfile> list) {

            }

            /**
             *  好友申请通知
             *
             *  @param list 好友申请者id列表，详见{@see TIMSNSChangeInfo}
             */
            @Override
            public void OnAddFriendReqs(List<TIMSNSChangeInfo> list) {

                for (TIMSNSChangeInfo changeInfo : list) {
                    Log.e(TAG, "OnAddFriendReqs: " + changeInfo.getIdentifier());
                }

            }

            /**
             *  添加好友分组通知
             *
             *  @param list 好友分组列表，详见{@see TIMFriendGroup}
             */
            @Override
            public void OnAddFriendGroups(List<TIMFriendGroup> list) {

            }

            /**
             *  删除好友分组通知
             *
             *  @param list 好友分组名称列表
             */
            @Override
            public void OnDelFriendGroups(List<String> list) {

            }

            /**
             *  好友分组更新通知
             *
             *  @param list 好友分组列表, 详见{@see TIMFriendGroup}
             */
            @Override
            public void OnFriendGroupUpdate(List<TIMFriendGroup> list) {

            }
        });

    }


    private void addNewFriendMessage(String identifier, long timestamp) {

        getUsersProfile(new TIMValueCallBack<List<TIMUserProfile>>() {
            @Override
            public void onError(int i, String s) {
                Log.e(TAG, "onError: " + s);
            }

            @Override
            public void onSuccess(List<TIMUserProfile> timUserProfiles) {
                if (timUserProfiles == null) return;
                for (TIMUserProfile userProfile : timUserProfiles) {
                    SelfCenterMessageCacheData messageCacheData;

                    if (ACache.get(context).getAsObject(CacheConfig.MESSAGE_INFO) == null) {
                        messageCacheData = new SelfCenterMessageCacheData(new ArrayList<>());
                    } else {
                        messageCacheData = (SelfCenterMessageCacheData) ACache.get(context).getAsObject(CacheConfig.MESSAGE_INFO);
                    }
                    String messageName;
                    if (!TextUtils.isEmpty(userProfile.getNickName())) {
                        messageName = userProfile.getNickName();
                    } else {
                        messageName = userProfile.getIdentifier();
                    }
                    messageCacheData.getCenterMessageDatas()
                            .add(new SelfCenterMessageData("新朋友", messageName + " 添加你为好友")
                                    .setUserProfile(new UserProfile(userProfile))
                                    .setTime(timestamp * 1000));
                    ACache.get(context).put(CacheConfig.MESSAGE_INFO, messageCacheData);
                    rxBus.post(new Action.ActionGetMessage());

                    notifyMessage("新朋友", messageName + "添加你为好友", 0, null);
                }
            }
        }, identifier);


    }

    private void addIncognizanceMessage(TIMUserProfile userProfile, TIMTextElem textElem, long timestamp) {

        SelfCenterMessageCacheData messageCacheData;

        if (ACache.get(context).getAsObject(CacheConfig.MESSAGE_INFO) == null) {
            messageCacheData = new SelfCenterMessageCacheData(new ArrayList<>());
        } else {
            messageCacheData = (SelfCenterMessageCacheData) ACache.get(context).getAsObject(CacheConfig.MESSAGE_INFO);
        }
        String messageName;
        if (!TextUtils.isEmpty(userProfile.getNickName())) {
            messageName = userProfile.getNickName();
        } else {
            messageName = userProfile.getIdentifier();
        }
        messageCacheData.getCenterMessageDatas()
                .add(new SelfCenterMessageData("陌生人", messageName + " 对你说: " + textElem.getText())
                        .setUserProfile(new UserProfile(userProfile))
                        .setTime(timestamp * 1000));
        ACache.get(context).put(CacheConfig.MESSAGE_INFO, messageCacheData);
        rxBus.post(new Action.ActionGetMessage());
        notifyMessage("陌生人", messageName + " 对你说: " + textElem.getText(), 0, null);
    }

    private void saveAsMessage(String sender, TIMUserProfile userProfile, TIMTextElem textElem, long timestamp) {
        if (userProfile == null) {
            SelfCenterMessageCacheData messageCacheData;

            if (ACache.get(context).getAsObject(CacheConfig.MESSAGE_INFO) == null) {
                messageCacheData = new SelfCenterMessageCacheData(new ArrayList<>());
            } else {
                messageCacheData = (SelfCenterMessageCacheData) ACache.get(context).getAsObject(CacheConfig.MESSAGE_INFO);
            }

            messageCacheData.getCenterMessageDatas()
                    .add(new SelfCenterMessageData("管理员", sender + " 对你说: " + textElem.getText())
                            .setUserProfile(new UserProfile(sender))
                            .setTime(timestamp * 1000));
            ACache.get(context).put(CacheConfig.MESSAGE_INFO, messageCacheData);
            rxBus.post(new Action.ActionGetMessage());
            notifyMessage("管理员", sender + " 对你说: " + textElem.getText(), 0, null);
            return;
        }

        //-------------------------------------------------------------------------------------------

        TIMFriendshipManager.getInstance().getFriendList(new TIMValueCallBack<List<TIMUserProfile>>() {
            @Override
            public void onError(int code, String desc) {
                Log.e(TAG, "onError: " + desc);
            }

            @Override
            public void onSuccess(List<TIMUserProfile> result) {
                for (TIMUserProfile res : result) {
                    if (res.getIdentifier().equals(userProfile.getIdentifier())) {
                        addChatData(userProfile, textElem);
                        return;
                    }
                }
                addIncognizanceMessage(userProfile, textElem, timestamp);

            }
        });
    }

    private void addChatData(TIMUserProfile userProfile, TIMTextElem textElem) {
        String other;
        if (!TextUtils.isEmpty(userProfile.getNickName())) {
            other = userProfile.getNickName();
        } else {
            other = userProfile.getIdentifier();
        }
        notifyMessage(other, textElem.getText(), 1, userProfile.getIdentifier());
        rxBus.post(new Action.ActionGetChatMessage(userProfile.getIdentifier()));
    }

    public void notifyMessage(String senderStr, String contentStr, int type, String identifier) {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        Intent notificationIntent = null;
        if (type == 0) {
            notificationIntent = new Intent(context, MessageActivity.class);
        } else if (type == 1) {
            ACache.get(context).put(CacheConfig.INTENT_CHAT_DATA, identifier);
            notificationIntent = new Intent(context, NotifyChatActivity.class);
        } else if (type == 2) {
            notificationIntent = new Intent(context, FriendsActivity.class);
        }
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        mBuilder.setContentTitle(senderStr)//设置通知栏标题
                .setContentText(contentStr)
                .setContentIntent(intent) //设置通知栏点击意图
                .setNumber(1) //设置通知集合的数量
                .setTicker(senderStr + ":" + contentStr) //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setDefaults(Notification.DEFAULT_ALL)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                .setSmallIcon(R.drawable.campus_logo)
                .setLargeIcon(BitmapFactory
                        .decodeResource(context.getResources(), R.drawable.campus_logo));
        Notification notify = mBuilder.build();
        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(0, notify);
    }

    public void verifyCode(String code) {
        accountHelper.TLSPwdRegVerifyCode(code, new TLSPwdRegListener() {
            @Override
            public void OnPwdRegAskCodeSuccess(int i, int i1) {
                Log.e(TAG, "OnPwdRegAskCodeSuccess: " + i + "    " + i1);
            }

            @Override
            public void OnPwdRegReaskCodeSuccess(int i, int i1) {
                Log.e(TAG, "OnPwdRegReaskCodeSuccess: " + i + "    " + i1);

            }

            @Override
            public void OnPwdRegVerifyCodeSuccess() {
                Log.e(TAG, "OnPwdRegVerifyCodeSuccess: ");
                rxBus.post(new Action.ActionCodeVerify(1));
            }

            @Override
            public void OnPwdRegCommitSuccess(TLSUserInfo tlsUserInfo) {
                Log.e(TAG, "OnPwdRegCommitSuccess: " + tlsUserInfo.identifier);

            }

            @Override
            public void OnPwdRegFail(TLSErrInfo tlsErrInfo) {
                Log.e(TAG, "OnPwdRegFail: " + tlsErrInfo.Msg);
                rxBus.post(new Action.ActionCodeVerify(-1));
            }

            @Override
            public void OnPwdRegTimeout(TLSErrInfo tlsErrInfo) {
                Log.e(TAG, "OnPwdRegTimeout: " + tlsErrInfo.Msg);
                rxBus.post(new Action.ActionCodeVerify(0));
            }
        });
    }

    public void getMSMCode(String userPhone) {
        accountHelper.TLSPwdRegAskCode("86-" + userPhone, new TLSPwdRegListener() {
            @Override
            public void OnPwdRegAskCodeSuccess(int i, int i1) {
                Log.e(TAG, "OnPwdRegAskCodeSuccess: " + i + "    " + i1);
                rxBus.post(new Action.ActionStateGetMessage(String.valueOf(i), 1));

            }

            @Override
            public void OnPwdRegReaskCodeSuccess(int i, int i1) {
                Log.e(TAG, "OnPwdRegReaskCodeSuccess: " + i + "    " + i1);

            }

            @Override
            public void OnPwdRegVerifyCodeSuccess() {
                Log.e(TAG, "OnPwdRegVerifyCodeSuccess: ");

            }

            @Override
            public void OnPwdRegCommitSuccess(TLSUserInfo tlsUserInfo) {
                Log.e(TAG, "OnPwdRegCommitSuccess: " + tlsUserInfo.identifier);
            }

            @Override
            public void OnPwdRegFail(TLSErrInfo tlsErrInfo) {
                Log.e(TAG, "OnPwdRegFail: " + tlsErrInfo.Msg);
                rxBus.post(new Action.ActionStateGetMessage(tlsErrInfo.Msg, -1));

            }

            @Override
            public void OnPwdRegTimeout(TLSErrInfo tlsErrInfo) {
                Log.e(TAG, "OnPwdRegTimeout: " + tlsErrInfo.Msg);
                rxBus.post(new Action.ActionStateGetMessage("", 0));
            }
        });
    }


    public void pwdCommit(String password) {
        accountHelper.TLSPwdRegCommit(password, new TLSPwdRegListener() {
            @Override
            public void OnPwdRegAskCodeSuccess(int i, int i1) {

            }

            @Override
            public void OnPwdRegReaskCodeSuccess(int i, int i1) {

            }

            @Override
            public void OnPwdRegVerifyCodeSuccess() {

            }

            @Override
            public void OnPwdRegCommitSuccess(TLSUserInfo tlsUserInfo) {
                Log.e(TAG, "OnPwdRegCommitSuccess: " + tlsUserInfo.identifier);
                rxBus.post(new Action.ActionPWDCommit(tlsUserInfo.identifier, 1));
            }

            @Override
            public void OnPwdRegFail(TLSErrInfo tlsErrInfo) {
                Log.e(TAG, "OnPwdRegFail: " + tlsErrInfo.Msg);
                rxBus.post(new Action.ActionPWDCommit("", -1));

            }

            @Override
            public void OnPwdRegTimeout(TLSErrInfo tlsErrInfo) {
                Log.e(TAG, "OnPwdRegTimeout: " + tlsErrInfo.Msg);
                rxBus.post(new Action.ActionPWDCommit("", 0));

            }
        });

    }

//    pwdRegListener = new TLSPwdRegListener() {
//        @Override
//        public void OnPwdRegAskCodeSuccess(int reaskDuration, int expireDuration) {
//      /* 请求下发短信成功，可以跳转到输入验证码进行校验的界面，同时可以开始倒计时, (reaskDuration 秒内不可以重发短信，如果在expireDuration 秒之后仍然没有进行短信验证，则应该回到上一步，重新开始流程)；在用户输入收到的短信验证码之后，可以调用PwdRegVerifyCode 进行验证。*/
//        }
//
//        @Override
//        public void OnPwdRegReaskCodeSuccess(int reaskDuration, int expireDuration) {
//      /* 重新请求下发短信成功，可以跳转到输入验证码进行校验的界面，并开始倒计时，(reaskDuration 秒内不可以再次请求重发，在expireDuration 秒之后仍然没有进行短信验证，则应该回到第一步，重新开始流程)；在用户输入收到的短信验证码之后，可以调用PwdRegVerifyCode 进行验证。*/
//        }
//
//        @Override
//        public void OnPwdRegVerifyCodeSuccess() {
//           /* 短信验证成功，接下来可以引导用户输入密码，然后调用PwdRegCommit 进行注册的最后一步*/
//        }
//
//        @Override
//        public void OnPwdRegCommitSuccess(TLSUserInfo userInfo) {
//      /* 最终注册成功，接下来可以引导用户进行密码登录了，登录流程请查看相应章节*/
//        }
//
//        @Override
//        public void OnPwdRegFail(TLSErrInfo tlsErrInfo) {
//       /* 密码注册过程中任意一步都可以到达这里，可以根据tlsErrInfo 中ErrCode, Title, Msg 给用户弹提示语，引导相关操作*/
//        }
//
//        @Override
//        public void OnPwdRegTimeout(TLSErrInfo tlsErrInfo) {
//      /* 密码注册过程中任意一步都可以到达这里，顾名思义，网络超时，可能是用户网络环境不稳定，一般让用户重试即可*/
//        }
//    };

    public void verifyImgcode(String imageCode) {
        loginHelper.TLSPwdLoginVerifyImgcode(imageCode, new TLSPwdLoginListener() {
            @Override
            public void OnPwdLoginSuccess(TLSUserInfo tlsUserInfo) {

            }

            @Override
            public void OnPwdLoginReaskImgcodeSuccess(byte[] bytes) {

            }

            @Override
            public void OnPwdLoginNeedImgcode(byte[] bytes, TLSErrInfo tlsErrInfo) {

            }

            @Override
            public void OnPwdLoginFail(TLSErrInfo tlsErrInfo) {

            }

            @Override
            public void OnPwdLoginTimeout(TLSErrInfo tlsErrInfo) {

            }
        });
    }

    public void getSin(String userPhone, String password) {
        if (isLogin) return;

        if (!userPhone.startsWith("86-")) {
            if (RegExpUtils.isChinaPhoneLegal(userPhone)) {
                userPhone = "86-" + userPhone;
            }
        }

        loginHelper.TLSPwdLogin(userPhone, password.getBytes(), new TLSPwdLoginListener() {
            @Override
            public void OnPwdLoginSuccess(TLSUserInfo tlsUserInfo) {
                userSig = loginHelper.getUserSig(tlsUserInfo.identifier);
                identifier = tlsUserInfo.identifier;
                login();
                Log.e(TAG, "OnPwdLoginSuccess: " + tlsUserInfo.identifier);
            }

            @Override
            public void OnPwdLoginReaskImgcodeSuccess(byte[] bytes) {
                Log.e(TAG, "OnPwdLoginReaskImgcodeSuccess: " + bytes);
            }

            @Override
            public void OnPwdLoginNeedImgcode(byte[] bytes, TLSErrInfo tlsErrInfo) {
                Log.e(TAG, "OnPwdLoginNeedImgcode: " + tlsErrInfo.Msg);
            }

            @Override
            public void OnPwdLoginFail(TLSErrInfo tlsErrInfo) {
                Log.e(TAG, "OnPwdLoginFail: " + tlsErrInfo.Msg);
            }

            @Override
            public void OnPwdLoginTimeout(TLSErrInfo tlsErrInfo) {
                Log.e(TAG, "OnPwdLoginTimeout: " + tlsErrInfo.Msg);

            }
        });
    }

//    pwdLoginListener = new TLSPwdLoginListener() {
//        @Override
//        public void OnPwdLoginSuccess(TLSUserInfo userInfo) {
//          /* 登录成功了，在这里可以获取用户票据*/
//            String usersig = loginHelper.getUserSig(userInfo.identifier);
//        }
//
//        @Override
//        public void OnPwdLoginReaskImgcodeSuccess(byte[] picData) {
//          /* 请求刷新图片验证码成功，此时需要用picData 更新验证码图片，原先的验证码已经失效*/
//        }
//
//        @Override
//        public void OnPwdLoginNeedImgcode(byte[] picData, TLSErrInfo errInfo) {
//          /* 用户需要进行图片验证码的验证，需要把验证码图片展示给用户，并引导用户输入；如果验证码输入错误，仍然会到达此回调并更新图片验证码*/
//        }
//
//        @Override
//        public void OnPwdLoginFail(TLSErrInfo errInfo) {
//          /* 登录失败，比如说密码错误，用户帐号不存在等，通过errInfo.ErrCode, errInfo.Title, errInfo.Msg等可以得到更具体的错误信息*/
//        }
//        @Override
//        public void OnPwdLoginTimeout(TLSErrInfo errInfo) {
//          /* 密码登录过程中任意一步都可以到达这里，顾名思义，网络超时，可能是用户网络环境不稳定，一般让用户重试即可*/
//        }
//    };

    public void setHeader(String headUrl) {
        TIMFriendshipManager.getInstance().setFaceUrl(headUrl, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                Log.e(TAG, "setFaceUrl failed: " + code + " desc" + desc);
            }

            @Override
            public void onSuccess() {
                Log.e(TAG, "setFaceUrl success");
            }
        });
    }

    public void getSelfProfile(TIMValueCallBack<TIMUserProfile> userProfileTIMValueCallBack) {
        //获取自己的资料
        TIMFriendshipManager.getInstance().getSelfProfile(userProfileTIMValueCallBack);
//        new TIMValueCallBack<TIMUserProfile>() {
//            @Override
//            public void onError(int code, String desc) {
//                //错误码code和错误描述desc，可用于定位请求失败原因
//                //错误码code列表请参见错误码表
//                Log.e(TAG, "getSelfProfile failed: " + code + " desc");
//            }
//
//            @Override
//            public void onSuccess(TIMUserProfile result) {
//                Log.e(TAG, "getSelfProfile success");
//                Log.e(TAG, "identifier: " + result.getIdentifier() + " nickName: " + result.getNickName()
//                        + " remark: " + result.getRemark() + " allow: " + result.getAllowType());
//            }
//        });
    }


    public void getUsersProfile(String... userIds) {
        //待获取用户资料的用户列表
        List<String> users = new ArrayList<String>();
        for (String userId : userIds) {
            users.add(userId);
        }

        //获取用户资料
        TIMFriendshipManager.getInstance().getUsersProfile(users, new TIMValueCallBack<List<TIMUserProfile>>() {
            @Override
            public void onError(int code, String desc) {
                Log.e(TAG, "getUsersProfile failed: " + code + " desc");
            }

            @Override
            public void onSuccess(List<TIMUserProfile> result) {
                Log.e(TAG, "getUsersProfile success");
                for (TIMUserProfile res : result) {
                    Log.e(TAG, "identifier: " + res.getIdentifier() + " nickName: " + res.getNickName()
                            + " remark: " + res.getRemark());
                }
            }
        });
    }


    public void getUsersProfile(TIMValueCallBack<List<TIMUserProfile>> timValueCallBack, String... userIds) {
        //待获取用户资料的用户列表
        List<String> users = new ArrayList<String>();
        for (String userId : userIds) {
            users.add(userId);
        }
        //获取用户资料
        TIMFriendshipManager.getInstance().getUsersProfile(users, timValueCallBack);
    }


    public void setSelfSignature(String signature) {

        TIMFriendshipManager.getInstance().setSelfSignature(signature, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                Log.e(TAG, "setNickName failed: " + " desc");
            }

            @Override
            public void onSuccess() {
                Log.e(TAG, "setNickName success");
            }
        });
    }

    public void setSelfSignature(String signature, TIMCallBack timCallBack) {

        TIMFriendshipManager.getInstance().setSelfSignature(signature, timCallBack);
    }

    public void setNikeName(String nikeName) {
        //设置新昵称为cat
        TIMFriendshipManager.getInstance().setNickName(nikeName, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                Log.e(TAG, "setNickName failed: " + code + " desc");
            }

            @Override
            public void onSuccess() {
                Log.e(TAG, "setNickName success");
            }
        });
    }

    public void addFriend() {
        //创建请求列表
        List<TIMAddFriendRequest> reqList = new ArrayList<TIMAddFriendRequest>();

        //添加好友请求
        TIMAddFriendRequest req = new TIMAddFriendRequest();
        req.setAddrSource("DemoApp");
        req.setAddWording("add me");
        req.setIdentifier(identifier);
        req.setRemark("Cat");
        reqList.add(req);

        //申请添加好友
        TIMFriendshipManager.getInstance().addFriend(reqList, new TIMValueCallBack<List<TIMFriendResult>>() {
            @Override
            public void onError(int code, String desc) {
                Log.e(TAG, "addFriend failed: " + code + " desc");
            }

            @Override
            public void onSuccess(List<TIMFriendResult> result) {
                Log.e(TAG, "addFriend success");
                for (TIMFriendResult res : result) {
                    Log.e(TAG, "identifier: " + res.getIdentifer() + " status: " + res.getStatus());
                }
            }
        });
    }

    public void getFriendList() {
        //获取好友列表
        TIMFriendshipManager.getInstance().getFriendList(new TIMValueCallBack<List<TIMUserProfile>>() {
            @Override
            public void onError(int code, String desc) {
                Log.e(TAG, "getFriendList failed: " + code + " desc");
            }

            @Override
            public void onSuccess(List<TIMUserProfile> result) {
                for (TIMUserProfile res : result) {
                    Log.e(TAG, "identifier: " + res.getIdentifier() + " nickName: " + res.getNickName()
                            + " remark: " + res.getRemark());
                }
            }
        });
    }


    public void getFriendList(TIMValueCallBack<List<TIMUserProfile>> timValueCallBack) {
        //获取好友列表
        TIMFriendshipManager.getInstance().getFriendList(timValueCallBack);
    }

    public void addFriendResponse(String userId) {
        TIMFriendAddResponse friendAddResponse = new TIMFriendAddResponse();
        friendAddResponse.setIdentifier(userId);
        friendAddResponse.setType(TIMFriendResponseType.AgreeAndAdd);
        TIMFriendshipManager.getInstance().addFriendResponse(friendAddResponse, new TIMValueCallBack<TIMFriendResult>() {
            @Override
            public void onError(int code, String s) {
                Log.e(TAG, "getFriendList failed: " + code + " desc");
            }

            @Override
            public void onSuccess(TIMFriendResult timFriendResult) {
                Log.e(TAG, "onSuccess: " + timFriendResult);
            }
        });
    }

    public void getMessage(@Nullable TIMMessage message, String peer, String num) {
        TIMConversation conversation = TIMManager.getInstance().getConversation(
                TIMConversationType.C2C,    //会话类型：单聊
                peer);
        conversation.getMessage(Integer.parseInt(num), message, new TIMValueCallBack<List<TIMMessage>>() {
            @Override
            public void onError(int i, String s) {
                Log.e(TAG, "get message error" + s);
            }

            @Override
            public void onSuccess(List<TIMMessage> timMessages) {

            }
        });
    }

    /**
     * action
     */
    public static class Action {
        public static class ActionStateGetMessage {
            public String code;
            public int state;

            public ActionStateGetMessage(String code, int state) {
                this.code = code;
                this.state = state;
            }
        }

        public static class ActionCodeVerify {
            public int state;

            public ActionCodeVerify(int state) {
                this.state = state;
            }
        }

        public static class ActionPWDCommit {
            public int state;
            public String imUserName;

            public ActionPWDCommit(String imUserName, int state) {
                this.state = state;
                this.imUserName = imUserName;
            }
        }

        public static class ActionGetMessage {

        }

        public static class ActionGetChatMessage {
            public String identifer;

            public ActionGetChatMessage(String identifer) {
                this.identifer = identifer;
            }
        }

        public static class ActionImLogin {

        }
    }

}

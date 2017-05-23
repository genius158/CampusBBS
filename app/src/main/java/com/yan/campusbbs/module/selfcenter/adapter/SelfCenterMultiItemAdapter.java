package com.yan.campusbbs.module.selfcenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.tencent.TIMCallBack;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;
import com.yan.campusbbs.R;
import com.yan.campusbbs.config.CacheConfig;
import com.yan.campusbbs.module.ImManager;
import com.yan.campusbbs.module.campusbbs.api.UserInfo;
import com.yan.campusbbs.module.campusbbs.data.TopicData;
import com.yan.campusbbs.module.campusbbs.data.TopicDetailData;
import com.yan.campusbbs.module.campusbbs.ui.common.topicdetail.TopicDetailActivity;
import com.yan.campusbbs.module.campusbbs.ui.selfcenter.SelfCenterActivity;
import com.yan.campusbbs.module.campusbbs.ui.selfcenter.chat.ChatActivity;
import com.yan.campusbbs.module.campusbbs.ui.userinfo.UserInfoActivity;
import com.yan.campusbbs.module.common.pop.PopPhotoView;
import com.yan.campusbbs.module.selfcenter.data.LoginInfoData;
import com.yan.campusbbs.module.selfcenter.data.PublishData;
import com.yan.campusbbs.module.selfcenter.data.UserInfoData;
import com.yan.campusbbs.module.selfcenter.ui.friendpage.FriendPageActivity;
import com.yan.campusbbs.module.selfcenter.ui.selfmore.SelfMainPageMoreActivity;
import com.yan.campusbbs.repository.DataAddress;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.utils.ACache;
import com.yan.campusbbs.utils.AppRetrofit;
import com.yan.campusbbs.utils.EmptyUtil;
import com.yan.campusbbs.utils.FrescoUtils;
import com.yan.campusbbs.utils.RegExpUtils;
import com.yan.campusbbs.utils.RxBus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by yan on 2017/2/7.
 */
public class SelfCenterMultiItemAdapter extends BaseMultiItemQuickAdapter<DataMultiItem, BaseViewHolder> {

    public static final int ITEM_TYPE_SELF_HEADER = 1;
    public static final int ITEM_TYPE_SELF_DYNAMIC = 2;
    public static final int ITEM_TYPE_FRIEND_TITLE = 3;
    public static final int ITEM_TYPE_FRIEND_DYNAMIC = 4;

    public static final int ITEM_TYPE_OTHER_HEADER = 5;
    public static final int ITEM_TYPE_DETAIL_TOPIC = 6;

    private Context context;

    private PopPhotoView popPhotoView;

    private TextView tvSign;
    private String selfSignature;
    private String signStrOther;

    private CompositeDisposable compositeDisposable;
    private RxBus rxBus;
    private Retrofit retrofit;

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public void setPopPhotoView(PopPhotoView popPhotoView) {
        this.popPhotoView = popPhotoView;
    }

    @Inject
    public SelfCenterMultiItemAdapter(List<DataMultiItem> data, Context context, CompositeDisposable compositeDisposable, RxBus rxBus) {
        super(data);
        addItemType(ITEM_TYPE_SELF_HEADER, R.layout.fragment_self_center_bg_header_sign);
        addItemType(ITEM_TYPE_OTHER_HEADER, R.layout.fragment_self_center_other_bg_header_sign);
        addItemType(ITEM_TYPE_SELF_DYNAMIC, R.layout.fragment_self_center_self_dynamic_item);
        addItemType(ITEM_TYPE_FRIEND_TITLE, R.layout.fragment_self_center_friend_dynamic_title);
        addItemType(ITEM_TYPE_FRIEND_DYNAMIC, R.layout.fragment_self_center_friend_dynamic_item);
        addItemType(ITEM_TYPE_DETAIL_TOPIC, R.layout.fragment_self_center_friend_dynamic_item);
        this.context = context;
        this.rxBus = rxBus;
        this.compositeDisposable = compositeDisposable;

        initRxAction();
    }

    private void initRxAction() {
        compositeDisposable.add(rxBus.getEvent(ImManager.Action.ActionImLogin.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(actionImLogin -> {
                    setSign();
                }));
    }

    @Override
    protected void convert(BaseViewHolder holder, DataMultiItem multiItem) {
        switch (holder.getItemViewType()) {
            case ITEM_TYPE_SELF_HEADER:
                SimpleDraweeView selfImg = holder.getView(R.id.self_part_one_img);
                selfImg.setAspectRatio(1.50f);
                if (multiItem.data instanceof LoginInfoData) {
                    LoginInfoData loginInfoData = (LoginInfoData) multiItem.data;
                    holder.setText(R.id.tv_nike_name, TextUtils.isEmpty(loginInfoData.getData().getUserInfo().getUserNickname())
                            ? loginInfoData.getData().getUserInfo().getUserAccount()
                            : loginInfoData.getData().getUserInfo().getUserNickname());
                    holder.setText(R.id.tv_plus, "等级:" + loginInfoData.getData().getUserInfo().getUserRank());

                    tvSign = holder.getView(R.id.tv_sign);
                    if (!TextUtils.isEmpty(selfSignature)) {
                        tvSign.setText(selfSignature);
                    }

                    FrescoUtils.display(holder.getView(R.id.self_part_one_header)
                            , String.valueOf(DataAddress.URL_GET_FILE + loginInfoData.getData().getUserInfo().getUserHeadImg()));

                    FrescoUtils.display(selfImg, DataAddress.URL_GET_FILE + loginInfoData.getData().getUserInfo().getUserHeadImg());
                    selfImg.setOnClickListener(v -> {
                        if (popPhotoView != null) {
                            popPhotoView.show();
                            popPhotoView.setImageUrl(
                                    DataAddress.URL_GET_FILE + String.valueOf(loginInfoData.getData().getUserInfo().getUserHeadImg()));
                        }
                    });
                }

                holder.getView(R.id.self_part_one_header)
                        .setOnClickListener(v -> {
                            context.startActivity(new Intent(context, SelfCenterActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        });
                holder.getView(R.id.tv_more)
                        .setOnClickListener(v -> {
                            context.startActivity(new Intent(context, SelfMainPageMoreActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        });

                ((EditText) holder.getView(R.id.fragment_self_center_sign)).setOnEditorActionListener(onEditorActionListener);

                break;
            case ITEM_TYPE_OTHER_HEADER:
                SimpleDraweeView otherSDV = holder.getView(R.id.other_part_one_img);
                otherSDV.setAspectRatio(1.50f);
                if (multiItem.data instanceof UserInfoData) {
                    UserInfoData otherUserInfo = (UserInfoData) multiItem.data;

                    if (otherUserInfo.getData() == null
                            || otherUserInfo.getData().getUserDetailInfo() == null) return;

                    holder.setText(R.id.tv_nike_name, otherUserInfo.getData().getUserDetailInfo().getUserNickname());
                    holder.setText(R.id.tv_plus, "等级:" + otherUserInfo.getData().getUserDetailInfo().getUserRank());

                    FrescoUtils.display(holder.getView(R.id.other_part_one_header)
                            , DataAddress.URL_GET_FILE + otherUserInfo.getData().getUserDetailInfo().getUserHeadImg());

                    FrescoUtils.display(otherSDV
                            , DataAddress.URL_GET_FILE + otherUserInfo.getData().getUserDetailInfo().getUserHeadImg());

                    holder.getView(R.id.other_part_one_header).setOnClickListener(v -> {
                        context.startActivity(new Intent(context, UserInfoActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                .putExtra("userId", otherUserInfo.getData().getUserDetailInfo().getUserId()));
                    });
                    /*
                     *聊天
                     */
                    holder.getView(R.id.iv_btn_chat)
                            .setOnClickListener(v -> {
                                context.startActivity(new Intent(context, ChatActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                        .putExtra("identifier", otherUserInfo.getData().getUserDetailInfo().getUserAccount())
                                );
                            });
                    holder.getView(R.id.tv_btn_chat)
                            .setOnClickListener(v -> {
                                context.startActivity(new Intent(context, ChatActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                        .putExtra("identifier", otherUserInfo.getData().getUserDetailInfo().getUserAccount())
                                );
                            });

                    /*
                     *加好友
                     */
                    String identifier = otherUserInfo.getData().getUserDetailInfo().getUserAccount();
                    if (!identifier.startsWith("86-")) {
                        if (RegExpUtils.isChinaPhoneLegal(identifier)) {
                            identifier = "86-" + identifier;
                        }
                    }
                    String finalIdentifier = identifier;
                    holder.getView(R.id.iv_btn_add_friend)
                            .setOnClickListener(v -> {
                                ImManager.getImManager().addFriend(finalIdentifier);
                                UserInfo userInfo = retrofit.create(UserInfo.class);
                                userInfo.addFriend(otherUserInfo.getData().getUserDetailInfo().getUserId())
                                        .subscribeOn(Schedulers.io())
                                        .subscribe(userInfoData -> {
                                        });
                            });
                    holder.getView(R.id.tv_btn_add_friend)
                            .setOnClickListener(v -> {
                                ImManager.getImManager().addFriend(finalIdentifier);
                                UserInfo userInfo = retrofit.create(UserInfo.class);
                                userInfo.addFriend(otherUserInfo.getData().getUserDetailInfo().getUserId())
                                        .subscribeOn(Schedulers.io())
                                        .subscribe(userInfoData -> {
                                        });
                            });
                    setSignOther(holder.getView(R.id.tv_sign), finalIdentifier);
                }
                otherSDV.setOnClickListener(v -> {
                    if (popPhotoView != null) {
                        popPhotoView.show();
                        popPhotoView.setImageUrl(String.valueOf(multiItem.data));
                    }
                });

                break;

            case ITEM_TYPE_FRIEND_TITLE:

                break;
            case ITEM_TYPE_SELF_DYNAMIC:
                PublishData.DataBean.TopicInfoListBean.TopicListBean selfBean =
                        (PublishData.DataBean.TopicInfoListBean.TopicListBean) multiItem.data;

                SimpleDraweeView selfSimpleDrawee = holder.getView(R.id.self_part_one_img);
                holder.setText(R.id.message_detail, selfBean.getTopicTitle());
                holder.setText(R.id.self_dynamic, selfBean.getTopicContent());
                holder.setText(R.id.tv_brown_count, "浏览(" + EmptyUtil.numObjectEmpty(selfBean.getBrowseCount()) + ")");

                if (!TextUtils.isEmpty(selfBean.getFileImage())) {
                    selfSimpleDrawee.setVisibility(View.VISIBLE);
                    FrescoUtils.adjustViewOnImage(context, selfSimpleDrawee
                            , DataAddress.URL_GET_FILE + selfBean.getFileImage());
                } else {
                    selfSimpleDrawee.setVisibility(View.GONE);
                }

                holder.getView(R.id.container).setOnClickListener(v -> {
                    context.startActivity(new Intent(context, TopicDetailActivity.class)
                            .putExtra("title", selfBean.getTopicTitle())
                            .putExtra("topicId", selfBean.getTopicId())
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    );
                });
                break;
            case ITEM_TYPE_FRIEND_DYNAMIC:
                if (multiItem.data instanceof PublishData.DataBean.TopicInfoListBean.TopicListBean) {
                    PublishData.DataBean.TopicInfoListBean.TopicListBean otherBean =
                            (PublishData.DataBean.TopicInfoListBean.TopicListBean) multiItem.data;
                    SimpleDraweeView simpleDrawee = holder.getView(R.id.self_part_one_img);
                    SimpleDraweeView head = holder.getView(R.id.sdv_head);
                    head.setImageURI(String.valueOf(DataAddress.URL_GET_FILE + otherBean.getUserHeadImg()));
                    head.setOnClickListener(v -> {
                        context.startActivity(new Intent(context, FriendPageActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                .putExtra("userId", otherBean.getUserId())
                                .putExtra("otherBean", otherBean)
                        );
                    });
                    holder.setText(R.id.user_name, otherBean.getUserNickname());
                    holder.setText(R.id.self_dynamic, otherBean.getTopicTitle());
                    holder.setText(R.id.tv_brown_count, "浏览(" + EmptyUtil.numObjectEmpty(otherBean.getBrowseCount()) + ")");

                    if (!TextUtils.isEmpty(otherBean.getFileImage())) {
                        simpleDrawee.setVisibility(View.VISIBLE);
                        FrescoUtils.adjustViewOnImage(context, simpleDrawee
                                , DataAddress.URL_GET_FILE + otherBean.getFileImage());
                    } else {
                        simpleDrawee.setVisibility(View.GONE);
                    }

                    holder.getView(R.id.container).setOnClickListener(v -> {
                        context.startActivity(new Intent(context, TopicDetailActivity.class)
                                .putExtra("title", otherBean.getTopicTitle())
                                .putExtra("topicId", otherBean.getTopicId())
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        );
                    });

                } else {
                    TopicData.DataBean.TopicInfoListBean.TopicListBean otherBean =
                            (TopicData.DataBean.TopicInfoListBean.TopicListBean) multiItem.data;
                    SimpleDraweeView simpleDrawee = holder.getView(R.id.self_part_one_img);
                    SimpleDraweeView head = holder.getView(R.id.sdv_head);
                    head.setImageURI(String.valueOf(DataAddress.URL_GET_FILE + otherBean.getUserHeadImg()));
                    head.setOnClickListener(v -> {
                        context.startActivity(new Intent(context, FriendPageActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                .putExtra("userId", otherBean.getUserId())
                                .putExtra("nickName", TextUtils.isEmpty(otherBean.getUserNickname())
                                        ? otherBean.getUserAccount()
                                        : otherBean.getUserNickname())
                        );
                    });
                    holder.setText(R.id.user_name, otherBean.getUserNickname());
                    holder.setText(R.id.self_dynamic, otherBean.getTopicTitle());
                    holder.setText(R.id.tv_brown_count, "浏览(" + EmptyUtil.numObjectEmpty(otherBean.getBrowseCount()) + ")");
                    FrescoUtils.adjustViewOnImage(context, simpleDrawee, DataAddress.URL_GET_FILE + otherBean.getUserHeadImg());
                    holder.getView(R.id.container).setOnClickListener(v -> {
                        context.startActivity(new Intent(context, TopicDetailActivity.class)
                                .putExtra("title", otherBean.getTopicTitle())
                                .putExtra("topicId", otherBean.getTopicId())
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        );
                    });
                }

                break;
            case ITEM_TYPE_DETAIL_TOPIC:
                TopicDetailData.DataBean.TopicDetailInfoBean infoBean =
                        (TopicDetailData.DataBean.TopicDetailInfoBean) multiItem.data;

                SimpleDraweeView simpleDrawee2 = holder.getView(R.id.self_part_one_img);
                SimpleDraweeView head2 = holder.getView(R.id.sdv_head);
                head2.setImageURI(String.valueOf(DataAddress.URL_GET_FILE + infoBean.getUserTopicInfo().getUserHeadImg()));
                head2.setOnClickListener(v -> {
                    context.startActivity(new Intent(context, FriendPageActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .putExtra("userId", infoBean.getUserTopicInfo().getUserId())
                            .putExtra("nickName", TextUtils.isEmpty(infoBean.getUserTopicInfo().getUserNickname())
                                    ? infoBean.getUserTopicInfo().getUserAccount()
                                    : infoBean.getUserTopicInfo().getUserNickname())
                    );
                });
                holder.setText(R.id.user_name, infoBean.getUserTopicInfo().getUserNickname());
                holder.setText(R.id.self_dynamic, infoBean.getUserTopicInfo().getTopicTitle());
                holder.setText(R.id.tv_brown_count, "浏览(" + EmptyUtil.numObjectEmpty(infoBean.getUserTopicInfo().getBrowseCount()) + ")");

                holder.getView(R.id.container).setOnClickListener(v -> {
                    context.startActivity(new Intent(context, TopicDetailActivity.class)
                            .putExtra("title", infoBean.getUserTopicInfo().getTopicTitle())
                            .putExtra("topicId", infoBean.getUserTopicInfo().getTopicId())
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    );
                });
                if (infoBean.getFileList() != null
                        && !infoBean.getFileList().isEmpty()
                        && !TextUtils.isEmpty(infoBean.getFileList().get(0).getFileImage())
                        ) {
                    FrescoUtils.adjustViewOnImage(context, simpleDrawee2
                            , DataAddress.URL_GET_FILE + infoBean.getFileList().get(0).getFileImage());
                }

                break;
        }
    }

    private void setSign() {
        ImManager.getImManager().getSelfProfile(
                new TIMValueCallBack<TIMUserProfile>() {
                    @Override
                    public void onError(int code, String desc) {
                        Log.e(TAG, "getSelfProfile failed: " + code + " desc");
                    }

                    @Override
                    public void onSuccess(TIMUserProfile result) {
                        if (TextUtils.isEmpty(result.getSelfSignature())) {
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("userId", ACache.get(context).getAsString(CacheConfig.USER_ID));
                                jsonObject.put("signature", "默认签名");
                                ImManager.getImManager().setSelfSignature(jsonObject.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            return;
                        }
                        boolean isJson;
                        try {
                            new JsonParser().parse(result.getSelfSignature());
                            isJson = true;
                        } catch (JsonParseException e) {
                            isJson = false;
                        }
                        if (isJson) {
                            if (tvSign != null) {
                                try {
                                    JSONObject jsonObject = new JSONObject(result.getSelfSignature());
                                    selfSignature = jsonObject.getString("signature");
                                    tvSign.setText(selfSignature);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
        );
    }

    private TextView.OnEditorActionListener onEditorActionListener = (v, actionId, event) -> {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            if (!TextUtils.isEmpty(v.getText())) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("userId", ACache.get(context).getAsString(CacheConfig.USER_ID));
                    jsonObject.put("signature", v.getText().toString());
                    selfSignature = v.getText().toString();
                    ImManager.getImManager().setSelfSignature(jsonObject.toString(), new TIMCallBack() {
                        @Override
                        public void onError(int i, String s) {
                            Log.e(TAG, "onError: ");
                        }

                        @Override
                        public void onSuccess() {
                            if (tvSign != null) {
                                tvSign.setText(selfSignature);
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        return false;
    };

    public void setSignOther(TextView signOther, String finalIdentifier) {
        if (!TextUtils.isEmpty(signStrOther)) {
            signOther.setText(signStrOther);
            return;
        }
        ImManager.getImManager().getUsersProfile(
                new TIMValueCallBack<List<TIMUserProfile>>() {
                    @Override
                    public void onError(int code, String s) {
                        Log.e(TAG, "getSelfProfile failed: " + code + "    " + s);
                    }

                    @Override
                    public void onSuccess(List<TIMUserProfile> timUserProfiles) {
                        for (TIMUserProfile userProfile : timUserProfiles) {
                            boolean isJson;
                            try {
                                new JsonParser().parse(userProfile.getSelfSignature());
                                isJson = true;
                            } catch (JsonParseException e) {
                                isJson = false;
                            }
                            if (isJson) {
                                try {
                                    JSONObject jsonObject = new JSONObject(userProfile.getSelfSignature());
                                    signStrOther = jsonObject.getString("signature");
                                    signOther.setText(signStrOther);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
                , finalIdentifier
        );
    }
}

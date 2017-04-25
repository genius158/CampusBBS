package com.yan.campusbbs.module.selfcenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tencent.TIMCallBack;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;
import com.yan.campusbbs.R;
import com.yan.campusbbs.module.ImManager;
import com.yan.campusbbs.module.campusbbs.data.TopicData;
import com.yan.campusbbs.module.campusbbs.data.TopicDetailData;
import com.yan.campusbbs.module.campusbbs.ui.common.topicdetail.TopicDetailActivity;
import com.yan.campusbbs.module.campusbbs.ui.selfcenter.SelfCenterActivity;
import com.yan.campusbbs.module.campusbbs.ui.selfcenter.chat.ChatActivity;
import com.yan.campusbbs.module.common.pop.PopPhotoView;
import com.yan.campusbbs.module.selfcenter.data.LoginInfoData;
import com.yan.campusbbs.module.selfcenter.data.PublishData;
import com.yan.campusbbs.module.selfcenter.data.UserInfoData;
import com.yan.campusbbs.module.selfcenter.ui.friendpage.FriendPageActivity;
import com.yan.campusbbs.module.selfcenter.ui.selfmore.SelfMainPageMoreActivity;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.util.EmptyUtil;
import com.yan.campusbbs.util.FrescoUtils;
import com.yan.campusbbs.util.RegExpUtils;
import com.yan.campusbbs.util.RxBus;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

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
    private String signStr;
    private String signStrOther;

    private CompositeDisposable compositeDisposable;
    private RxBus rxBus;

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
                    if (!TextUtils.isEmpty(signStr)) {
                        tvSign.setText(signStr);
                    }

                    FrescoUtils.display(holder.getView(R.id.self_part_one_header)
                            , String.valueOf(loginInfoData.getData().getUserInfo().getUserHeadImg()));

                    FrescoUtils.display(selfImg, loginInfoData.getData().getUserInfo().getUserHeadImg());
                    selfImg.setOnClickListener(v -> {
                        if (popPhotoView != null) {
                            popPhotoView.show();
                            popPhotoView.setImageUrl(String.valueOf(loginInfoData.getData().getUserInfo().getUserHeadImg()));
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
                    UserInfoData loginInfoData = (UserInfoData) multiItem.data;

                    if (loginInfoData.getData() == null
                            || loginInfoData.getData().getUserDetailInfo() == null) return;

                    holder.setText(R.id.tv_nike_name, loginInfoData.getData().getUserDetailInfo().getUserNickname());
                    holder.setText(R.id.tv_plus, "等级:" + loginInfoData.getData().getUserDetailInfo().getUserRank());

                    FrescoUtils.display(holder.getView(R.id.other_part_one_header)
                            , loginInfoData.getData().getUserDetailInfo().getUserHeadImg());

                    FrescoUtils.display(otherSDV
                            , loginInfoData.getData().getUserDetailInfo().getUserHeadImg());

                    /*
                     *聊天
                     */
                    holder.getView(R.id.iv_btn_chat)
                            .setOnClickListener(v -> {
                                context.startActivity(new Intent(context, ChatActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        .putExtra("identifier", loginInfoData.getData().getUserDetailInfo().getUserAccount())
                                );
                            });
                    holder.getView(R.id.tv_btn_chat)
                            .setOnClickListener(v -> {
                                context.startActivity(new Intent(context, ChatActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        .putExtra("identifier", loginInfoData.getData().getUserDetailInfo().getUserAccount())
                                );
                            });

                    /*
                     *加好友
                     */
                    String identifier = loginInfoData.getData().getUserDetailInfo().getUserAccount();
                    if (!identifier.startsWith("86-")) {
                        if (RegExpUtils.isChinaPhoneLegal(identifier)) {
                            identifier = "86-" + identifier;
                        }
                    }
                    String finalIdentifier = identifier;
                    ImManager.getImManager().getFriendList(new TIMValueCallBack<List<TIMUserProfile>>() {
                        @Override
                        public void onError(int i, String s) {
                            Log.e(TAG, "onError: " + s);
                        }

                        @Override
                        public void onSuccess(List<TIMUserProfile> timUserProfiles) {
                            boolean isNeedShow = true;
                            for (TIMUserProfile userProfile : timUserProfiles) {

                                if (finalIdentifier.equals(userProfile.getIdentifier())) {
                                    holder.setVisible(R.id.iv_btn_add_friend, false);
                                    holder.setVisible(R.id.tv_btn_add_friend, false);
                                    isNeedShow = false;
                                    break;
                                }
                            }
                            if (isNeedShow) {
                                holder.setVisible(R.id.iv_btn_add_friend, true);
                                holder.setVisible(R.id.tv_btn_add_friend, true);
                            }
                        }
                    });
                    holder.getView(R.id.iv_btn_add_friend)
                            .setOnClickListener(v -> {
                                ImManager.getImManager().addFriend(finalIdentifier);
                            });
                    holder.getView(R.id.tv_btn_add_friend)
                            .setOnClickListener(v -> {
                                ImManager.getImManager().addFriend(finalIdentifier);
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
                FrescoUtils.adjustViewOnImage(context, selfSimpleDrawee, selfBean.getUserHeadImg());
                holder.getView(R.id.container).setOnClickListener(v -> {
                    context.startActivity(new Intent(context, TopicDetailActivity.class)
                            .putExtra("title", selfBean.getTopicTitle())
                            .putExtra("topicId", selfBean.getTopicId())
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    );
                });

                selfSimpleDrawee.setOnClickListener(v -> {
                    if (popPhotoView != null) {
                        popPhotoView.show();
                        popPhotoView.setImageUrl(selfBean.getUserHeadImg());
                    }
                });


                break;
            case ITEM_TYPE_FRIEND_DYNAMIC:
                if (multiItem.data instanceof PublishData.DataBean.TopicInfoListBean.TopicListBean) {
                    PublishData.DataBean.TopicInfoListBean.TopicListBean otherBean =
                            (PublishData.DataBean.TopicInfoListBean.TopicListBean) multiItem.data;
                    SimpleDraweeView simpleDrawee = holder.getView(R.id.self_part_one_img);
                    SimpleDraweeView head = holder.getView(R.id.sdv_head);
                    head.setImageURI(String.valueOf(otherBean.getUserHeadImg()));
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
                    FrescoUtils.adjustViewOnImage(context, simpleDrawee, otherBean.getUserHeadImg());
                    holder.getView(R.id.container).setOnClickListener(v -> {
                        context.startActivity(new Intent(context, TopicDetailActivity.class)
                                .putExtra("title", otherBean.getTopicTitle())
                                .putExtra("topicId", otherBean.getTopicId())
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        );
                    });
                    simpleDrawee.setOnClickListener(v -> {
                        if (popPhotoView != null) {
                            popPhotoView.show();
                            popPhotoView.setImageUrl(otherBean.getUserHeadImg());
                        }
                    });
                } else {
                    TopicData.DataBean.TopicInfoListBean.TopicListBean otherBean =
                            (TopicData.DataBean.TopicInfoListBean.TopicListBean) multiItem.data;
                    SimpleDraweeView simpleDrawee = holder.getView(R.id.self_part_one_img);
                    SimpleDraweeView head = holder.getView(R.id.sdv_head);
                    head.setImageURI(String.valueOf(otherBean.getUserHeadImg()));
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
                    FrescoUtils.adjustViewOnImage(context, simpleDrawee, otherBean.getUserHeadImg());
                    holder.getView(R.id.container).setOnClickListener(v -> {
                        context.startActivity(new Intent(context, TopicDetailActivity.class)
                                .putExtra("title", otherBean.getTopicTitle())
                                .putExtra("topicId", otherBean.getTopicId())
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        );
                    });
                    simpleDrawee.setOnClickListener(v -> {
                        if (popPhotoView != null) {
                            popPhotoView.show();
                            popPhotoView.setImageUrl(otherBean.getUserHeadImg());
                        }
                    });
                }

                break;
            case ITEM_TYPE_DETAIL_TOPIC:
                TopicDetailData.DataBean.TopicDetailInfoBean.UserTopicInfoBean infoBean =
                        (TopicDetailData.DataBean.TopicDetailInfoBean.UserTopicInfoBean) multiItem.data;

                SimpleDraweeView simpleDrawee2 = holder.getView(R.id.self_part_one_img);
                SimpleDraweeView head2 = holder.getView(R.id.sdv_head);
                head2.setImageURI(String.valueOf(infoBean.getUserHeadImg()));
                head2.setOnClickListener(v -> {
                    context.startActivity(new Intent(context, FriendPageActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .putExtra("userId", infoBean.getUserId())
                            .putExtra("nickName", TextUtils.isEmpty(infoBean.getUserNickname())
                                    ? infoBean.getUserAccount()
                                    : infoBean.getUserNickname())
                    );
                });
                holder.setText(R.id.user_name, infoBean.getUserNickname());
                holder.setText(R.id.self_dynamic, infoBean.getTopicTitle());
                holder.setText(R.id.tv_brown_count, "浏览(" + EmptyUtil.numObjectEmpty(infoBean.getBrowseCount()) + ")");
                FrescoUtils.adjustViewOnImage(context, simpleDrawee2, infoBean.getUserHeadImg());
                holder.getView(R.id.container).setOnClickListener(v -> {
                    context.startActivity(new Intent(context, TopicDetailActivity.class)
                            .putExtra("title", infoBean.getTopicTitle())
                            .putExtra("topicId", infoBean.getTopicId())
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    );
                });
                simpleDrawee2.setOnClickListener(v -> {
                    if (popPhotoView != null) {
                        popPhotoView.show();
                        popPhotoView.setImageUrl(infoBean.getUserHeadImg());
                    }
                });
                break;
        }
    }

    private void setSign() {
        if (!TextUtils.isEmpty(signStr)) {
            tvSign.setText(signStr);
            return;
        }
        ImManager.getImManager().getSelfProfile(
                new TIMValueCallBack<TIMUserProfile>() {
                    @Override
                    public void onError(int code, String desc) {
                        Log.e(TAG, "getSelfProfile failed: " + code + " desc");
                    }

                    @Override
                    public void onSuccess(TIMUserProfile result) {
                        signStr = result.getSelfSignature();
                        if (tvSign != null) {
                            tvSign.setText(signStr);
                        }
                    }
                }
        );
    }

    private TextView.OnEditorActionListener onEditorActionListener = (v, actionId, event) -> {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            if (!TextUtils.isEmpty(v.getText())) {
                signStr = v.getText().toString();
                ImManager.getImManager().setSelfSignature(v.getText().toString(), new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        Log.e(TAG, "onError: ");
                    }

                    @Override
                    public void onSuccess() {
                        if (tvSign != null) {
                            tvSign.setText(signStr);
                        }
                    }
                });
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
                            signStrOther = userProfile.getSelfSignature();
                            signOther.setText(signStrOther);
                        }

                    }
                }
                , finalIdentifier
        );
    }
}

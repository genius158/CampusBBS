package com.yan.campusbbs.module.campusbbs.ui.common.topicdetail;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseActivity;
import com.yan.campusbbs.config.CacheConfig;
import com.yan.campusbbs.module.campusbbs.data.CommentData;
import com.yan.campusbbs.module.campusbbs.data.ReplyCommentData;
import com.yan.campusbbs.module.campusbbs.data.TopicDetailData;
import com.yan.campusbbs.module.campusbbs.data.TopicLikeData;
import com.yan.campusbbs.module.common.data.TopicCacheData;
import com.yan.campusbbs.module.common.pop.PopPhotoView;
import com.yan.campusbbs.module.selfcenter.ui.friendpage.FriendPageActivity;
import com.yan.campusbbs.module.setting.ImageControl;
import com.yan.campusbbs.module.setting.SettingHelper;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.repository.DataAddress;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.utils.ACache;
import com.yan.campusbbs.utils.EmptyUtil;
import com.yan.campusbbs.utils.FrescoUtils;
import com.yan.campusbbs.utils.SPUtils;
import com.yan.campusbbs.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * Created by yan on 2017/4/21.
 */

public class TopicDetailActivity extends BaseActivity implements TopicDetailContract.View {
    private static final String TAG = "TopicDetailActivity";

    @Inject
    SettingHelper changeSkinHelper;
    @Inject
    SPUtils spUtils;
    @Inject
    ToastUtils toastUtils;
    @Inject
    ImageControl imageControl;
    @Inject
    TopicDetailPresenter presenter;

    @BindView(R.id.common_app_bar)
    CardView commonAppBar;
    @BindView(R.id.container)
    View container;
    @BindView(R.id.title)
    TextView tvTitle;
    @BindView(R.id.sdv_head)
    SimpleDraweeView sdvHead;
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.tv_reply_content)
    TextView tvReplyContent;
    @BindView(R.id.tv_topic_title)
    TextView tvTopicTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_like_num)
    TextView tvLikeNum;
    @BindView(R.id.tv_reply_num)
    TextView tvReplyNum;
    @BindView(R.id.tv_re_commit_num)
    TextView tvReCommitNum;
    @BindView(R.id.tv_brown_count)
    TextView tvBrownCount;
    @BindView(R.id.sdv_img)
    SimpleDraweeView sdvImg;
    @BindView(R.id.et_content)
    TextView etContent;
    @BindView(R.id.et_reply)
    EditText etReply;

    private String title;
    private String topicId;

    private TopicDetailData topicDetail;
    private PopPhotoView popPhotoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);
        ButterKnife.bind(this);
        daggerInject();
        imageControl.frescoInit();
        init();
    }

    private void daggerInject() {
        DaggerTopicDetailComponent.builder().applicationComponent(
                ((ApplicationCampusBBS) getApplication()).getApplicationComponent())
                .settingModule(new SettingModule(this, compositeDisposable))
                .topicDetailModule(new TopicDetailModule(this))
                .build().inject(this);
    }

    private void init() {
        title = getIntent().getStringExtra("title");
        topicId = getIntent().getStringExtra("topicId");
        presenter.getTopicDetail(topicId);
        presenter.getReplyList(topicId, "1");
        popPhotoView = new PopPhotoView(container, toastUtils);
    }

    @Override
    protected SPUtils sPUtils() {
        return spUtils;
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        super.changeSkin(actionChangeSkin);
        tvTitle.setText("详情");

        commonAppBar.setCardBackgroundColor(
                ContextCompat.getColor(this, actionChangeSkin.getColorPrimaryId())
        );
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            commonAppBar.setBackgroundColor(
                    ContextCompat.getColor(this, actionChangeSkin.getColorPrimaryId())
            );
        }
    }

    @Override
    public void netError() {
        toastUtils.showUIShort("网络错误");
    }

    @Override
    public void setTopicDetail(TopicDetailData topicDetail) {
        this.topicDetail = topicDetail;
        if (topicDetail.getData() != null
                && topicDetail.getData().getTopicDetailInfo() != null
                && topicDetail.getData().getTopicDetailInfo().getUserTopicInfo() != null
                ) {
            TopicCacheData cacheData = (TopicCacheData) ACache.get(getBaseContext()).getAsObject(CacheConfig.TOPIC_CACHE);
            if (cacheData == null) {
                cacheData = new TopicCacheData(new LinkedHashSet<>());
            }
            cacheData.topicDetailDatas.add(topicDetail.getData().getTopicDetailInfo());
            ACache.get(getBaseContext()).put(CacheConfig.TOPIC_CACHE, cacheData);

            TopicDetailData.DataBean.TopicDetailInfoBean.UserTopicInfoBean detailData =
                    topicDetail.getData().getTopicDetailInfo().getUserTopicInfo();
            if (topicDetail.getData().getTopicDetailInfo().getFileList() != null
                    && !topicDetail.getData().getTopicDetailInfo().getFileList().isEmpty()
                    && !TextUtils.isEmpty(topicDetail.getData().getTopicDetailInfo().getFileList().get(0).getFileImage())
                    ) {
                FrescoUtils.adjustViewOnImage(getBaseContext(), sdvImg
                        , DataAddress.URL_GET_FILE + topicDetail.getData().getTopicDetailInfo().getFileList().get(0).getFileImage());

                sdvImg.setOnClickListener(v -> {
                    if (popPhotoView != null) {
                        popPhotoView.show();
                        popPhotoView.setImageUrl(DataAddress.URL_GET_FILE
                                + topicDetail.getData().getTopicDetailInfo().getFileList().get(0).getFileImage());
                    }
                });
            }
            sdvHead.setImageURI(DataAddress.URL_GET_FILE +detailData.getUserHeadImg());
            tvNickName.setText(TextUtils.isEmpty(detailData.getUserNickname())
                    ? detailData.getUserAccount()
                    : detailData.getUserNickname());
            tvTime.setText(String.valueOf("发表于 : " + detailData.getTopicReleaseTime()));
            tvLikeNum.setText(String.valueOf("(" + EmptyUtil.numObjectEmpty(detailData.getLikeCount()) + ")"));
//            tvReCommitNum.setText(String.valueOf("(" + EmptyUtil.numObjectEmpty(detailData.getCmtCount()) + ")"));
            tvBrownCount.setText(String.valueOf("浏览(" + EmptyUtil.numObjectEmpty(detailData.getBrowseCount()) + ")"));
            etContent.setText(detailData.getTopicContent());
            tvTopicTitle.setText(detailData.getTopicTitle());
//            tvReplyContent.setText("阿傻 : 傻里傻气"
//                    + "\n" + "阿三 : 阿傻说的很对"
//
//            );
        } else {
            toastUtils.showUIShort(topicDetail.getMessage());
        }
    }

    @Override
    public void setReplyList(CommentData replyList) {
        if (replyList.getData() != null
                && replyList.getData().getCommentInfoList() != null
                && replyList.getData().getCommentInfoList().getCommentList() != null
                ) {

            StringBuilder stringBuilder = new StringBuilder();
            for (CommentData.DataBean.CommentInfoListBean.CommentListBean bean :
                    replyList.getData().getCommentInfoList().getCommentList())
                stringBuilder.append(bean.getUserNickname() == null ? bean.getUserAccount() : bean.getUserNickname())
                        .append(" : ")
                        .append(bean.getCmtContent())
                        .append("\n");
            tvReplyContent.setText(stringBuilder.toString());
            tvReplyNum.setText(String.valueOf("(" + replyList.getData().getCommentInfoList().getTotalCount() + ")"));
        }
    }

    @Override
    public void reply(ReplyCommentData replyCommentData) {
        toastUtils.showShort(replyCommentData.getMessage());
        presenter.getReplyList(topicId, "1");
        InputMethodManager methodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        methodManager.hideSoftInputFromWindow(etReply.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
        etReply.setText("");
    }

    @Override
    public void topicLike(TopicLikeData topicLikeData) {
        toastUtils.showShort(topicLikeData.getMessage());
        presenter.getTopicDetail(topicId);
    }

    @OnClick({R.id.arrow_back, R.id.iv_like, R.id.iv_reply, R.id.iv_re_commit, R.id.sdv_head, R.id.tv_nick_name, R.id.tv_reply_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.arrow_back:
                finish();
                break;
            case R.id.iv_like:
                presenter.topicLike(topicId, "1");
                break;
            case R.id.iv_reply:
                etReply.requestFocus();
                InputMethodManager methodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                methodManager.showSoftInput(etReply, InputMethodManager.SHOW_FORCED);
                break;
            case R.id.iv_re_commit:
                break;
            case R.id.sdv_head:
            case R.id.tv_nick_name:
                if (topicDetail.getData() != null
                        && topicDetail.getData().getTopicDetailInfo() != null
                        && topicDetail.getData().getTopicDetailInfo().getUserTopicInfo() != null
                        ) {
                    startActivity(new Intent(this, FriendPageActivity.class)
                            .putExtra("userId", topicDetail.getData().getTopicDetailInfo().getUserTopicInfo().getUserId())
                            .putExtra("nickName", tvNickName.getText().toString())
                    );
                }
                break;

            case R.id.tv_reply_comment:
                if (!TextUtils.isEmpty(etReply.getText())) {
                    presenter.replyComment(topicId, etReply.getText().toString());
                }
                break;
        }
    }

}
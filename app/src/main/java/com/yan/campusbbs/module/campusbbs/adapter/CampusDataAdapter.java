package com.yan.campusbbs.module.campusbbs.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yan.campusbbs.R;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.util.SizeUtils;
import com.yan.campusbbs.widget.banner.Banner;
import com.yan.campusbbs.widget.banner.BannerIndicator;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/3/5.
 */

public class CampusDataAdapter extends BaseMultiItemQuickAdapter<DataMultiItem, BaseViewHolder> {
    private Context context;
    public static final int ITEM_TYPE_BANNER = 1;
    public static final int ITEM_TYPE_POST_ALL = 2;
    public static final int ITEM_TYPE_POST_TAG = 3;

    @Inject
    public CampusDataAdapter(List<DataMultiItem> data, Context context) {
        super(data);
        addItemType(ITEM_TYPE_BANNER, R.layout.fragment_campus_bbs_item_banner);
        addItemType(ITEM_TYPE_POST_ALL, R.layout.fragment_campus_bbs_item_post_all);
        addItemType(ITEM_TYPE_POST_TAG, R.layout.fragment_campus_bbs_item_post_tag);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, DataMultiItem multiItem) {
        switch (holder.getItemViewType()) {
            case ITEM_TYPE_BANNER:
                Banner banner = holder.getView(R.id.banner);
                BannerIndicator bannerIndicator = holder.getView(R.id.indicator);
                banner.setInterval(5000);
                banner.setPageChangeDuration(500);
                banner.setBannerDataInit(new Banner.BannerDataInit() {
                    @Override
                    public ImageView initImageView() {
                        return (ImageView) LayoutInflater.from(context).inflate(R.layout.banner_imageview, null);
                    }

                    @Override
                    public void initImgData(ImageView imageView, Object imgPath) {
                        ((SimpleDraweeView) imageView).setImageURI((String) imgPath);
                    }
                });
                bannerIndicator.setIndicatorSource(
                        ContextCompat.getDrawable(context, R.drawable.banner_indicator_select),//select
                        ContextCompat.getDrawable(context, R.drawable.banner_indicator_unselect),//unselect
                        SizeUtils.dp2px(context, 8)//widthAndHeight
                );
                banner.setOnBannerItemClickListener(position ->
                        Toast.makeText(context, "position:" + position, Toast.LENGTH_SHORT).show());
                banner.setDataSource(multiItem.data);
                banner.attachIndicator(bannerIndicator);

                break;
            case ITEM_TYPE_POST_ALL:
                SimpleDraweeView img = holder.getView(R.id.img);
                img.setImageURI("http://2t.5068.com/uploads/allimg/151104/57-151104141236.jpg");
                break;

            case ITEM_TYPE_POST_TAG:
                SimpleDraweeView head = holder.getView(R.id.head);
                head.setImageURI("http://2t.5068.com/uploads/allimg/151104/57-151104141236.jpg");

                break;
        }
    }
}

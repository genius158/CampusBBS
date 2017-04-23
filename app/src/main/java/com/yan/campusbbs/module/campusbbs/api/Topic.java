package com.yan.campusbbs.module.campusbbs.api;

import com.yan.campusbbs.module.campusbbs.data.TopicData;
import com.yan.campusbbs.module.campusbbs.data.TopicDetailData;
import com.yan.campusbbs.repository.DataAddress;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by yan on 2017/4/21.
 */

public interface Topic {
    @GET(DataAddress.URL_TOPIC_BBS)
    Observable<TopicData> getTopicList(
            @Query("pageNum") String pageNum
            , @Query("typeDiv") int typeDiv
            , @Query("topicLabel") String topicLabel
    );

    @GET(DataAddress.URL_TOPIC_BBS)
    Observable<TopicData> getTopicList(
            @Query("pageNum") String pageNum
            , @Query("typeDiv") int typeDiv
    );

    @GET(DataAddress.URL_TOPIC_DETAIL)
    Observable<TopicDetailData> getTopicDetail(
            @Query("topicId") String topicId
    );
}

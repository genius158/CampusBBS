package com.yan.campusbbs.module.search.api;

import com.yan.campusbbs.module.campusbbs.data.TopicData;
import com.yan.campusbbs.repository.DataAddress;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by yan on 2017/4/21.
 */

public interface Search {
    @GET(DataAddress.URL_TOPIC_BBS)
    Observable<ResponseBody> getTopicList(
            @Query("pageNum") String pageNum
            ,  @Query("searchKey") String searchKey
            ,  @Query("typeDiv") int typeDiv
            ,  @Query("topicLabel") int topicLabel
    );

  @GET(DataAddress.URL_TOPIC_BBS)
    Observable<TopicData> getTopicList(
            @Query("pageNum") String pageNum
            ,  @Query("searchKey") String searchKey
            ,  @Query("typeDiv") int typeDiv
    );
}

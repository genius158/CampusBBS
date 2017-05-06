package com.yan.campusbbs.module.campusbbs.api;

import com.yan.campusbbs.module.campusbbs.data.ModifyData;
import com.yan.campusbbs.module.selfcenter.data.UserInfoData;
import com.yan.campusbbs.repository.DataAddress;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by yan on 2017/4/17.
 */

public interface UserInfo {

//    userId：类型String（必填）
//    nickname：类型String
//    realName：类型String
//    headImg：类型String
//    mood：类型String
//    email：类型String
//    age：类型String
//    gender：类型String
//    birth：类型String
//    major：类型String
//    school：类型String
//    address：类型String

//    @Multipart
//    @POST(DataAddress.URL_USER_EDIT)
//    Observable<ModifyData> userInfoEdit(
//            @Field("nickname") String nickname,
//            @Field("realName") String realName,
//            @Field("headImg") String headImg,
//            @Field("mood") String mood,
//            @Field("email") String email,
//            @Field("age") String age,
//            @Field("gender") String gender,
//            @Field("birth") String birth,
//            @Field("major") String major,
//            @Field("school") String school,
//            @Field("address") String address
//            , @Part MultipartBody.Part headerFile
//
//    );

    @GET(DataAddress.URL_USER_DETAIL)
    Observable<UserInfoData> getUserInfo(
            @Query("userId") String userId
    );

    @GET(DataAddress.URL_USER_DETAIL)
    Observable<UserInfoData> getSelfInfo();

    @GET(DataAddress.URL_ADD_FRIEND)
    Observable<UserInfoData> addFriend(
            @Query("userId") String userId
    );

}

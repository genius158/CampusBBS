package com.yan.campusbbs.module.campusbbs.api;

import com.yan.campusbbs.module.campusbbs.data.ModifyData;
import com.yan.campusbbs.repository.DataAddress;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
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

    @GET(DataAddress.URL_USER_EDIT)
    Observable<ModifyData> userInfoEdit(
            @Query("nickname") String nickname,
            @Query("realName") String realName,
            @Query("headImg") String headImg,
            @Query("mood") String mood,
            @Query("email") String email,
            @Query("age") String age,
            @Query("gender") String gender,
            @Query("birth") String birth,
            @Query("major") String major,
            @Query("school") String school,
            @Query("address") String address

    );

}

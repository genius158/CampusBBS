package com.yan.campusbbs.module.selfcenter.api;

import com.yan.campusbbs.module.selfcenter.data.RegisterData;
import com.yan.campusbbs.repository.DataAddress;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by yan on 2017/4/1.
 */

public interface Register {

    @GET(DataAddress.URL_REGISTER)
    Observable<RegisterData> register(
            @Query("phoneNum") String phoneNum
            , @Query("password") String password
            , @Query("nickname") String nickname
            , @Query("mood") String mood
            , @Query("email") String email
            , @Query("school") String school
            , @Query("birth") String birth
    );

}

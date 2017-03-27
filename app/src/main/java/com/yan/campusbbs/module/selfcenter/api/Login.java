package com.yan.campusbbs.module.selfcenter.api;

import com.yan.campusbbs.module.selfcenter.data.LoginInfoData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.yan.campusbbs.repository.DataAddress.URL_LOGIN;

/**
 * Created by yan on 2017/3/27.
 */

public interface Login {

    @GET(URL_LOGIN)
    Observable<LoginInfoData> login(
            @Query("userAccount") String userAccount,
            @Query("userPassword") String userPassword
    );


}

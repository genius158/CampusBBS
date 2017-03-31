package com.yan.campusbbs.module.campusbbs.ui.publish;

import android.content.Context;

import com.yan.campusbbs.module.campusbbs.ui.publish.api.Publish;
import com.yan.campusbbs.util.AppRetrofit;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class PublishPresenter implements PublishContract.Presenter {
    private PublishContract.View view;
    private Context context;
    private AppRetrofit appRetrofit;

    @Inject
    public PublishPresenter(Context context, PublishContract.View view, AppRetrofit appRetrofit) {
        this.view = view;
        this.context = context;
        this.appRetrofit = appRetrofit;
    }

    @Override
    public void start() {

    }

    @Override
    public void publish(String topicTitle, String topicContent, String typeDiv, String contentDiv) {
        Publish publish = appRetrofit.retrofit().create(Publish.class);
        view.addDisposable(publish.publish(topicTitle, topicContent, typeDiv, contentDiv)
                .subscribeOn(Schedulers.io())
                .map(responseBody -> {
                    JSONObject jsonObject = new JSONObject(responseBody.string());
                    return jsonObject.getInt("resultCode") == 200;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                            if (aBoolean) {
                                view.stateSuccess();
                            } else {
                                view.stateError();
                            }
                        }
                        , throwable -> {
                            throwable.printStackTrace();
                            view.stateNetError();
                        })
        );
    }
}

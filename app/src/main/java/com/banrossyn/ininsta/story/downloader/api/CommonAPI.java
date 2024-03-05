package com.banrossyn.ininsta.story.downloader.api;

import android.app.Activity;

import com.banrossyn.ininsta.story.downloader.api.model.FullDetailModel;
import com.banrossyn.ininsta.story.downloader.api.model.StoryModel;
import com.banrossyn.ininsta.story.downloader.utils.Utils;
import com.google.gson.JsonObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class CommonAPI {
    private static CommonAPI commonAPI;
    private static Activity activity;
    public String UserAgent = "\"Mozilla/5.0 (Linux; Android 13; Windows NT 10.0; Win64; x64; rv:109.0;Mobile; LG-M255; rv:113.0; SM-A205U; LM-Q720; Pixel 2 XL Build/OPD1.170816.004) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.5672.131 Mobile Safari/537.36 Instagram 282.0.0.22.119\"";

    public static CommonAPI getInstance(Activity activity) {
        if (commonAPI == null) {
            commonAPI = new CommonAPI();
        }
        CommonAPI.activity = activity;
        return commonAPI;
    }

    public void Result(final DisposableObserver disposableObserver, String str, String str2) {
        if (Utils.isNullOrEmpty(str2)) {
            str2 = "";
        }
        RestClient.getInstance(activity).getService().getResult(str, str2, UserAgent).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<JsonObject>() {

            @Override
            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(JsonObject jsonObject) {
                disposableObserver.onNext(jsonObject);
            }

            @Override
            public void onError(Throwable th) {
                disposableObserver.onError(th);
            }

            @Override
            public void onComplete() {
                disposableObserver.onComplete();
            }
        });
    }

    public void getStories(final DisposableObserver disposableObserver, String str) {
        if (Utils.isNullOrEmpty(str)) {
            str = "";
        }
        RestClient.getInstance(activity).getService()
                .getStories("https://i.instagram.com/api/v1/feed/reels_tray/", str, UserAgent)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StoryModel>() {

                    @Override
                    public void onSubscribe(Disposable disposable) {
                    }

                    public void onNext(StoryModel storyModel) {
                        disposableObserver.onNext(storyModel);
                    }

                    @Override
                    public void onError(Throwable th) {
                        disposableObserver.onError(th);
                    }

                    @Override
                    public void onComplete() {
                        disposableObserver.onComplete();
                    }
                });
    }

    public void getFullFeed(final DisposableObserver disposableObserver, String userid, String str2) {
        APIServices service = RestClient.getInstance(activity).getService();
        service.getFullApi("https://i.instagram.com/api/v1/feed/reels_media/", userid, str2, UserAgent)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FullDetailModel>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                    }

                    public void onNext(FullDetailModel fullDetailModel) {
                        disposableObserver.onNext(fullDetailModel);
                    }

                    @Override
                    public void onError(Throwable th) {
                        disposableObserver.onError(th);
                    }

                    @Override
                    public void onComplete() {
                        disposableObserver.onComplete();
                    }
                });
    }


}
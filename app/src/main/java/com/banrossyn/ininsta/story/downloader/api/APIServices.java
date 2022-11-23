package com.banrossyn.ininsta.story.downloader.api;

import com.banrossyn.ininsta.story.downloader.api.model.FullDetailModel;
import com.banrossyn.ininsta.story.downloader.api.model.StoryModel;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Url;

public interface APIServices {
    @GET
    Observable<JsonObject> getResult(
            @Url String str,
            @Header("Cookie")
                    String str2,
            @Header("User-Agent")
                    String str3);

    @GET
    Observable<FullDetailModel> getFullApi(
            @Url String str,
            @Header("Cookie")
                    String str2,
            @Header("User-Agent")
                    String str3);

    @GET
    Observable<StoryModel> getStories(
            @Url String str,
            @Header("Cookie")
                    String str2,
            @Header("User-Agent")
                    String str3);

}
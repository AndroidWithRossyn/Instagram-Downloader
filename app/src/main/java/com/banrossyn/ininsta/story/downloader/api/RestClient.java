package com.banrossyn.ininsta.story.downloader.api;

import android.app.Activity;

import com.google.gson.Gson;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestClient {
    private static Activity mActivity;
    private static final RestClient restClient = new RestClient();
    private static Retrofit retrofit;

    public static RestClient getInstance(Activity activity) {
        mActivity = activity;
        return restClient;
    }

    private RestClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient build = new OkHttpClient.Builder().readTimeout(2, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES).addInterceptor(new Interceptor() {

            @Override
            public final Response intercept(Chain chain) throws IOException{
                Response response = null;
                try {
                    response = chain.proceed(chain.request());
                    if (response.code() == 200) {
                        try {
                            return response.newBuilder().body(ResponseBody.create(response.body().contentType(), new JSONObject(response.body().string()).toString())).build();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (SocketTimeoutException e2) {
                    e2.printStackTrace();
                }
                return response;
            }
        }).addInterceptor(httpLoggingInterceptor).build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl("https://www.instagram.com/").addConverterFactory(GsonConverterFactory.create(new Gson())).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(build).build();
        }
    }

    public APIServices getService() {
        return (APIServices) retrofit.create(APIServices.class);
    }

}
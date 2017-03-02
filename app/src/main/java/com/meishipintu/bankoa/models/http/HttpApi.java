package com.meishipintu.bankoa.models.http;

import com.google.gson.GsonBuilder;
import com.meishipintu.bankoa.Constans;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/3/1.
 */

public class HttpApi {

    private static HttpApi instance;
    private Retrofit retrofit = null;
    private HttpService httpService;

    private HttpApi() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constans.BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        httpService = retrofit.create(HttpService.class);
    }

    public static HttpApi getInstance() {
        //因为netService是静态对象，所以使用静态锁
        synchronized (HttpApi.class) {
            if (instance == null) {
                instance = new HttpApi();
            }
        }
        return instance;
    }
}

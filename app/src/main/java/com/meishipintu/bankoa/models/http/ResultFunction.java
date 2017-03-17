package com.meishipintu.bankoa.models.http;


import android.util.Log;

import com.meishipintu.bankoa.Constans;
import com.meishipintu.bankoa.models.entity.HttpResult;

import rx.functions.Func1;

/**
 * Created by Administrator on 2017/3/16.
 * <p>
 * 主要功能：
 */

public class ResultFunction<T> implements Func1<HttpResult<T>, T> {

    @Override
    public T call(HttpResult<T> httpResult) {
        Log.d(Constans.APP, "httpResult:" + httpResult.toString());
        if (httpResult.getStatus() != 1) {
            throw new RuntimeException(httpResult.getMsg());
        }
        Log.i("test", httpResult.getData().toString());
        return httpResult.getData();
    }
}

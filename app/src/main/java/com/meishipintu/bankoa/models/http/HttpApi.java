package com.meishipintu.bankoa.models.http;

import com.google.gson.GsonBuilder;
import com.meishipintu.bankoa.Constans;
import com.meishipintu.bankoa.models.entity.HttpResult;
import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.models.entity.TaskTriggerInfo;
import com.meishipintu.bankoa.models.entity.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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

    //登录
    public Observable<UserInfo> login(String tel, String psw) {
        return httpService.loginService(tel, psw).map(new ResultFunction<UserInfo>());
    }

    //获取申请状态
    public Observable<Boolean> getRegisterStatus(String tel) {
        return httpService.getRegisterStatusService(tel).map(new ResultFunction<JSONObject>())
                .map(new Func1<JSONObject, Boolean>() {
                    @Override
                    public Boolean call(JSONObject s) {
                        try {
                            Boolean is_pass = s.getBoolean("is_pass");
                            return is_pass;
                        } catch (JSONException e) {
                            e.printStackTrace();
                            return false;
                        }
                    }
                });
    }

    //获取验证码接口
    public Observable<String> getVerifyCode(String tel) {
        return httpService.getVerifyCodeService(tel).map(new Func1<ResponseBody, String>() {
            @Override
            public String call(ResponseBody responseBody) {
                return responseBody.toString();
            }
        });
    }

    //注册方法
    public Observable<UserInfo> register(String tel, String verifyCode, String psw) {
        return httpService.registerService(tel, verifyCode, psw).map(new ResultFunction<UserInfo>());
    }

    //发起任务
    public Observable<Task> triggerTask(TaskTriggerInfo triggerInfo) {
        return httpService.triggerTaskService(triggerInfo).map(new ResultFunction<Task>());
    }

    //获取当前任务信息
    public Observable<JSONObject> getTaskInfoNow(String taskId) {
        return httpService.getTaskInfoNowService(taskId).map(new Func1<ResponseBody, JSONObject>() {
            @Override
            public JSONObject call(ResponseBody responseBody) {
                try {
                    String result = responseBody.string();
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("status") == 1) {
                        return jsonObject.getJSONObject("data");
                    } else {
                        throw new RuntimeException(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }
}



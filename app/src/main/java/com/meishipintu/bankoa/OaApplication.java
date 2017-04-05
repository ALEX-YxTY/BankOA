package com.meishipintu.bankoa;

import android.app.Application;
import android.util.Log;

import com.meishipintu.bankoa.components.ApplicationComponent;
import com.meishipintu.bankoa.components.DaggerApplicationComponent;
import com.meishipintu.bankoa.models.PreferenceHelper;
import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.bankoa.modules.ApplicationModule;
import com.tencent.bugly.Bugly;

import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/3/1.
 */

public class OaApplication extends Application {

    private ApplicationComponent applicationComponent;
    private static OaApplication instance;
    private static UserInfo user;
    public static JSONObject nodeNameList;
    public static JSONObject departmentList;
    public static int nodeNumber;

    public static OaApplication getInstance() {
        return instance;
    }

    public static UserInfo getUser() {
        return user;
    }

    public static void setUser(UserInfo user) {
        OaApplication.user = user;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        instance = this;
        //初始化jpush
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        //初始化Bugly
        Bugly.init(getApplicationContext(), "0f4b094654", false);


        user = PreferenceHelper.getUserInfo();
        nodeNameList = PreferenceHelper.getNodeNameList();
        nodeNumber = PreferenceHelper.getNodeNum();
        departmentList = PreferenceHelper.getDepartmentList();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}

package com.meishipintu.bankoa;

import android.app.Application;
import android.util.Log;

import com.meishipintu.bankoa.components.ApplicationComponent;
import com.meishipintu.bankoa.components.DaggerApplicationComponent;
import com.meishipintu.bankoa.models.PreferenceHelper;
import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.bankoa.modules.ApplicationModule;
import com.tencent.bugly.Bugly;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/3/1.
 */

public class OaApplication extends Application {

    private ApplicationComponent applicationComponent;
    private static OaApplication instance;
    private static UserInfo user;
    public static Map<String,JSONObject> nodeNameList;
    public static Map<String, Integer> nodeNumber;
    public static List<String> centerBranchList;
    public static Map<Integer, String[]> branchList;
    public static JSONObject departmentList;

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
        String[] ceterBranchList = PreferenceHelper.getCeterBranchList();
        if (ceterBranchList != null) {
            centerBranchList = Arrays.asList(ceterBranchList);
        }
        branchList = new HashMap<>();
        if (ceterBranchList != null) {
            for (int i = 0; i < centerBranchList.size(); i ++) {
                String[] branchList = PreferenceHelper.getBranchList(i + 1);
                if (branchList != null) {
                    OaApplication.branchList.put(i + 1, branchList);
                }
            }
        }
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}

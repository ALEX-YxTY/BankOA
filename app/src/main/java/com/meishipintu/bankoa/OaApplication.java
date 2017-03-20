package com.meishipintu.bankoa;

import android.app.Application;

import com.meishipintu.bankoa.components.ApplicationComponent;
import com.meishipintu.bankoa.components.DaggerApplicationComponent;
import com.meishipintu.bankoa.models.PreferenceHelper;
import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.bankoa.modules.ApplicationModule;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/3/1.
 */

public class OaApplication extends Application {

    private ApplicationComponent applicationComponent;
    private static OaApplication instance;
    private static UserInfo user;
    public static JSONObject nodeNameList;
    public static JSONObject taskTypeList;
    public static JSONObject departmentList;
    public static JSONObject centerBranchList;
    public static JSONObject branchList;
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
        user = PreferenceHelper.getUserInfo();
        nodeNameList = PreferenceHelper.getNodeNameList();
        nodeNumber = PreferenceHelper.getNodeNum();
        departmentList = PreferenceHelper.getDepartmentList();
        taskTypeList = PreferenceHelper.getTakTypeList();
        centerBranchList = PreferenceHelper.getCeterBranchList();
        branchList = PreferenceHelper.getBranchList();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}

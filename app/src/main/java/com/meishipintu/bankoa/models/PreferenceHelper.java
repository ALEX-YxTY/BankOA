package com.meishipintu.bankoa.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.meishipintu.bankoa.Constans;
import com.meishipintu.bankoa.OaApplication;
import com.meishipintu.bankoa.models.entity.UserInfo;

/**
 * Created by Administrator on 2017/3/2.
 * <p>
 * 主要功能：
 */

public class PreferenceHelper {

    public static SharedPreferences getSharePreference() {
        return OaApplication.getInstance().getSharedPreferences(OaApplication.class.getPackage().getName()
                , Context.MODE_PRIVATE);
    }

    public static void saveUserInfo(UserInfo userInfo) {
        SharedPreferences.Editor editor = getSharePreference().edit();
        editor.putString("uid", userInfo.getUid());
        editor.putString("id", userInfo.getId());
        editor.putString("userName", userInfo.getUser_name());
        editor.putString("departmentNo", userInfo.getDepartment_id());
        editor.putString("jobNo", userInfo.getJob_number());
        editor.putString("creditNo", userInfo.getCredit_number());
        editor.putString("level", userInfo.getLevel());
        editor.putString("mobile", userInfo.getMobile());
        editor.putString("url", userInfo.getUrl());
        editor.apply();
    }

    public static UserInfo getUserInfo() {
        SharedPreferences sharedPreferences = getSharePreference();
        UserInfo userInfo = new UserInfo();
        userInfo.setId(sharedPreferences.getString("id", null));
        userInfo.setUid(sharedPreferences.getString("uid", null));
        userInfo.setLevel(sharedPreferences.getString("level", null));
        userInfo.setDepartment_id(sharedPreferences.getString("departmentNo", null));
        userInfo.setJob_number(sharedPreferences.getString("jobNo", null));
        userInfo.setUser_name(sharedPreferences.getString("userName", null));
        userInfo.setMobile(sharedPreferences.getString("mobile", null));
        userInfo.setUrl(sharedPreferences.getString("url", null));
        return userInfo;
    }

    public static void savePsw(String psw) {
        SharedPreferences.Editor editor = getSharePreference().edit();
        editor.putString("special", psw);
        editor.apply();
    }

    public static String getId() {
        SharedPreferences sharedPreferences = getSharePreference();
        return sharedPreferences.getString("id", null);
    }

    public static String getLevel() {
        SharedPreferences sharedPreferences = getSharePreference();
        return sharedPreferences.getString("level", null);
    }
}

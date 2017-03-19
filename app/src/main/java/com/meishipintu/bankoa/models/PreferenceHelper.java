package com.meishipintu.bankoa.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.meishipintu.bankoa.OaApplication;
import com.meishipintu.bankoa.models.entity.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

    public static void saveCenterBranch(String centerBranchList) {
        SharedPreferences.Editor editor = getSharePreference().edit();
        editor.putString("centerBranch", centerBranchList);
        editor.apply();
    }

    public static Map<String, String> getCenterBranch() {
        SharedPreferences sharedPreferences = getSharePreference();
        Map<String, String> result = new HashMap<>();
        String centerBranch = sharedPreferences.getString("centerBranch", null);
        if (centerBranch != null) {
            try {
                JSONObject jsonObject = new JSONObject(centerBranch);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        } else {
            return result;
        }
    }

    public static void saveBranch(String branchList) {
        SharedPreferences.Editor editor = getSharePreference().edit();
        editor.putString("branch", branchList);
        editor.apply();
    }

    public static void saveTaskType(String taskType) {
        SharedPreferences.Editor editor = getSharePreference().edit();
        editor.putString("taskType", taskType);
        editor.apply();
    }

    public static void saveTaskNum(Integer number) {
        SharedPreferences.Editor editor = getSharePreference().edit();
        editor.putInt("nodeNumber", number);
        editor.apply();
    }
}

package com.meishipintu.bankoa.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.meishipintu.bankoa.OaApplication;
import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.library.util.StringUtils;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

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
        // 创建字节输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // 创建对象输出流，并封装字节流
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            // 将对象写入字节流
            oos.writeObject(userInfo);
            // 将字节流编码成base64的字符窜
            String oAuth_Base64 = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
            editor.putString("user", oAuth_Base64);
            editor.apply();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static UserInfo getUserInfo() {
        UserInfo userInfo = null;
        SharedPreferences sharedPreferences = getSharePreference();
        String productBase64 = sharedPreferences.getString("user", "");

        if (!StringUtils.isNullOrEmpty(productBase64)) {
            //读取字节
            byte[] base64 = Base64.decode(productBase64,Base64.DEFAULT);
            //封装到字节流
            ByteArrayInputStream bais = new ByteArrayInputStream(base64);
            try {
                //再次封装
                ObjectInputStream bis = new ObjectInputStream(bais);
                try {
                    //读取对象
                    userInfo = (UserInfo) bis.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                bis.close();
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    bais.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return userInfo;
    }


    public static void saveCenterBranch(String centerBranchList) {
        SharedPreferences.Editor editor = getSharePreference().edit();
        editor.putString("centerBranch", centerBranchList);
        editor.apply();
    }

    public static JSONObject getCeterBranchList() {
        SharedPreferences sharedPreferences = getSharePreference();
        String centerBranchList = sharedPreferences.getString("centerBranch", null);
        try {
            JSONObject result = new JSONObject(centerBranchList);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveBranch(String branchList) {
        SharedPreferences.Editor editor = getSharePreference().edit();
        editor.putString("branch", branchList);
        editor.apply();
    }

    public static JSONObject getBranchList() {
        SharedPreferences sharedPreferences = getSharePreference();
        String branchList = sharedPreferences.getString("branch", null);
        try {
            JSONObject result = new JSONObject(branchList);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveTaskType(String taskType) {
        SharedPreferences.Editor editor = getSharePreference().edit();
        editor.putString("taskType", taskType);
        editor.apply();
    }

    public static JSONObject getTakTypeList() {
        SharedPreferences sharedPreferences = getSharePreference();
        String taskTypeList = sharedPreferences.getString("taskType", null);
        try {
            JSONObject result = new JSONObject(taskTypeList);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveTaskNum(Integer number) {
        SharedPreferences.Editor editor = getSharePreference().edit();
        editor.putInt("nodeNumber", number);
        editor.apply();
    }

    public static int getNodeNum() {
        SharedPreferences sharedPreferences = getSharePreference();
        int nodeNum = sharedPreferences.getInt("nodeNumber", 24);
        return nodeNum;
    }

    public static void saveDepartmentList(String departmentList) {
        SharedPreferences.Editor editor = getSharePreference().edit();
        editor.putString("departmentList", departmentList);
        editor.apply();
    }

    public static JSONObject getDepartmentList() {
        SharedPreferences sharedPreferences = getSharePreference();
        String departmentList = sharedPreferences.getString("departmentList", null);
        try {
            JSONObject result = new JSONObject(departmentList);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveNodeNameList(String nodeNameList) {
        SharedPreferences.Editor editor = getSharePreference().edit();
        editor.putString("nodeNameList", nodeNameList);
        editor.apply();
    }

    public static JSONObject getNodeNameList() {
        SharedPreferences sharedPreferences = getSharePreference();
        String nodeNameList = sharedPreferences.getString("nodeNameList", null);
        try {
            JSONObject result = new JSONObject(nodeNameList);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void clear() {
        SharedPreferences sharePreference = PreferenceHelper.getSharePreference();
        SharedPreferences.Editor editor = sharePreference.edit();
        editor.remove("user");
        editor.remove("auto_login");
        editor.remove("mobile");
        editor.apply();
    }

    public static void saveAutoLogin(boolean isAutoLogin) {
        SharedPreferences.Editor editor = getSharePreference().edit();
        editor.putBoolean("auto_login", isAutoLogin);
        editor.apply();
    }

    public static boolean isAutoLogin() {
        SharedPreferences sharedPreferences = getSharePreference();
        return sharedPreferences.getBoolean("auto_login", false);
    }

    public static void saveMobile(String mobile) {
        SharedPreferences.Editor editor = getSharePreference().edit();
        editor.putString("mobile", mobile);
        editor.apply();
    }

    public static String getMobile() {
        SharedPreferences sharedPreferences = getSharePreference();
        return sharedPreferences.getString("mobile", null);
    }

}

package com.meishipintu.bankoa.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meishipintu.bankoa.OaApplication;
import com.meishipintu.bankoa.models.entity.CenterBranch;
import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.library.util.StringUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by Administrator on 2017/3/2.
 * <p>
 * 主要功能：
 */

public class PreferenceHelper {

    public static String TAG = "BankOA-PreferenceHelper";

    public static SharedPreferences getSharePreference()    {
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


    public static void saveCenterBranch(List<CenterBranch> centerBranchList) {
        Gson gson = new Gson();
        String result = gson.toJson(centerBranchList);
        Log.i("test", "list to json:" + result);
        SharedPreferences.Editor editor = getSharePreference().edit();
        editor.putString("centerBranchNew", result.toString());
        editor.apply();
    }

    public static List<CenterBranch> getCeterBranchList() {
        SharedPreferences sharedPreferences = getSharePreference();
        String centerBranchList = sharedPreferences.getString("centerBranchNew", null);
        if (centerBranchList != null) {
            Gson gson = new Gson();
            List<CenterBranch> list = gson.fromJson(centerBranchList, new TypeToken<List<CenterBranch>>(){}.getType());
            return list;
        } else {
            return null;
        }
    }

    public static void saveBranch(int index, Map<Integer,String> branchList) {
        StringBuffer sbf = new StringBuffer();
        for (Map.Entry<Integer, String> entry : branchList.entrySet()) {
            Log.d("LoginPresenter", "key:" + entry.getKey() + ",value:" + entry.getValue());
            sbf.append(entry.getKey() + ";" + entry.getValue() + ",");
        }
        SharedPreferences.Editor editor = getSharePreference().edit();
        editor.putString("newbranch"+index, sbf.toString());
        editor.apply();
    }

    public static Map<Integer,String> getBranchList(int index) {
        SharedPreferences sharedPreferences = getSharePreference();
        String branchList = sharedPreferences.getString("newbranch" + index, null);
        if (branchList != null) {
            Map<Integer, String> resultMap = new HashMap<>();
            String[] split = branchList.split(",");
            for (int i = 0; i < split.length-1; i++) {
                String[] split1 = split[i].split(";");
                resultMap.put(Integer.parseInt(split1[0]), split1[1]);
            }
            return resultMap;
        } else {
            return null;
        }
    }

    public static void saveTaskType(List<String> taskType) {
        StringBuffer sbf = new StringBuffer();
        for(int i=0;i<taskType.size();i++) {
            sbf.append(taskType.get(i));
            if (i != taskType.size() - 1) {
                sbf.append(",");
            }
        }
        SharedPreferences.Editor editor = getSharePreference().edit();
        editor.putString("taskType", sbf.toString());
        editor.apply();
    }

    public static String[] getTakTypeList() {
        SharedPreferences sharedPreferences = getSharePreference();
        String taskTypeList = sharedPreferences.getString("taskType", null);
        if (taskTypeList != null) {
            return taskTypeList.split(",");
        } else {
            return new String[]{};
        }
    }

    //单独存取每个type的任务节点数量
    public static void saveNodeNum(int type, int number) {
        SharedPreferences.Editor editor = getSharePreference().edit();
        editor.putInt("nodeNumber"+type, number);
        editor.apply();
    }

    //整体获取所有任务节点数量，以map返回
    public static Map<String ,Integer> getNodeNum() {
        SharedPreferences sharedPreferences = getSharePreference();
        Map<String,Integer> resultMap = new HashMap<>();
        for (int i = 1; i <= getTakTypeList().length; i ++) {
            resultMap.put(i + "", sharedPreferences.getInt("nodeNumber", -1));
        }
        return resultMap;
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

    //单独存取每个type的任务节点名称
    public static void saveNodeNameList(int type, String nodeNameList) {
        SharedPreferences.Editor editor = getSharePreference().edit();
        editor.putString("nodeNameList" + type, nodeNameList);
        editor.apply();
    }

    //统一获取所有type的节点名称，以map形式返回
    public static Map<String,JSONObject> getNodeNameList() {
        SharedPreferences sharedPreferences = getSharePreference();
        Map<String,JSONObject> resultMap = new HashMap<>();
        for (int i = 1; i <= getTakTypeList().length; i ++) {
            String nodeNameList = sharedPreferences.getString("nodeNameList"+i, null);
            try {
                JSONObject result = new JSONObject(nodeNameList);
                resultMap.put(i + "", result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }

    public static void clear() {
        SharedPreferences sharePreference = PreferenceHelper.getSharePreference();
        SharedPreferences.Editor editor = sharePreference.edit();
        editor.remove("user");
        editor.remove("auto_login");
        editor.remove("mobile");
        editor.remove("newest_remind");
        editor.remove("newest_notice");
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

    public static int getNewestNotice() {
        SharedPreferences sharedPreferences = getSharePreference();
        return sharedPreferences.getInt("newest_notice", 0);
    }

    public static void saveNewestNotice(int integer) {
        SharedPreferences.Editor editor = getSharePreference().edit();
        editor.putInt("newest_notice", integer);
        editor.apply();
    }

    public static int getNewestRemind() {
        SharedPreferences sharedPreferences = getSharePreference();
        return sharedPreferences.getInt("newest_remind", 0);
    }

    public static void saveNewestRemind(int integer) {
        SharedPreferences.Editor editor = getSharePreference().edit();
        editor.putInt("newest_remind", integer);
        editor.apply();
    }

    //保存已读消息列表
    public static void saveReadNewdList(String uid, Set<String> stringSet) {
        SharedPreferences.Editor editor = getSharePreference().edit();
        editor.putStringSet(uid, stringSet);
        editor.apply();
    }

    //获取已读消息列表
    public static Set<String> getReadNewsList(String uid) {
        SharedPreferences sharePreference = getSharePreference();
        Set<String> stringSet = new HashSet<>(sharePreference.getStringSet(uid, new HashSet<String>()));
        return stringSet;
    }

    public static void saveBranchName(String tel) {
        SharedPreferences.Editor editor = getSharePreference().edit();
        editor.putString("branchname", tel);
        editor.apply();
    }

    public static void saveBranchPsw(String psw) {
        SharedPreferences.Editor editor = getSharePreference().edit();
        editor.putString("branchpw", psw);
        editor.apply();
    }

    public static String getBranchName() {
        SharedPreferences sharePreference = getSharePreference();
        return sharePreference.getString("branchname", "");
    }

    public static String getBranchPw() {
        SharedPreferences sharePreference = getSharePreference();
        return sharePreference.getString("branchpw", "");
    }
}

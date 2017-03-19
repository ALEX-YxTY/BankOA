package com.meishipintu.bankoa.models.http;

import com.meishipintu.bankoa.Constans;
import com.meishipintu.bankoa.models.entity.RemarkInfo;
import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.models.entity.TaskTriggerInfo;
import com.meishipintu.bankoa.models.entity.UserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Func1;

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

    //添加备注方法
    public Observable<Boolean> addRemark(RemarkInfo remarkInfo) {
        return httpService.addRemarkService(remarkInfo).map(new Func1<ResponseBody, Boolean>() {
            @Override
            public Boolean call(ResponseBody responseBody) {
                try {
                    String result = responseBody.string();
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("status") == 1) {
                        return true;
                    } else {
                        return false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
    }

    //获取中心支行列表
    public Observable<JSONObject> getCenterBranchList() {
        return httpService.getCenterBranchListService().map(new Func1<ResponseBody, JSONObject>() {
            @Override
            public JSONObject call(ResponseBody responseBody) {
                JSONObject resultJson = new JSONObject();
                try {
                    String result = responseBody.string();
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("status") != 1) {
                        throw new RuntimeException(jsonObject.getString("msg"));
                    } else {
                        JSONArray dataArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject data = dataArray.getJSONObject(i);
                            resultJson.put(data.getString("id"), data.getString("branch"));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    return resultJson;
                }
            }
        });
    }

    //获取支行列表
    public Observable<JSONObject> getBranchList() {
        return httpService.getBranchListService().map(new Func1<ResponseBody, JSONObject>() {
            @Override
            public JSONObject call(ResponseBody responseBody) {
                JSONObject resultJson = new JSONObject();
                try {
                    String result = responseBody.string();
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("status") != 1) {
                        throw new RuntimeException(jsonObject.getString("msg"));
                    } else {
                        JSONArray dataArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject data = dataArray.getJSONObject(i);
                            resultJson.put(data.getString("id"), data.getString("branch"));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    return resultJson;
                }
            }
        });
    }

    //获取任务类型列表
    public Observable<JSONObject> getTaskTypeList() {
        return httpService.getTaskTypeListService().map(new Func1<ResponseBody, JSONObject>() {
            @Override
            public JSONObject call(ResponseBody responseBody) {
                JSONObject resultJson = new JSONObject();
                try {
                    String result = responseBody.string();
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("status") != 1) {
                        throw new RuntimeException(jsonObject.getString("msg"));
                    } else {
                        JSONArray dataArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject data = dataArray.getJSONObject(i);
                            resultJson.put(data.getString("id"), data.getString("type_name"));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    return resultJson;
                }
            }
        });
    }

    //获取任务节点数
    public Observable<Integer> getTaskNodeNum() {
        return httpService.getLastTaskService().map(new Func1<ResponseBody, Integer>() {
            @Override
            public Integer call(ResponseBody responseBody) {
                try {
                    String result = responseBody.string();
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("status") != 1) {
                        throw new RuntimeException(jsonObject.getString("msg"));
                    } else {
                        return jsonObject.getJSONObject("data").getInt("level");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }
}



package com.meishipintu.bankoa.models.http;

import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.meishipintu.bankoa.Constans;
import com.meishipintu.bankoa.models.entity.CommentInfo;
import com.meishipintu.bankoa.models.entity.PaymentDetailItem;
import com.meishipintu.bankoa.models.entity.PaymentInfo;
import com.meishipintu.bankoa.models.entity.RemarkInfo;
import com.meishipintu.bankoa.models.entity.SysNotic;
import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.models.entity.UpClassRemind;
import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.bankoa.models.entity.paymentItem;
import com.meishipintu.library.util.Encoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/1.
 */

public class HttpApi {

    private static final String TAG = "BankOA-HttpApi";
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
        final Gson gson = new Gson();
        return httpService.loginService(tel, Encoder.md5(psw)).map(new Func1<ResponseBody, UserInfo>() {
            @Override
            public UserInfo call(ResponseBody responseBody) {
                String result = null;
                try {
                    result = responseBody.string();
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("status") != 1) {
                        throw new RuntimeException(jsonObject.getString("msg"));
                    } else {
                        return gson.fromJson(jsonObject.getString("data"),UserInfo.class);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    //获取申请状态
    public Observable<Boolean> getRegisterStatus(String tel) {
        return httpService.getRegisterStatusService(tel).map(new Func1<ResponseBody, Boolean>() {
            @Override
            public Boolean call(ResponseBody responseBody) {
                try {
                    String result = responseBody.string();
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("status") == 1) {
                        return jsonObject.getJSONObject("data").getInt("is_pass") == 1;
                    } else {
                        throw new RuntimeException(jsonObject.getString("msg"));
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

    //获取验证码接口
    public Observable<String> getVerifyCode(String tel) {
        return httpService.getVerifyCodeService(tel).map(new Func1<ResponseBody, String>() {
            @Override
            public String call(ResponseBody responseBody) {
                try {
                    String result = responseBody.string();
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("status") == 1) {
                        return jsonObject.getString("data");
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

    //注册方法
    public Observable<UserInfo> register(String tel, String verifyCode, String psw) {
        return httpService.registerService(tel, verifyCode, Encoder.md5(psw)).map(new Func1<ResponseBody, UserInfo>() {
            @Override
            public UserInfo call(ResponseBody responseBody) {
                Gson gson = new Gson();
                try {
                    String result = responseBody.string();
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("status") == 1) {
                        return gson.fromJson(jsonObject.getString("data"), UserInfo.class);
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

    //发起任务
    public Observable<Task> triggerTask(String credit_name
            , String apply_money, String credit_center_branch
            ,String credit_branch, String task_type
            , String task_name, String credit_manager
            , String sponsor_id, String sponsor_level) {
//        return httpService.triggerTaskService(map);
        return httpService.triggerTaskService(credit_name,apply_money,credit_center_branch,credit_branch
                ,task_type,task_name,credit_manager,sponsor_id,sponsor_level).map(new ResultFunction<Task>());
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
    public Observable<Boolean> addRemark(String taskId,String taskLevel,String remarkContent,String userId) {
        return httpService.addRemarkService(taskId,taskLevel,remarkContent,userId)
                .map(new Func1<ResponseBody, Boolean>() {
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

    //添加评论方法
    public Observable<Boolean> addComment(CommentInfo info) {
        return httpService.addCommentService(info.getTaskOwner(),info.getUserId(), info.getUserLevel(), info.getTask_id(), info.getTask_level()
                , info.getComment_content(), info.getPid()).map(new Func1<ResponseBody, Boolean>() {
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
    public Observable<List<String>> getCenterBranchList() {
        return httpService.getCenterBranchListService().map(new Func1<ResponseBody, List<String>>() {
            @Override
            public List<String> call(ResponseBody responseBody) {
                List<String> result = new ArrayList<>();
                try {
                    String resultString = responseBody.string();
                    JSONObject jsonObject = new JSONObject(resultString);
                    if (jsonObject.getInt("status") != 1) {
                        throw new RuntimeException(jsonObject.getString("msg"));
                    } else {
                        JSONArray dataArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < dataArray.length(); i++) {
                            result.add(dataArray.getJSONObject(i).getString("branch"));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    return result;
                }
            }
        });
    }

    //获取支行列表
    public Observable<List<String>> getBranchList() {
        return httpService.getBranchListService().map(new Func1<ResponseBody, List<String>>() {
            @Override
            public List<String> call(ResponseBody responseBody) {
                List<String> resultList = new ArrayList<String>();
                try {
                    String result = responseBody.string();
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("status") != 1) {
                        throw new RuntimeException(jsonObject.getString("msg"));
                    } else {
                        JSONArray dataArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < dataArray.length(); i++) {
                            resultList.add(dataArray.getJSONObject(i).getString("branch"));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    return resultList;
                }
            }
        });
    }

    //获取任务类型列表
    public Observable<List<String>> getTaskTypeList() {
        return httpService.getTaskTypeListService().map(new Func1<ResponseBody, List<String>>() {
            @Override
            public List<String> call(ResponseBody responseBody) {
                List<String> resultList = new ArrayList<>();
                try {
                    String result = responseBody.string();
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("status") != 1) {
                        throw new RuntimeException(jsonObject.getString("msg"));
                    } else {
                        JSONArray dataArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < dataArray.length(); i++) {
                            resultList.add(dataArray.getJSONObject(i).getString("type_name"));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    return resultList;
                }
            }
        });
    }

    //获取部门列表
    public Observable<JSONObject> getDepartmentList() {
        return httpService.getDepartmentListService().map(new Func1<ResponseBody, JSONObject>() {
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
                            resultJson.put(data.getString("id"), data.getString("department_name"));
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

    //获取节点名称列表
    public Observable<JSONObject> getNodeNameList(int type) {
        return httpService.getNodeListService(type).map(new Func1<ResponseBody, JSONObject>() {
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
                            resultJson.put(data.getString("level"), data.getString("task_name"));
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

    //获取任务列表
    // type=1 未完成  type=2 已完成
    public Observable<List<Task>> getTaskList(String userId, int type) {
        return httpService.getTaskList(userId, type + "").map(new ResultFunction<List<Task>>());
    }

    //完成任务节点
    public Observable<Integer> finishNode(String uid, String taskId) {
        return httpService.finishNodeService(uid, taskId).map(new Func1<ResponseBody, Integer>() {
            @Override
            public Integer call(ResponseBody responseBody) {
                String result = null;
                try {
                    result = responseBody.string();
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("status") != 1) {
                        throw new RuntimeException(jsonObject.getString("msg"));
                    } else {
                        return 1;
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

    //录入还款信息
    public Observable<Integer> addLoanInfo(PaymentInfo paymentInfo) {
        return httpService.addLoanInfoService(paymentInfo)
                .map(new Func1<ResponseBody, Integer>() {
                    @Override
                    public Integer call(ResponseBody responseBody) {
                        try {
                            String result = responseBody.string();
                            Log.d(Constans.APP, "result:" + result);
                            JSONObject jsonObject = new JSONObject(result);
                            if (jsonObject.getInt("status") != 1) {
                                throw new RuntimeException(jsonObject.getString("msg"));
                            } else {
                                return 1;
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

    //获取还款信息详情
    public Observable<PaymentInfo> getPaymentInfo(String taskId) {
        return httpService.getRepaymentInfoService(taskId).map(new Func1<ResponseBody, PaymentInfo>() {
                    @Override
                    public PaymentInfo call(ResponseBody s) {
                        PaymentInfo paymentInfo = null;
                        try {
                            String result = s.string();
                            Log.d(Constans.APP, "result:" + result);
                            JSONObject jsonObject = new JSONObject(result);
                            if (jsonObject.getInt("status") != 1) {
                                throw new RuntimeException(jsonObject.getString("msg"));
                            } else {
                                paymentInfo = new PaymentInfo();
                                JSONObject list = jsonObject.getJSONObject("data").getJSONObject("list");
                                paymentInfo.setTask_id(list.getString("id"));
                                paymentInfo.setCheck_money(list.getString("check_money"));
                                paymentInfo.setResult_money(list.getString("result_money"));
                                paymentInfo.setLoad_time(list.getString("loan_time"));
                                JSONArray paymentArray = jsonObject.getJSONObject("data").getJSONArray("repayment");
                                List<PaymentDetailItem> paymentItems = new ArrayList<>();
                                for(int i=0;i<paymentArray.length();i++) {
                                    JSONObject payment = paymentArray.getJSONObject(i);
                                    PaymentDetailItem item = new PaymentDetailItem(payment.getString("id")
                                            ,payment.getString("repayment_time"), payment.getString("repayment_money"));
                                    paymentItems.add(item);
                                }
                                paymentInfo.setRepayment_json(paymentItems);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return paymentInfo;
                    }
                });
    }

    //完成节点还款
    public Observable<Integer> finishPaymentNode(String nodeId, String taskId, final String realMoney) {
        return httpService.finishPaymentNodeService(nodeId,taskId,realMoney).map(new Func1<ResponseBody, Integer>() {
            @Override
            public Integer call(ResponseBody responseBody) {
                try {
                    String result = responseBody.string();
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("status") != 1) {
                        throw new RuntimeException(jsonObject.getString("msg"));
                    } else {
                        return 1;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    //获取员工列表
    public Observable<List<UserInfo>> getClerkList(String uid, String level) {
        return httpService.getClerkListService(uid, level).map(new ResultFunction<List<UserInfo>>());
    }

    //通过uid获取用户信息
    public Observable<UserInfo> getUserInfoById(String uid) {
        return httpService.getUserInfoByIdService(uid).map(new Func1<ResponseBody, UserInfo>() {
            @Override
            public UserInfo call(ResponseBody responseBody) {
                try {
                    String result = responseBody.string();
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("status") != 1) {
                        throw new RuntimeException(jsonObject.getString("msg"));
                    } else {
                        Gson gson = new Gson();
                        UserInfo userInfo = gson.fromJson(jsonObject.getString("data"), UserInfo.class);
                        return userInfo;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    //获取提醒
    public Observable<List<UpClassRemind>> getRemind(String uid) {
        return httpService.getRemindService(uid).map(new ResultFunction<List<UpClassRemind>>());
    }

    //获取最新系统通知id数
    public Observable<Integer> getNewestRemindNumber(String uid) {
        return httpService.getRemindService(uid).map(new ResultFunction<List<UpClassRemind>>())
                .map(new Func1<List<UpClassRemind>, Integer>() {
                    @Override
                    public Integer call(List<UpClassRemind> noticList) {
                        if (noticList.size() == 0) {
                            return 0;
                        } else {
                            return Integer.parseInt(noticList.get(0).getId());
                        }
                    }
                });
    }

    //获取系统通知
    public Observable<List<SysNotic>> getSysNotice() {
        return httpService.getSysNoticeService().map(new ResultFunction<List<SysNotic>>());
    }

    //获取最新系统通知id数
    public Observable<Integer> getNewestNoticeNumber() {
        return httpService.getSysNoticeService().map(new ResultFunction<List<SysNotic>>())
                .map(new Func1<List<SysNotic>, Integer>() {
                    @Override
                    public Integer call(List<SysNotic> noticList) {
                        if (noticList.size() == 0) {
                            return 0;
                        } else {
                            return Integer.parseInt(noticList.get(0).getId());
                        }
                    }
                });
    }

    //获取搜索结果
    public Observable<List<Task>> getSearchInfo(String level, String departmentId, String uid, String content) {
        return httpService.searchTaskService(level, departmentId, uid, content).map(new ResultFunction<List<Task>>());
    }

    //修改密码
    public Observable<Integer> changePsw(String mobile, String verifyCode, String pswNew) {
        Log.d(TAG, "change psw:" + Encoder.md5(pswNew));
        return httpService.changePswService(mobile,verifyCode, Encoder.md5(pswNew)).map(new Func1<ResponseBody, Integer>() {
            @Override
            public Integer call(ResponseBody responseBody) {
                try {
                    String result = responseBody.string();
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("status") != 1) {
                        throw new RuntimeException(jsonObject.getString("msg"));
                    } else {
                        return 1;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    //重绑手机
    public Observable<Integer> changeMobile(String mobile, String verifyCode, String uid,String psw) {
        return httpService.changeMobileService(mobile, verifyCode, uid, Encoder.md5(psw))
                .map(new Func1<ResponseBody, Integer>() {
                    @Override
                    public Integer call(ResponseBody responseBody) {
                        try {
                            String result = responseBody.string();
                            Log.d(TAG, "change mobile:" + result);
                            JSONObject jsonObject = new JSONObject(result);
                            if (jsonObject.getInt("status") != 1) {
                                throw new RuntimeException(jsonObject.getString("msg"));
                            } else {
                                return 1;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
        });
    }

    //删除任务
    public Observable<Integer> deletTask(String taskID) {
        return httpService.deletTaskService(taskID).map(new Func1<ResponseBody, Integer>() {
            @Override
            public Integer call(ResponseBody responseBody) {
                try {
                    String result = responseBody.string();
                    Log.d(TAG, "change mobile:" + result);
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("status") != 1) {
                        throw new RuntimeException(jsonObject.getString("msg"));
                    } else {
                        return 1;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }
}



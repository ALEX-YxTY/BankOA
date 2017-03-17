package com.meishipintu.bankoa.models.http;

import com.meishipintu.bankoa.models.entity.HttpResult;
import com.meishipintu.bankoa.models.entity.RemarkInfo;
import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.models.entity.TaskTriggerInfo;
import com.meishipintu.bankoa.models.entity.UserInfo;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2017/3/1.
 */

public interface HttpService {

    //登录接口
    @FormUrlEncoded
    @POST("Home/Api/login")
    Observable<HttpResult<UserInfo>> loginService(@Field("mobile") String tel, @Field("password") String psw);

    //检查审核是否通过接口
    @FormUrlEncoded
    @POST("Home/Api/getRegistStatus")
    Observable<HttpResult<JSONObject>> getRegisterStatusService(@Field("mobile") String mobile);

    //获取验证码接口
    @FormUrlEncoded
    @POST("Home/Api/getVerifyCodeByMobile")
    Observable<ResponseBody> getVerifyCodeService(@Field("mobile") String mobile);

    //注册接口
    @FormUrlEncoded
    @POST("Home/Api/user_regist_step_finish")
    Observable<HttpResult<UserInfo>> registerService(@Field("mobile") String mobile
            , @Field("verify") String verifyCode, @Field("password") String psw);

    //发起任务
    @FormUrlEncoded
    @POST("Home/Api/startTaskOfStepOne")
    Observable<HttpResult<Task>> triggerTaskService(@Body TaskTriggerInfo taskTriggerInfo);

    //获取当前任务信息
    @FormUrlEncoded
    @POST("Home/Api/getNowTaskAllInfo")
    Observable<ResponseBody> getTaskInfoNowService(@Field("task_id") String taskId);

    //添加评论接口
    @FormUrlEncoded
    @POST("Home/Api/doTaskRemark")
    Observable<ResponseBody> addRemarkService(@Body RemarkInfo remarkInfo);
}

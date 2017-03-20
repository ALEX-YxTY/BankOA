package com.meishipintu.bankoa.models.http;

import android.widget.ListView;

import com.meishipintu.bankoa.models.entity.HttpResult;
import com.meishipintu.bankoa.models.entity.RemarkInfo;
import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.models.entity.TaskTriggerInfo;
import com.meishipintu.bankoa.models.entity.UserInfo;

import org.json.JSONObject;

import java.util.List;

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
    Observable<ResponseBody> loginService(@Field("mobile") String tel, @Field("password") String psw);

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

    //获取中心支行列表
    @POST("Home/Api/getCenterBranch")
    Observable<ResponseBody> getCenterBranchListService();

    //获取支行列表
    @POST("Home/Api/getBranch")
    Observable<ResponseBody> getBranchListService();

    //获取部门列表
    @POST("Home/Api/getDepartmentList")
    Observable<ResponseBody> getDepartmentListService();

    //获取任务类型列表
    @POST("Home/Api/getTaskType")
    Observable<ResponseBody> getTaskTypeListService();

    //获取任务节点数
    @POST("Home/Api/getLastTask")
    Observable<ResponseBody> getLastTaskService();

    //获取节点名称列表
    @POST("Home/Api/getAllTask")
    Observable<ResponseBody> getNodeListService();

    //获取用户的任务列表
    @FormUrlEncoded
    @POST("Home/Api/getUserTask")
    Observable<HttpResult<List<Task>>> getTaskList(@Field("user_id") String uid, @Field("type") String type);

    //完成当前节点
    @FormUrlEncoded
    @POST("Home/Api/startOtherStepTask")
    Observable<ResponseBody> finishNodeService(@Field("user_id") String uid, @Field("task_id") String taskID);
}

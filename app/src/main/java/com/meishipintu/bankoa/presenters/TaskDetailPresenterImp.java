package com.meishipintu.bankoa.presenters;

import android.provider.Settings;
import android.util.Log;

import com.google.gson.Gson;
import com.meishipintu.bankoa.Constans;
import com.meishipintu.bankoa.contracts.TaskDetailContract;
import com.meishipintu.bankoa.models.entity.NodeInfoNow;
import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.bankoa.models.http.HttpApi;
import com.meishipintu.library.util.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017/3/17.
 * <p>
 * 主要功能：
 */

public class TaskDetailPresenterImp implements TaskDetailContract.IPresenter {

    private TaskDetailContract.IView iView;
    private HttpApi httpApi;
    private CompositeSubscription subscriptions;
    public NodeInfoNow nodeInfoNow;

    @Inject
    TaskDetailPresenterImp(TaskDetailContract.IView view) {
        this.iView = view;
        httpApi = HttpApi.getInstance();
        subscriptions = new CompositeSubscription();
    }

    public TaskDetailPresenterImp() {
        httpApi = HttpApi.getInstance();
        subscriptions = new CompositeSubscription();
    }

    @Override
    public void getTaskInfo(String taskId) {
        subscriptions.add(httpApi.getTaskInfoNow(taskId).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<JSONObject>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        iView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        Gson gson = new Gson();
                        try {
                            JSONObject nowInfo = jsonObject.getJSONObject("now_task_info");
                            String nodeNowLevel = nowInfo.getString("user_task_level");
                            String timeRemain = nowInfo.getString("need_finish");
                            String nodeNowName = jsonObject.getJSONObject("now_task").getString("task_name");
                            String nodeBeforeName = null;
                            String nodeAfterName = null;
                            if (!jsonObject.isNull("before_task_info")) {
                                nodeBeforeName = jsonObject.getJSONObject("before_task_info")
                                        .getString("task_name");
                            }
                            if(!jsonObject.isNull("after_task_info")) {
                                nodeAfterName = jsonObject.getJSONObject("after_task_info")
                                        .getString("task_name");
                            }
                            iView.showGraphic(new NodeInfoNow(nodeNowLevel, nodeNowName
                                    , nodeBeforeName, nodeAfterName, timeRemain));
                            if (!jsonObject.isNull("userinfo")) {
                                UserInfo userInfo = gson.fromJson(jsonObject.getString("userinfo"), UserInfo.class);
                                iView.showUserInfo(userInfo);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            iView.showError("获取数据失败，请稍后重试");
                        }
                    }
                }));
    }

    @Override
    public void setTaskNodeFinished() {

    }

    @Override
    public void addNodeRemarks(String taskId, String taskLevel, String remark, String userId) {

    }

    @Override
    public void addNodeComment(String supervisorId, String comment) {

    }

    //from TaskDetailContract.IPresenter
    @Override
    public void unSubscrib() {
        subscriptions.clear();
    }
}

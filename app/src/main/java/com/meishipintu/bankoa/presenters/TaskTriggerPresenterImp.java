package com.meishipintu.bankoa.presenters;

import android.content.Context;
import android.util.Log;

import com.meishipintu.bankoa.Constans;
import com.meishipintu.bankoa.OaApplication;
import com.meishipintu.bankoa.contracts.TaskTriggerContract;
import com.meishipintu.bankoa.models.PreferenceHelper;
import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.models.http.HttpApi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017/3/2.
 * <p>
 *
 * 主要功能：TaskTrigger页的依赖Module
 */

public class TaskTriggerPresenterImp implements TaskTriggerContract.IPresenter {

    private TaskTriggerContract.IView view;
    private CompositeSubscription subscriptions;
    private HttpApi httpApi;
    private PreferenceHelper preferenceHelper;

    @Inject
    TaskTriggerPresenterImp(Context context, TaskTriggerContract.IView view) {
        this.view = view;
        httpApi = HttpApi.getInstance();
        this.subscriptions = new CompositeSubscription();
    }

    @Override
    public void getCenteralBranches() {

    }

    @Override
    public void getBranches() {

    }

    @Override
    public void getTaskType() {

    }

    @Override
    public void triggerTask(String loanerName, String loanMoney, String centerBranchType, String branchType
            , String taskType, final String taskName, final String recommendManager,String uid,String level) {

        subscriptions.add(httpApi.triggerTask(loanerName, loanMoney, centerBranchType, branchType
                , taskType, taskName, recommendManager, uid, level)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Task>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showTriggerResult(e.getMessage(), false, null);
                    }

                    @Override
                    public void onNext(Task task) {
                        view.showTriggerResult("", true, task);
                    }
                }));
    }

    //from BasicPresenter
    @Override
    public void unSubscrib() {
        subscriptions.clear();
    }
}

package com.meishipintu.bankoa.presenters;

import android.content.Context;
import android.util.Log;

import com.meishipintu.bankoa.Constans;
import com.meishipintu.bankoa.OaApplication;
import com.meishipintu.bankoa.contracts.TaskTriggerContract;
import com.meishipintu.bankoa.models.PreferenceHelper;
import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.models.http.HttpApi;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

    @Inject
    TaskTriggerPresenterImp(TaskTriggerContract.IView view) {
        this.view = view;
        httpApi = HttpApi.getInstance();
        this.subscriptions = new CompositeSubscription();
    }

    @Override
    public void getCenteralBranches() {
        List<String> ceterBranchList = OaApplication.centerBranchList;
        if (ceterBranchList == null) {
            subscriptions.add(httpApi.getCenterBranchList().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<String>>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            view.showError(e.getMessage());
                        }

                        @Override
                        public void onNext(List<String> strings) {
                            PreferenceHelper.saveCenterBranch(strings);
                            OaApplication.centerBranchList = strings;
                            String[] stringArr = new String[strings.size()];
                            strings.toArray(stringArr);
                            view.showCenteralBranches(stringArr);
                        }
                    }));
        } else {
            String[] stringArr = new String[ceterBranchList.size()];
            ceterBranchList.toArray(stringArr);
            view.showCenteralBranches(stringArr);
        }
    }

    @Override
    public void getBranches(final int centerBranch) {
        String[] branchList = OaApplication.branchList.get(centerBranch);
        if (branchList == null) {
            subscriptions.add(httpApi.getBranchList(centerBranch).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<String>>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            view.showError(e.getMessage());
                        }

                        @Override
                        public void onNext(List<String> strings) {
                            PreferenceHelper.saveBranch(centerBranch, strings);
                            String[] stringArr = new String[strings.size()];
                            strings.toArray(stringArr);
                            OaApplication.branchList.put(centerBranch, stringArr);
                            if (stringArr.length == 0) {
                                view.showError("该分行没有下级支行！");
                            } else {
                                view.showBranches(stringArr);
                            }
                        }
                    }));
        }else if (branchList.length == 0) {
            view.showError("该分行没有下级支行！");
        } else {
            view.showBranches(branchList);
        }
    }

    @Override
    public void getTaskType() {
        if (PreferenceHelper.getTakTypeList() != null) {
            view.showTaskType(PreferenceHelper.getTakTypeList());
        } else {
            subscriptions.add(httpApi.getTaskTypeList().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<String>>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            view.showError(e.getMessage());
                        }

                        @Override
                        public void onNext(List<String> strings) {
                            PreferenceHelper.saveTaskType(strings);
                            String[] stringArr = new String[strings.size()];
                            strings.toArray(stringArr);
                            view.showTaskType(stringArr);
                        }
                    }));
        }
    }

    @Override
    public void triggerTask(String loanerName, String loanMoney, int centerBranchType, int branchType
            , int taskType, final String recommendManager,String uid,String level) {

        subscriptions.add(httpApi.triggerTask(loanerName, loanMoney, centerBranchType, branchType
                , taskType,recommendManager, uid, level)
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

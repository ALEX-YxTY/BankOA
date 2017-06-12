package com.meishipintu.bankoa.presenters;

import android.app.Presentation;

import com.meishipintu.bankoa.OaApplication;
import com.meishipintu.bankoa.contracts.TaskContract;
import com.meishipintu.bankoa.models.PreferenceHelper;
import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.models.http.HttpApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017/3/17.
 * <p>
 * 主要功能：
 */

public class TaskPresenterImp implements TaskContract.IPresenter {

    private TaskContract.IView iView;
    private HttpApi httpApi;
    private CompositeSubscription subsriptions;

    @Inject
    TaskPresenterImp(TaskContract.IView view) {
        this.iView = view;
        this.httpApi = HttpApi.getInstance();
        subsriptions = new CompositeSubscription();
    }

    @Override
    public void getTask(String uid, int type) {
        subsriptions.add(httpApi.getTaskList(uid,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Task>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        iView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Task> taskList) {
                        iView.showTask(taskList);
                    }
                }));

    }

    @Override
    public void getCenterBranch() {
        List<String> centerBranchList = OaApplication.centerBranchList;
        if (centerBranchList == null) {
            subsriptions.add(httpApi.getCenterBranchList().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<String>>() {
                        @Override
                        public void call(List<String> strings) {
                            PreferenceHelper.saveCenterBranch(strings);
                            iView.onCenterBranchGet(strings);
                        }
                    }));
        } else {
            iView.onCenterBranchGet(centerBranchList);
        }
    }

    @Override
    public void getBranchList(int totalNum) {
        for(int i=1;i<=totalNum;i++) {
            final int index = i;
            Map<Integer, String> branchList = OaApplication.branchList.get(index);
            if (branchList == null) {
                subsriptions.add(httpApi.getBranchList(i).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Map<Integer, String>>() {
                            @Override
                            public void call(Map<Integer, String> strings) {
                                PreferenceHelper.saveBranch(index, strings);
                                OaApplication.branchList.put(index, strings);
                                iView.onBranchListGet(index, strings);
                            }
                        }));
            } else {
                iView.onBranchListGet(i, branchList);
            }
        }
    }

    //from BasicPresenter.IPresenter
    @Override
    public void unSubscrib() {
        subsriptions.clear();
    }

}

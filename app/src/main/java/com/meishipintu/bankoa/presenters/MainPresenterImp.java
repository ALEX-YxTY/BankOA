package com.meishipintu.bankoa.presenters;

import android.util.Log;

import com.meishipintu.bankoa.Constans;
import com.meishipintu.bankoa.OaApplication;
import com.meishipintu.bankoa.contracts.MainContract;
import com.meishipintu.bankoa.models.PreferenceHelper;
import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.bankoa.models.http.HttpApi;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017/3/16.
 * <p>
 * 主要功能：
 */

public class MainPresenterImp implements MainContract.IPresenter {

    private MainContract.IView iView;
    private HttpApi httpApi;
    private CompositeSubscription subscriptions;

    @Inject
    public MainPresenterImp(MainContract.IView view) {
        this.iView = view;
        httpApi = HttpApi.getInstance();
        subscriptions = new CompositeSubscription();
    }

    @Override
    public void getUserInfo() {
        UserInfo userInfo = OaApplication.getUser();
        if (userInfo != null && userInfo.getId() != null) {
            iView.refreshUI(userInfo);
        }else {
            iView.showError("获取信息失败，请重新登录");
        }
    }

    @Override
    public void getNewestNotice() {
        subscriptions.add(httpApi.getNewestNoticeNumber().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        iView.dealNewestNotice(integer);
                    }
                }));
    }

    @Override
    public void getNewestRemind(String uid) {
        subscriptions.add(httpApi.getNewestRemindNumber(uid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        iView.dealNewestRemind(integer);
                    }
                }));
    }

    @Override
    public void getCenterBranchList() {
        subscriptions.add(httpApi.getCenterBranchList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> strings) {
                        for(int i=0;i<strings.size();i++) {
                            final int index = i + 1;
                            subscriptions.add(httpApi.getBranchList(index).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Action1<Map<Integer,String>>() {
                                        @Override
                                        public void call(Map<Integer,String> strings) {
                                            PreferenceHelper.saveBranch(index,strings);
                                            OaApplication.branchList.put(index, strings);
                                        }
                                    }));
                        }
                        PreferenceHelper.saveCenterBranch(strings);
                        OaApplication.centerBranchList = strings;
                    }
                }));
    }

    @Override
    public void getTaskTypeList() {
        subscriptions.add(httpApi.getTaskTypeList().subscribeOn(Schedulers.io())
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> strings) {
                        PreferenceHelper.saveTaskType(strings);
                        for(int i=1;i<=strings.size();i++) {
                            final int k = i;
                            subscriptions.add(httpApi.getNodeNameList(i).subscribeOn(Schedulers.io())
                                    .subscribe(new Action1<JSONObject>() {
                                        @Override
                                        public void call(JSONObject jsonObject) {
                                            if (jsonObject.length() > 0) {
                                                PreferenceHelper.saveNodeNameList(k, jsonObject.toString());
                                                OaApplication.nodeNameList.put(k + "", jsonObject);
                                                PreferenceHelper.saveNodeNum(k, jsonObject.length());
                                                OaApplication.nodeNumber.put(k + "", jsonObject.length());
                                            }
                                        }
                                    }));
                        }
                    }
                }));
    }

    @Override
    public void getDepartmentList() {
        subscriptions.add(httpApi.getDepartmentList().subscribeOn(Schedulers.io())
                .subscribe(new Action1<JSONObject>() {
                    @Override
                    public void call(JSONObject jsonObject) {
                        PreferenceHelper.saveDepartmentList(jsonObject.toString());
                        OaApplication.departmentList = jsonObject;
                    }
                }));
    }

    //from BasicPresenter.IPresenter
    @Override
    public void unSubscrib() {
        subscriptions.clear();
    }
}

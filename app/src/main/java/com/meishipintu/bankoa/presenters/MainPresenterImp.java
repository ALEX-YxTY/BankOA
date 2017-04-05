package com.meishipintu.bankoa.presenters;

import android.util.Log;

import com.meishipintu.bankoa.Constans;
import com.meishipintu.bankoa.OaApplication;
import com.meishipintu.bankoa.contracts.MainContract;
import com.meishipintu.bankoa.models.PreferenceHelper;
import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.bankoa.models.http.HttpApi;

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
    public void getVersionInfo() {
        //TODO 获取当前最新版本信息
    }

    //from BasicPresenter.IPresenter
    @Override
    public void unSubscrib() {
        subscriptions.clear();
    }
}

package com.meishipintu.bankoa.presenters;

import com.meishipintu.bankoa.OaApplication;
import com.meishipintu.bankoa.contracts.MainContract;
import com.meishipintu.bankoa.contracts.SupervisorContract;
import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.bankoa.models.http.HttpApi;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017/3/23.
 * <p>
 * 主要功能：
 */

public class SupervisorPresenterImp implements SupervisorContract.IPresenter {

    private SupervisorContract.IView iView;
    private HttpApi httpApi;
    private CompositeSubscription subscriptions;

    @Inject
    public SupervisorPresenterImp(SupervisorContract.IView view) {
        this.iView = view;
        httpApi = HttpApi.getInstance();
        subscriptions = new CompositeSubscription();
    }

    @Override
    public void getUserInfo(String uid) {
        subscriptions.add(httpApi.getUserInfoById(uid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserInfo>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        iView.showError("获取信息失败，请重新登录");
                    }

                    @Override
                    public void onNext(UserInfo userInfo) {
                        iView.refreshUI(userInfo);
                    }
                }));
    }

    //from BasicPresenter.IPresenter
    @Override
    public void unSubscrib() {
        subscriptions.clear();
    }
}

package com.meishipintu.bankoa.presenters;

import android.content.Context;
import android.util.Log;

import com.meishipintu.bankoa.Constans;
import com.meishipintu.bankoa.contracts.LoginContract;
import com.meishipintu.bankoa.contracts.TaskTriggerContract;
import com.meishipintu.bankoa.models.PreferenceHelper;
import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.bankoa.models.http.HttpApi;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017/3/13.
 * <p>
 * 主要功能：
 */

public class LoginPresenterImp implements LoginContract.IPresenter {

    private LoginContract.IView view;
    private CompositeSubscription subscriptions;
    private HttpApi httpApi;

    @Inject
    LoginPresenterImp(LoginContract.IView iView) {
        this.view = iView;
        httpApi = HttpApi.getInstance();
        this.subscriptions = new CompositeSubscription();
    }

    @Override
    public void login(String tel, final String psw, final boolean savePsw) {
        Subscription subscription = httpApi.login(tel, psw).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserInfo>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(UserInfo userInfo) {
                        Log.d(Constans.APP, userInfo.toString());
                        view.startMain(userInfo);
                        //TODO 保存Cookies用户信息及密码
                        if (savePsw) {
                            PreferenceHelper.savePsw(psw);
                        }
                    }
                });
        subscriptions.add(subscription);
    }

    //from BasicPresenter
    @Override
    public void unSubscrib() {
        subscriptions.clear();
    }
}

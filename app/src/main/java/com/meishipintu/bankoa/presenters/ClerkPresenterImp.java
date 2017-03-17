package com.meishipintu.bankoa.presenters;

import com.meishipintu.bankoa.contracts.ClerkContract;
import com.meishipintu.bankoa.contracts.LoginContract;
import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.bankoa.models.http.HttpApi;

import javax.inject.Inject;

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

public class ClerkPresenterImp implements ClerkContract.IPresenter {

    private ClerkContract.IView view;
    private CompositeSubscription subscriptions;
    private HttpApi httpApi;

    @Inject
    ClerkPresenterImp(ClerkContract.IView iView) {
        this.view = iView;
        httpApi = HttpApi.getInstance();
        this.subscriptions = new CompositeSubscription();
    }

    @Override
    public void getClerk(String uid) {
        //TODO 调用获取员工列表接口
    }

    //from BasicPresenter
    @Override
    public void unSubscrib() {
        subscriptions.clear();
    }
}

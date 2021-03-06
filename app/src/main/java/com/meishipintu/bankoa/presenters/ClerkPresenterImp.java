package com.meishipintu.bankoa.presenters;

import com.meishipintu.bankoa.contracts.ClerkContract;
import com.meishipintu.bankoa.contracts.LoginContract;
import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.bankoa.models.http.HttpApi;

import java.util.List;

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
    public void getClerk(String uid, String level) {
        subscriptions.add(httpApi.getClerkList(uid,level).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<UserInfo>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<UserInfo> userInfos) {
                        view.showCLerk(userInfos);
                    }
                }));
    }

    //from BasicPresenter
    @Override
    public void unSubscrib() {
        subscriptions.clear();
    }
}

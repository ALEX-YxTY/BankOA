package com.meishipintu.bankoa.presenters;

import com.meishipintu.bankoa.contracts.NoticContract;
import com.meishipintu.bankoa.models.entity.SysNotic;
import com.meishipintu.bankoa.models.entity.UpClassRemind;
import com.meishipintu.bankoa.models.http.HttpApi;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017/3/27.
 * <p>
 * 主要功能：
 */

public class NoticePresenterImp implements NoticContract.IPresenter{

    private NoticContract.IView iView;
    private HttpApi httpApi;
    private CompositeSubscription subscriptions;

    @Inject
    NoticePresenterImp(NoticContract.IView view) {
        this.iView = view;
        httpApi = HttpApi.getInstance();
        subscriptions = new CompositeSubscription();
    }

    @Override
    public void getRemind(String uid) {
        subscriptions.add(httpApi.getRemind(uid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<UpClassRemind>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        iView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<UpClassRemind> remindList) {
                        iView.showRemind(remindList);
                    }
                }));
    }

    @Override
    public void getSysNotic() {
        subscriptions.add(httpApi.getSysNotice().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<SysNotic>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        iView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<SysNotic> noticList) {
                        iView.showSysNotic(noticList);
                    }
                }));
    }

    //from BasicPresenter
    @Override
    public void unSubscrib() {
        subscriptions.clear();
    }

}

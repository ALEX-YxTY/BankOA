package com.meishipintu.bankoa.presenters;

import android.util.Log;

import com.meishipintu.bankoa.Constans;
import com.meishipintu.bankoa.contracts.PaymentEnterContract;
import com.meishipintu.bankoa.models.entity.PaymentInfo;
import com.meishipintu.bankoa.models.http.HttpApi;

import org.json.JSONObject;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017/3/21.
 * <p>
 * 主要功能：
 */

public class PaymentEnterPresenterImp implements PaymentEnterContract.IPresenter {

    private PaymentEnterContract.IView iView;
    private HttpApi httpApi;
    private CompositeSubscription subscriptions;

    @Inject
    PaymentEnterPresenterImp(PaymentEnterContract.IView view) {
        this.iView = view;
        httpApi = HttpApi.getInstance();
        subscriptions = new CompositeSubscription();
    }

    @Override
    public void getPaymentInfo(String taskId) {
        subscriptions.add(httpApi.getPaymentInfo(taskId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PaymentInfo>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        iView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(PaymentInfo paymentInfo) {
                        iView.onPaymentInfoGet(paymentInfo);
                    }
                }));
    }

    @Override
    public void enterPayment(PaymentInfo paymentInfo) {
        Log.d(Constans.APP, "post json:" + paymentInfo.toString());
        subscriptions.addAll(httpApi.addLoanInfo(paymentInfo).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        iView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        if (integer == 1) {
                            iView.onEnterSuccess();
                        } else {
                            iView.showError("信息录入失败，请稍后重试");
                        }
                    }
                }));
    }

    //from Basic.IPresenter
    @Override
    public void unSubscrib() {
        subscriptions.clear();
    }
}

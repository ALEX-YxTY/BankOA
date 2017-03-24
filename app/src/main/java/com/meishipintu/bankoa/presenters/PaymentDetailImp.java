package com.meishipintu.bankoa.presenters;

import android.nfc.Tag;
import android.util.Log;

import com.meishipintu.bankoa.Constans;
import com.meishipintu.bankoa.contracts.PaymentDetailContract;
import com.meishipintu.bankoa.models.entity.PaymentInfo;
import com.meishipintu.bankoa.models.http.HttpApi;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017/3/22.
 * <p>
 * 主要功能：
 */

public class PaymentDetailImp implements PaymentDetailContract.IPresenter {

    private static final String TAG = "BankOA-PaymentDetailImp";
    private PaymentDetailContract.IView iView;
    private HttpApi httpApi;
    private CompositeSubscription subscriptions;

    @Inject
    PaymentDetailImp(PaymentDetailContract.IView view) {
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
    public void finishPaymen(String taskid, String paymentId, String money) {
        Log.d(TAG, "id:money，" + paymentId + ":" + money);
        subscriptions.add(httpApi.finishPaymentNode(paymentId,taskid,money).subscribeOn(Schedulers.io())
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
                            iView.onPaymentFinish();
                        } else {
                            iView.showError("确认还款失败，请稍后重试");
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

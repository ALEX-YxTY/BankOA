package com.meishipintu.bankoa.presenters;

import android.util.Log;

import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.contracts.RegisterContract;
import com.meishipintu.bankoa.models.PreferenceHelper;
import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.bankoa.models.http.HttpApi;
import com.meishipintu.library.util.ToastUtils;

import javax.inject.Inject;

import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017/3/13.
 * <p>
 * 主要功能：
 */

public class RegisterPresenterImp implements RegisterContract.IPresenter {

    private HttpApi httpApi;
    private RegisterContract.IView view;
    private CompositeSubscription subscriptions;

    @Inject
    RegisterPresenterImp(RegisterContract.IView iView) {
        this.view = iView;
        httpApi = HttpApi.getInstance();
        subscriptions = new CompositeSubscription();
    }

    @Override
    public void getVerifyCode(final String tel) {
        //TODO 先调用检查是否通过审核接口
        subscriptions.add(httpApi.getRegisterStatus(tel).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showBtEnableTime(e.getMessage(), false);
                    }

                    @Override
                    public void onNext(Boolean s) {
                        if (s) {
                            //TODO 调用获取验证码接口
                            subscriptions.add(httpApi.getVerifyCode(tel).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Subscriber<String>() {
                                        @Override
                                        public void onCompleted() {
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            view.showToast("网络连接错误，请稍后重试");
                                        }

                                        @Override
                                        public void onNext(String s) {
                                            view.showBtEnableTime(s, true);
                                        }
                                    }));
                        } else {
                            view.showToast("该用户已注册或资料尚未通过审核");
                        }
                    }
                }));
    }

    @Override
    public void register(String tel, String verifyCode, String psw) {
        //TODO 调用注册接口
        subscriptions.add(httpApi.register(tel,verifyCode,psw).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<UserInfo>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(UserInfo userInfo) {
                        PreferenceHelper.saveUserInfo(userInfo);
                        view.showResult("注册成功");
                        Log.d("debug", "userInfo: " + userInfo.toString());
                    }
                }));

    }

    @Override
    public void changePsw(String tel, String verifyCode, String pswNew) {
        //TODO 调用修改密码接口
    }

    @Override
    public void rebingTel(String telNew, String verifyCode, String psw) {
        //TODO 调用重新绑定手机号接口
    }

    //from RegisterContract.IPresenter
    @Override
    public void unSubscrib() {
        subscriptions.clear();
    }
}

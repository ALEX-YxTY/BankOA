package com.meishipintu.bankoa.presenters;

import android.util.Log;

import com.meishipintu.bankoa.Constans;
import com.meishipintu.bankoa.OaApplication;
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
        //先调用检查是否通过审核接口
        subscriptions.add(httpApi.getRegisterStatus(tel)
                .subscribeOn(Schedulers.io())
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
                            //调用获取验证码接口
                            subscriptions.add(httpApi.getVerifyCode(tel).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Subscriber<String>() {
                                        @Override
                                        public void onCompleted() {
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            view.showBtEnableTime("网络连接错误，请稍后重试", false);
                                        }

                                        @Override
                                        public void onNext(String s) {
                                            view.showBtEnableTime(s, true);
                                        }
                                    }));
                        } else {
                            view.showBtEnableTime("该用户已注册或资料尚未通过审核", false);
                        }
                    }
                }));
    }

    @Override
    public void getVerifyCodeDerectly(String tel) {
        //调用获取验证码接口
        subscriptions.add(httpApi.getVerifyCode(tel).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showBtEnableTime("网络连接错误，请稍后重试", false);
                    }

                    @Override
                    public void onNext(String s) {
                        view.showBtEnableTime(s, true);
                    }
                }));
    }

    @Override
    public void register(String tel, String verifyCode, String psw) {
        //调用注册接口
        subscriptions.add(httpApi.register(tel,verifyCode,psw)
                .subscribeOn(Schedulers.io())
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
                        if (userInfo != null) {
                            PreferenceHelper.saveMobile(userInfo.getMobile());
                            OaApplication.setUser(userInfo);
                            view.showResult("注册成功", false);
                        } else {
                            view.showResult("注册失败，请稍后重试", false);
                        }
                    }
                }));
    }

    @Override
    public void changePsw(String tel, String verifyCode, String pswNew) {
        //调用修改密码接口
        subscriptions.add(httpApi.changePsw(tel, verifyCode, pswNew).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        if (integer != null) {
                            view.showResult("新改密码成功，请重新登录~", false);
                        } else {
                            view.showError("修改密码失败，请稍后重试");
                        }
                    }
                }));

    }

    @Override
    public void rebingTel(String telNew, String verifyCode, String uid, String psw) {
        //调用重新绑定手机号接口
        subscriptions.add(httpApi.changeMobile(telNew, verifyCode, uid, psw).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        if (integer != null) {
                            view.showResult("手机号已重新绑定，请重新登录~", true);
                        } else {
                            view.showError("手机号改绑失败，请稍后重试");
                        }
                    }
                }));
    }

    //from RegisterContract.IPresenter
    @Override
    public void unSubscrib() {
        subscriptions.clear();
    }
}

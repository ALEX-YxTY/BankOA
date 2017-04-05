package com.meishipintu.bankoa.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.meishipintu.bankoa.Constans;
import com.meishipintu.bankoa.OaApplication;
import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.components.DaggerRegisterComponent;
import com.meishipintu.bankoa.contracts.RegisterContract;
import com.meishipintu.bankoa.modules.RegisterModule;
import com.meishipintu.bankoa.presenters.RegisterPresenterImp;
import com.meishipintu.library.util.Encoder;
import com.meishipintu.library.util.StringUtils;
import com.meishipintu.library.util.ToastUtils;
import com.meishipintu.library.view.CircleImageView;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/1.
 * <p>
 * 注册页面/忘记密码重设页面共用
 */

public class RegisterActivity extends BasicActivity implements RegisterContract.IView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_subTitle)
    TextView tvSubTitle;
    @BindView(R.id.civ_head)
    CircleImageView civHead;
    @BindView(R.id.bt_verify)
    Button btVerify;
    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.et_verify_code)
    EditText etVerifyCode;
    @BindView(R.id.et_psw_new)
    EditText etPswNew;
    @BindView(R.id.bt_register)
    Button btRegister;

    int registerType;             //判断启动类型
    private String tel;
    private String verifyCodeGet;

    private int remainTime;        //等待时间60s

    @Inject
    RegisterPresenterImp mPresenter;

    private MyHandler handler = new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
        registerType = getIntent().getIntExtra("type", Constans.REGISTER_TYPE_NORMAL);
        initUI();

        //注入
        DaggerRegisterComponent.builder()
                .registerModule(new RegisterModule(this))
                .build().inject(this);
    }

    private void initUI() {
        switch (registerType) {
            case Constans.REGISTER_TYPE_NORMAL:
                tvTitle.setText(R.string.register);
                tvSubTitle.setText(R.string.apply_for);
                tvSubTitle.setVisibility(View.VISIBLE);
                btRegister.setText(R.string.register);
                break;
            case Constans.REGISTER_TYPE_FORGET_PSW:
                tvTitle.setText(R.string.forget_psw);
                btRegister.setText(R.string.reset_psw);
                etPswNew.setHint(R.string.psw_new_please);
                break;
            case Constans.REGISTER_TYPE_REBIND:
                tvTitle.setText(R.string.change_tel);
                btRegister.setText(R.string.ok);
                etTel.setHint(R.string.tel_new_please);
                etPswNew.setHint(R.string.psw_input_please);
                break;
        }
    }

    @OnClick({R.id.bt_back, R.id.tv_subTitle, R.id.bt_register, R.id.bt_verify})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_back:
                onBackPressed();
                break;
            case R.id.tv_subTitle:
                Intent intent = new Intent(RegisterActivity.this, WebViewActivity.class);
                intent.putExtra("type", Constans.APPLY_TYPE);
                startActivity(intent);
                break;
            case R.id.bt_verify:
                tel = etTel.getText().toString();
                if (!StringUtils.isTel(tel)) {
                    ToastUtils.show(this, R.string.err_tel_empty, true);
                } else {
                    if (btVerify.isEnabled()) {
                        remainTime = 60;
                        btVerify.setEnabled(false);
                        if (registerType == Constans.REGISTER_TYPE_REBIND) {
                            mPresenter.getVerifyCodeDerectly(etTel.getText().toString().trim());
                        } else {
                            mPresenter.getVerifyCode(etTel.getText().toString().trim());
                        }
                    }
                }
                break;
            case R.id.bt_register:
                tel = etTel.getText().toString();
                String codeInput = etVerifyCode.getText().toString();
                String psw = etPswNew.getText().toString().trim();
                if (StringUtils.isNullOrEmpty(tel) || StringUtils.isNullOrEmpty(codeInput)
                        || StringUtils.isNullOrEmpty(psw)) {
                    ToastUtils.show(this, R.string.err_empty_input, true);
                } else if (!codeInput.equals(verifyCodeGet)) {
                    ToastUtils.show(this, R.string.err_code, true);
                } else {
                    switch (registerType) {
                        case Constans.REGISTER_TYPE_NORMAL:
                            mPresenter.register(tel, codeInput, psw);
                            break;
                        case Constans.REGISTER_TYPE_FORGET_PSW:
                            mPresenter.changePsw(tel, codeInput, psw);
                            break;
                        case Constans.REGISTER_TYPE_REBIND:
                            mPresenter.rebingTel(tel, codeInput, OaApplication.getUser().getUid(), psw);
                            break;
                    }
                }
                break;
        }
    }

    //from RegisterContract.IView
    @Override
    public void showBtEnableTime(String codeGet, boolean success) {
        if (success) {
            verifyCodeGet = codeGet;
            //启动60秒等待线程
            handler.sendEmptyMessageDelayed(0, 1000);
        } else {
            btVerify.setEnabled(true);
            ToastUtils.show(this, codeGet, true);
        }
    }

    //from RegisterContract.IView
    @Override
    public void showResult(String result, boolean reLogin) {
        ToastUtils.show(this, result, true);
        if (!reLogin) {
            setResult(RESULT_OK);
        } else {
            //重新登錄
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("result", Constans.LOGOUT);
            startActivity(intent);
        }
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscrib();
        handler.removeMessages(0);
    }

    //from BasicView
    @Override
    public void showError(String errMsg) {
        ToastUtils.show(this, errMsg, true);
    }

    //handler设为内部静态类，防止handler持有activity对象导致内存泄漏
    private static class MyHandler extends Handler {
        private final WeakReference<RegisterActivity> reference;

        MyHandler(RegisterActivity activity){
            reference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            RegisterActivity activity = reference.get();
            if (activity.remainTime > 0) {
                this.sendEmptyMessageDelayed(0, 1000);
                activity.btVerify.setText(""+(--activity.remainTime)+"s");
            } else {
                activity.btVerify.setEnabled(true);
                activity.btVerify.setText(R.string.getVerifyCode);
            }
        }
    }

}


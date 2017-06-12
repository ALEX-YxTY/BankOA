package com.meishipintu.bankoa.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.models.PreferenceHelper;
import com.meishipintu.bankoa.models.entity.BranchUserInfo;
import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.bankoa.models.http.HttpApi;
import com.meishipintu.library.util.Encoder;
import com.meishipintu.library.util.StringUtils;
import com.meishipintu.library.util.ToastUtils;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/6/12.
 * <p>
 * 主要功能：分行查看的登录页面
 */

public class BranchSupervisorActivity extends BasicActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.et_psw)
    EditText etPsw;

    private HttpApi httpApi;
    private Subscription subscription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_branch);
        ButterKnife.bind(this);
        httpApi = HttpApi.getInstance();
        tvTitle.setText(R.string.branch_login);
        tvRegister.setVisibility(View.GONE);
        String branchName = PreferenceHelper.getBranchName();
        etTel.setText(branchName);
        String branchPw = PreferenceHelper.getBranchPw();
        etPsw.setText(branchPw);
    }

    @OnClick({R.id.et_tel, R.id.et_psw, R.id.bt_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                String tel = etTel.getText().toString();
                String psw = etPsw.getText().toString().trim();
                if (StringUtils.isNullOrEmpty(tel) || StringUtils.isNullOrEmpty(psw)) {
                    ToastUtils.show(this, R.string.err_empty_input, true);
                } else {
                    PreferenceHelper.saveBranchName(tel);
                    PreferenceHelper.saveBranchPsw(psw);
                    subscription = httpApi.loginBranch(tel, psw).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<BranchUserInfo>() {
                                @Override
                                public void onCompleted() {
                                }

                                @Override
                                public void onError(Throwable e) {
                                    ToastUtils.show(BranchSupervisorActivity.this, e.getMessage(), true);
                                }

                                @Override
                                public void onNext(BranchUserInfo branchUserInfo) {
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("branch", branchUserInfo);
                                    Intent intent = new Intent(BranchSupervisorActivity.this, BranchTaskActivity.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            });
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }
}

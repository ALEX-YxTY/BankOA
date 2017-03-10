package com.meishipintu.bankoa.views.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.meishipintu.bankoa.R;
import com.meishipintu.library.view.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created on 2017/3/8
 * <p>
 * 修改密码/修改手机号页面
 */

public class ForgetPswActivity extends BasicActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.civ_head)
    CircleImageView civHead;
    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.et_verify_code)
    EditText etVerifyCode;
    @BindView(R.id.et_psw_new)
    EditText etPswNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_psw);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_back, R.id.bt_verify, R.id.bt_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_back:
                break;
            case R.id.bt_verify:
                break;
            case R.id.bt_login:
                break;
        }
    }
}

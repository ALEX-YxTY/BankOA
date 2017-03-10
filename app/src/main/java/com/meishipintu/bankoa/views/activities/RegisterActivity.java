package com.meishipintu.bankoa.views.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.meishipintu.bankoa.R;
import com.meishipintu.library.view.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/1.
 * <p>
 * 注册页面
 */

public class RegisterActivity extends BasicActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_subTitle)
    TextView tvSubTitle;
    @BindView(R.id.civ_head)
    CircleImageView civHead;
    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.bt_verify)
    Button btVerify;
    @BindView(R.id.et_verify_code)
    EditText etVerifyCode;
    @BindView(R.id.et_psw_new)
    EditText etPswNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_back, R.id.tv_subTitle, R.id.bt_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_back:
                break;
            case R.id.tv_subTitle:
                break;
            case R.id.bt_register:
                break;
        }
    }
}

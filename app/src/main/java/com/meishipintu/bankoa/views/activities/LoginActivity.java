package com.meishipintu.bankoa.views.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.meishipintu.bankoa.R;
import com.meishipintu.library.view.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/1.
 * <p>
 * 登录页面
 */

public class LoginActivity extends BasicActivity {

    @BindView(R.id.civ_head)
    CircleImageView civHead;
    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.et_psw)
    EditText etPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {
        
    }

    @OnClick({R.id.tv_register, R.id.tv_forget_psw, R.id.tv_save_psw,R.id.bt_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                break;
            case R.id.tv_forget_psw:
                break;
            case R.id.tv_save_psw:
                break;
            case R.id.bt_login:
                break;
        }
    }
}

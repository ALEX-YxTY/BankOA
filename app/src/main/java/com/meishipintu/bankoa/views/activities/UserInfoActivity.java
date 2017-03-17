package com.meishipintu.bankoa.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.meishipintu.bankoa.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserInfoActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.loan_no)
    TextView tvLoanNo;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.tv_work_no)
    TextView tvWorkNo;
    @BindView(R.id.tv_tel)
    TextView tvTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {

    }

    @OnClick({R.id.bt_back, R.id.rl_change_tel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_back:
                onBackPressed();
                break;
            case R.id.rl_change_tel:
                Intent intent = new Intent(UserInfoActivity.this, BindTelActivity.class);
                intent.putExtra("tel", tvTel.getText().toString());
                startActivity(intent);
                break;
        }
    }
}

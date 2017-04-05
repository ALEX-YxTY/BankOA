package com.meishipintu.bankoa.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.meishipintu.bankoa.Constans;
import com.meishipintu.bankoa.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BindTelActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_tel)
    TextView tvTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_tel);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.change_tel);
        tvTel.setText(getIntent().getStringExtra("tel"));
    }

    @OnClick({R.id.bt_back, R.id.bt_change_tel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_back:
                onBackPressed();
                break;
            case R.id.bt_change_tel:
                Intent intent = new Intent(BindTelActivity.this, RegisterActivity.class);
                intent.putExtra("type", Constans.REGISTER_TYPE_REBIND);
                startActivity(intent);
                break;
        }
    }
}

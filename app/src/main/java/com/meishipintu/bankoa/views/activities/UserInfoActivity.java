package com.meishipintu.bankoa.views.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.meishipintu.bankoa.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
    }
}

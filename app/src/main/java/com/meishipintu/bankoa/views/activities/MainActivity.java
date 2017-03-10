package com.meishipintu.bankoa.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.meishipintu.bankoa.R;
import com.meishipintu.library.view.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created 2017-3-1
 * <p>
 * 主界面，功能模块展示
 */

public class MainActivity extends BasicActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.civ_head)
    CircleImageView civHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_job_title)
    TextView tvJobTitle;
    @BindView(R.id.tv_number)
    TextView tvNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initUI();
    }

    //初始化界面
    private void initUI() {

    }

    @OnClick({R.id.message, R.id.task, R.id.task_trigger, R.id.check, R.id.bt_setting, R.id.bt_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.message:
                startActivity(new Intent(MainActivity.this, NoticActivity.class));
                break;
            case R.id.task:
                startActivity(new Intent(MainActivity.this, TaskActivity.class));
                break;
            case R.id.task_trigger:
                startActivity(new Intent(MainActivity.this, TaskTriggerActivity.class));
                break;
            case R.id.check:
                startActivity(new Intent(MainActivity.this, ClerkListActivity.class));
                break;
            case R.id.bt_setting:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
            case R.id.bt_search:
                break;
        }
    }
}

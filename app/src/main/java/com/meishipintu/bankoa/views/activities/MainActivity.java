package com.meishipintu.bankoa.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meishipintu.bankoa.Constans;
import com.meishipintu.bankoa.OaApplication;
import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.components.DaggerMainComponent;
import com.meishipintu.bankoa.contracts.MainContract;
import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.bankoa.modules.MainModule;
import com.meishipintu.bankoa.presenters.MainPresenterImp;
import com.meishipintu.library.view.CircleImageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created 2017-3-1
 * <p>
 * 主界面，功能模块展示
 */

public class MainActivity extends BasicActivity implements MainContract.IView {


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
    @BindView(R.id.red_point)
    View redPoint;
    @BindView(R.id.check)
    RelativeLayout rlCheck;

    @Inject
    MainPresenterImp mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        DaggerMainComponent.builder().mainModule(new MainModule(this))
                .build().inject(this);
        initUI();
    }

    private void initUI() {
        tvTitle.setText(R.string.homePage);
        mPresenter.getUserInfo();
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
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                break;
        }
    }

    //from MainContract.IView
    @Override
    public void refreshUI(UserInfo userInfo) {
        Log.d(Constans.APP, "userInfo login:" + userInfo.toString());
        tvName.setText(userInfo.getUser_name());
        tvNumber.setText("工号：" + userInfo.getJob_number());
        String department,title;
        if (userInfo.getLevel().equals("1")) {
            department = "";
        } else {
            department = userInfo.getDepartment_name();
        }
        switch (Integer.parseInt(userInfo.getLevel())) {
            case 1:
                title = "行长";
                break;
            case 2:
                title = "经理";
                break;
            default:
                title = "员工";
                break;
        }
        tvJobTitle.setText(department + " " + title);
        if (Integer.parseInt(userInfo.getLevel()) < 3) {
            rlCheck.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscrib();
    }
}

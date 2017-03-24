package com.meishipintu.bankoa.views.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meishipintu.bankoa.OaApplication;
import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.components.DaggerSupervisorComponent;
import com.meishipintu.bankoa.contracts.SupervisorContract;
import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.bankoa.modules.SupervisorModule;
import com.meishipintu.bankoa.presenters.MainPresenterImp;
import com.meishipintu.bankoa.presenters.SupervisorPresenterImp;
import com.meishipintu.library.util.ToastUtils;
import com.meishipintu.library.view.CircleImageView;

import org.json.JSONException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SupervisorActivity extends AppCompatActivity implements SupervisorContract.IView{

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
    SupervisorPresenterImp mPresenter;

    private String uid;
    private String userLevel;
    private String supervisorId;
    private String supervisorLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisor);
        ButterKnife.bind(this);

        DaggerSupervisorComponent.builder().supervisorModule(new SupervisorModule(this))
                .build().inject(this);

        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        userLevel = intent.getStringExtra("user_level");
        supervisorId = intent.getStringExtra("supervisor_id");
        supervisorLevel = intent.getStringExtra("supervisor_level");
    }

    @Override
    protected void onResume() {
        super.onResume();
        initUI();
    }

    private void initUI() {
        mPresenter.getUserInfo(uid);
    }

    @OnClick({R.id.message, R.id.task, R.id.task_trigger, R.id.check, R.id.bt_search})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.message:
                intent= new Intent(SupervisorActivity.this, NoticActivity.class);
                break;
            case R.id.task:
                intent = new Intent(SupervisorActivity.this, TaskActivity.class);
                break;
            case R.id.task_trigger:
                intent = new Intent(SupervisorActivity.this, TaskTriggerActivity.class);
                break;
            case R.id.check:
                intent = new Intent(SupervisorActivity.this, ClerkListActivity.class);
                break;
            case R.id.bt_search:
                intent = new Intent(SupervisorActivity.this, SearchActivity.class);
                break;
            case R.id.bt_back:
                onBackPressed();
                break;
        }
        if (intent != null) {
            intent.putExtra("uid", uid);
            intent.putExtra("user_level", userLevel);
            intent.putExtra("supervisor_id", supervisorId);
            intent.putExtra("supervisor_level", supervisorLevel);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscrib();
    }

    //from SupervisorContract.IView
    @Override
    public void refreshUI(UserInfo userInfo) {
        tvTitle.setText(userInfo.getUser_name() + "的主页");
        tvName.setText(userInfo.getUser_name());
        tvNumber.setText("工号：" + userInfo.getJob_number());
        String department,title;
        if (userInfo.getLevel().equals("1")) {
            department = "";
        } else {
            try {
                department = OaApplication.departmentList.getString(userInfo.getDepartment_id());
            } catch (JSONException e) {
                e.printStackTrace();
                department = "";
            }
        }
        switch (Integer.parseInt(userInfo.getLevel())) {
            case 2:
                title = "经理";
                break;
            default:
                title = "业务员";
                break;
        }
        tvJobTitle.setText(department + " " + title);
        if (Integer.parseInt(userInfo.getLevel()) < 3) {
            rlCheck.setVisibility(View.VISIBLE);
        }
    }

    //from BasicView
    @Override
    public void showError(String errMsg) {
        //获取信息失败，返回上级界面
        ToastUtils.show(this, errMsg, true);
        this.finish();
    }

}

package com.meishipintu.bankoa.views.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.meishipintu.bankoa.Constans;
import com.meishipintu.bankoa.OaApplication;
import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.components.DaggerTaskTriggrtComponent;
import com.meishipintu.bankoa.contracts.TaskTriggerContract;
import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.modules.TaskTriggrtModule;
import com.meishipintu.bankoa.presenters.TaskTriggerPresenterImp;
import com.meishipintu.library.util.StringUtils;
import com.meishipintu.library.util.ToastUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/1.
 * <p>
 * 发起任务页面
 */

public class TaskTriggerActivity extends BasicActivity implements TaskTriggerContract.IView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_task_name)
    EditText etTaskName;
    @BindView(R.id.et_loans_name)
    EditText etLoansName;
    @BindView(R.id.et_loans_money)
    EditText etLoansMoney;
    @BindView(R.id.tv_center_branch)
    TextView tvCenterBranch;
    @BindView(R.id.tv_branch)
    TextView tvBranch;
    @BindView(R.id.et_recommend_manager)
    EditText etRecommendManager;
    @BindView(R.id.tv_type)
    TextView tvType;


    @Inject
    TaskTriggerPresenterImp mPresenter;

    private String sponsorId;
    private String sponsorLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_trigger);
        ButterKnife.bind(this);
        init();

        //通过Dagger注入
        DaggerTaskTriggrtComponent.builder()
                //导入依赖Component
                .applicationComponent(OaApplication.getInstance().getApplicationComponent())
                //导入依赖Module
                .taskTriggrtModule(new TaskTriggrtModule(this))
                .build()
                .inject(this);

    }

    private void init() {
        sponsorId = getIntent().getStringExtra("uid");
        sponsorLevel = getIntent().getStringExtra("user_level");
        if (sponsorId == null) {
            sponsorId = OaApplication.getUser().getUid();
            sponsorLevel = OaApplication.getUser().getLevel();
        }
        tvTitle.setText(R.string.trigger_task);
    }

    @OnClick({R.id.bt_back, R.id.ll_center_branch, R.id.ll_branch, R.id.ll_type, R.id.bt_trigger})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_back:
                onBackPressed();
                break;
            case R.id.ll_center_branch:
//                mPresenter.getCenteralBranches();
                break;
            case R.id.ll_branch:
//                mPresenter.getBranches();
                break;
            case R.id.ll_type:
//                mPresenter.getTaskType();
                break;
            case R.id.bt_trigger:
                String taskName = etTaskName.getText().toString();
                String loanerName = etLoansName.getText().toString();
                String loanMoney = etLoansMoney.getText().toString();
                String recommendManager = etRecommendManager.getText().toString();
                String centerBranch = tvCenterBranch.getText().toString();
                String branch = tvBranch.getText().toString();
                String type = tvType.getText().toString();
                if (StringUtils.isNullOrEmpty(new String[]{taskName, loanerName, loanMoney, recommendManager
                        , centerBranch, branch, type})) {
                    ToastUtils.show(this, R.string.err_empty_input, true);
                } else {
                    mPresenter.triggerTask(loanerName, loanMoney, "1", "1", "1", taskName
                            , recommendManager, sponsorId, sponsorLevel);
                }
                break;
        }
    }


    //from TaskTriggerContract.IView
    @Override
    public void showCenteralBranches(List<String> districts) {

    }

    //from TaskTriggerContract.IView
    @Override
    public void showBranches(List<String> branches) {

    }

    //from TaskTriggerContract.IView
    @Override
    public void showTaskType(List<String> type) {

    }

    //from TaskTriggerContract.IView
    @Override
    public void showTriggerResult(String result, boolean success, Task task) {
        if (!success) {
            ToastUtils.show(this, result, true);
        } else {
            Log.d(Constans.APP, "task triggered:" + task.toString());
            Intent intent = new Intent(TaskTriggerActivity.this, TaskDetailActivity.class);
            intent.putExtra("task", task);
            startActivity(intent);
            this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.unSubscrib();
        super.onDestroy();
    }

    //from BasicView
    @Override
    public void showError(String errMsg) {
        ToastUtils.show(this, errMsg, true);
    }
}

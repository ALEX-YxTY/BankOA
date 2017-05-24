package com.meishipintu.bankoa.views.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
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
import com.meishipintu.library.view.CustomNumPickeDialog;

import java.util.Arrays;
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

    private static final String TAG = "BankOA-TaskTrigger";
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

    private String supervisorId;    //监管人id
    private String supervisorLevel;    //监管人level

    private Dialog dialog;

    private int centerBranch = 0;
    private int branch = 0;
    private int type = 0;

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
        supervisorId = getIntent().getStringExtra("supervisor_id");
        supervisorLevel = getIntent().getStringExtra("supervisor_level");
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
                mPresenter.getCenteralBranches();
                break;
            case R.id.ll_branch:
                if (centerBranch == 0) {
                    //中心支行未选择
                    showError("请先选择中心支行");
                } else {
                    mPresenter.getBranches(centerBranch);
                }
                break;
            case R.id.ll_type:
                mPresenter.getTaskType();
                break;
            case R.id.bt_trigger:
                String taskName = etTaskName.getText().toString();
                String loanerName = etLoansName.getText().toString();
                String loanMoney = etLoansMoney.getText().toString();
                String recommendManager = etRecommendManager.getText().toString();
                String[] specialCenterBranch = new String[]{"分行营业部", "溧水支行", "高淳支行"};
                if (StringUtils.isNullOrEmpty(new String[]{taskName, loanerName, loanMoney, recommendManager
                        , tvCenterBranch.getText().toString(), tvType.getText().toString()})
                        || (StringUtils.isNullOrEmpty(tvBranch.getText().toString())
                        && !StringUtils.contains(specialCenterBranch, tvCenterBranch.getText().toString()))) {
                    ToastUtils.show(this, R.string.err_empty_input, true);
                } else {
                    Log.d(TAG, "centerBranch:" + centerBranch + " ,branch:" + branch);
                    mPresenter.triggerTask(loanerName, loanMoney, centerBranch, branch, type, taskName
                            , recommendManager, sponsorId, sponsorLevel);
                }
                break;
        }
    }


    //from TaskTriggerContract.IView
    @Override
    public void showCenteralBranches(final String[] districts) {
        dialog = new CustomNumPickeDialog(this, R.style.DialogNoAction, districts, new CustomNumPickeDialog.OnOkClickListener() {
            @Override
            public void onOkClick(int vlueChoose) {
                if (centerBranch != vlueChoose + 1) {
                    //与上次选择不同
                    branch = 0;
                    tvBranch.setText("");
                }
                centerBranch = vlueChoose + 1;
                tvCenterBranch.setText(districts[vlueChoose]);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //from TaskTriggerContract.IView
    @Override
    public void showBranches(final String[] branches) {
        dialog = new CustomNumPickeDialog(this, R.style.DialogNoAction, branches, new CustomNumPickeDialog.OnOkClickListener() {
            @Override
            public void onOkClick(int vlueChoose) {
                branch = vlueChoose + 1;
                tvBranch.setText(branches[vlueChoose]);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    //from TaskTriggerContract.IView
    @Override
    public void showTaskType(final String[] types) {
        dialog = new CustomNumPickeDialog(this, R.style.DialogNoAction, types, new CustomNumPickeDialog.OnOkClickListener() {
            @Override
            public void onOkClick(int vlueChoose) {
                type = vlueChoose + 1;
                tvType.setText(types[vlueChoose]);
                dialog.dismiss();
            }
        });
        dialog.show();
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
            if (supervisorId != null) {
                intent.putExtra("supervisor_id", supervisorId);
                intent.putExtra("supervisor_level", supervisorLevel);
            }
            startActivity(intent);
            this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.unSubscrib();
        if (dialog != null&&dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = null;
        super.onDestroy();
    }

    //from BasicView
    @Override
    public void showError(String errMsg) {
        ToastUtils.show(this, errMsg, true);
    }
}

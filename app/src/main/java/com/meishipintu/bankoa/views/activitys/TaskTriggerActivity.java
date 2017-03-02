package com.meishipintu.bankoa.views.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.contracts.TaskTriggerContract;
import com.meishipintu.bankoa.presenters.TaskTriggetPresenterImp;

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

public class TaskTriggerActivity extends BasicActivity implements TaskTriggerContract.IView{

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_loans_name)
    EditText etLoansName;
    @BindView(R.id.et_loans_company)
    EditText etLoansCompany;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.et_district)
    EditText etDistrict;
    @BindView(R.id.et_branch)
    EditText etBranch;
    @BindView(R.id.et_remark)
    EditText etRemark;

    @Inject
    TaskTriggetPresenterImp presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_trigger);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

    }

    @OnClick({R.id.bt_back, R.id.bt_trigger})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_back:
                break;
            case R.id.bt_trigger:
                break;
        }
    }

    //from TaskTriggerContract.IView
    @Override
    public void showDistricts(List<String> districts) {

    }

    //from TaskTriggerContract.IView
    @Override
    public void showBranches(List<String> branches) {

    }
}

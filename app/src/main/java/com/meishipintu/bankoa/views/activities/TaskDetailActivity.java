package com.meishipintu.bankoa.views.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.meishipintu.bankoa.Constans;
import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.components.DaggerTaskDetailComponent;
import com.meishipintu.bankoa.contracts.TaskDetailContract;
import com.meishipintu.bankoa.models.entity.NodeInfoNow;
import com.meishipintu.bankoa.models.entity.RemarkInfo;
import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.bankoa.modules.TaskDetailModule;
import com.meishipintu.bankoa.presenters.TaskDetailPresenterImp;
import com.meishipintu.bankoa.views.adapter.RemarkAdapter;
import com.meishipintu.library.util.DateUtil;
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
 * 功能介绍：任务详情页面（小）
 */

public class TaskDetailActivity extends BasicActivity implements TaskDetailContract.IView{

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.process_line_left1)
    View processLineLeft1;
    @BindView(R.id.process_line_left2)
    View processLineLeft2;
    @BindView(R.id.process_line_right1)
    View processLineRight1;
    @BindView(R.id.process_line_right2)
    View processLineRight2;
    @BindView(R.id.iv_process_left)
    ImageView ivProcessLeft;
    @BindView(R.id.tv_process_left)
    TextView tvProcessLeft;
    @BindView(R.id.iv_process_right)
    ImageView ivProcessRight;
    @BindView(R.id.tv_process_right)
    TextView tvProcessRight;
    @BindView(R.id.tv_process_now)
    TextView tvProcessNow;
    @BindView(R.id.tv_percentage)
    TextView tvPercentage;
    @BindView(R.id.tv_time_remain)
    TextView tvTimeRemain;
    @BindView(R.id.tv_isOutOfTime)
    TextView tvOutOfTime;

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_job_title)
    TextView tvJobTitle;

    @BindView(R.id.lv_remark)
    RecyclerView lvRemark;
    @BindView(R.id.rv_comment)
    RecyclerView rvComment;

    @BindView(R.id.tv_add_remark)
    TextView tvAddRemark;
    @BindView(R.id.et_remark)
    EditText etRemark;

    private String taskId;          //任务id
    private String sponsorId;       //发起人id
    private String supervisorId;    //监管人id
    private String taskName;        //项目名
    private String taskLevelNow;
    private RemarkAdapter remarkAdapter;        //备注Adapter


    @Inject
    TaskDetailPresenterImp mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        ButterKnife.bind(this);
        Task task = (Task) getIntent().getSerializableExtra("task");
        taskId = task.getId();
        sponsorId = task.getSponsor_id();
        taskName = task.getTask_name();
        supervisorId = getIntent().getStringExtra("supervisorId");

        DaggerTaskDetailComponent.builder().taskDetailModule(new TaskDetailModule(this))
                .build().inject(this);
        initUI();
    }

    private void initUI() {
        tvTitle.setText(taskName);
        mPresenter.getTaskInfo(taskId);
        if (supervisorId != null) {
            tvAddRemark.setText(R.string.comment);
            etRemark.setHint(R.string.comment_please);
        }
    }

    @OnClick({R.id.bt_back,R.id.bt_finish, R.id.bt_see_all, R.id.tv_add_remark})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                break;
            case R.id.bt_see_all:
                break;
            case R.id.tv_add_remark:
                String input = etRemark.getText().toString();
                if (StringUtils.isNullOrEmpty(input)) {
                    ToastUtils.show(this, R.string.err_empty_input, true);
                }
                if (supervisorId == null) {
                    mPresenter.addNodeRemarks(new RemarkInfo(taskId, taskLevelNow, input, sponsorId));
                } else {
                    mPresenter.addNodeComment(supervisorId, input);
                }
                break;
            case R.id.bt_back:
                onBackPressed();
                break;
        }
    }

    //from TaskDetailContract.IView
    @Override
    public void showError(String errMsg) {
        ToastUtils.show(this, errMsg, true);
    }

    //from TaskDetailContract.IView
    @Override
    public void showGraphic(NodeInfoNow nodeInfoNow) {
        taskLevelNow = nodeInfoNow.getNodeNowLevel();
        int level = Integer.parseInt(taskLevelNow);
        switch (level) {
            case 1:
                processLineLeft2.setVisibility(View.INVISIBLE);
                tvProcessLeft.setVisibility(View.INVISIBLE);
                ivProcessLeft.setVisibility(View.INVISIBLE);
            case 2:
                processLineLeft1.setVisibility(View.INVISIBLE);
                break;
            case 24:
                processLineRight2.setVisibility(View.INVISIBLE);
                tvProcessRight.setVisibility(View.INVISIBLE);
                ivProcessRight.setVisibility(View.INVISIBLE);
            case 23:
                processLineRight1.setVisibility(View.INVISIBLE);
                break;
            default:
                processLineLeft1.setVisibility(View.VISIBLE);
                processLineLeft2.setVisibility(View.VISIBLE);
                tvProcessLeft.setVisibility(View.VISIBLE);
                ivProcessLeft.setVisibility(View.VISIBLE);
                processLineRight1.setVisibility(View.VISIBLE);
                processLineRight2.setVisibility(View.VISIBLE);
                tvProcessRight.setVisibility(View.VISIBLE);
                ivProcessRight.setVisibility(View.VISIBLE);
                break;
        }
        if (nodeInfoNow.getNodeBeforeName() != null) {
            tvProcessLeft.setText(nodeInfoNow.getNodeBeforeName());
        }
        if (nodeInfoNow.getNodeAfterName() != null) {
            tvProcessRight.setText(nodeInfoNow.getNodeAfterName());
        }
        if (System.currentTimeMillis() / 1000 >= Long.parseLong(nodeInfoNow.getTimeRemain())) {
            tvOutOfTime.setText("超时 ");
            tvOutOfTime.setTextColor(0xffff6c5d);
        } else {
            tvOutOfTime.setText(R.string.time_remain);
            tvOutOfTime.setTextColor(0xff9ca7b2);
        }
        tvProcessNow.setText(nodeInfoNow.getNodeNowName());
        Log.d(Constans.APP, "time:" + DateUtil.showTimeRemain(nodeInfoNow.getTimeRemain()));
        tvTimeRemain.setText(DateUtil.showTimeRemain(nodeInfoNow.getTimeRemain()));
        tvPercentage.setText("" + (level * 100 / 24));
    }

    //from TaskDetailContract.IView
    @Override
    public void showUserInfo(UserInfo userInfo) {
        tvName.setText(userInfo.getUser_name()+" (工号： "+userInfo.getJob_number()+")");
        String title;
        switch (Integer.parseInt(userInfo.getLevel())) {
            case 1:
                title = "行长";
                break;
            case 2:
                title = "主管";
                break;
            case 3:
                title = "员工";
                break;
            default:
                title = "员工";
                break;
        }
        if (Integer.parseInt(userInfo.getLevel()) == 1) {
            tvJobTitle.setText(title);
        } else {
            tvJobTitle.setText(userInfo.getDepartment_name() + " " + title);
        }

    }

    //from TaskDetailContract.IView
    @Override
    public void showRemarks(List<RemarkInfo> remarkInfoList) {
        Log.d(Constans.APP, "data size:" + remarkInfoList.size());
        if (remarkAdapter == null) {
            lvRemark.setItemAnimator(new DefaultItemAnimator());
            lvRemark.setLayoutManager(new LinearLayoutManager(this));
            remarkAdapter = new RemarkAdapter(this, remarkInfoList);
            lvRemark.setAdapter(remarkAdapter);
        } else {
            List<RemarkInfo> dataList = remarkAdapter.getDataList();
            dataList.clear();
            dataList.addAll(remarkInfoList);
            remarkAdapter.notifyDataSetChanged();
        }
    }

    //from TaskDetailContract.IView
    @Override
    public void onAddRemarkSucess() {
        //TODO 刷新评论列表
    }
    @Override
    protected void onDestroy() {
        mPresenter.unSubscrib();
        super.onDestroy();
    }
}

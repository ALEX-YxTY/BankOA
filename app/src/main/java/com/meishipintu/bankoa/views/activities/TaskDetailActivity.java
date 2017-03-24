package com.meishipintu.bankoa.views.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.meishipintu.bankoa.Constans;
import com.meishipintu.bankoa.OaApplication;
import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.components.DaggerTaskDetailComponent;
import com.meishipintu.bankoa.contracts.TaskDetailContract;
import com.meishipintu.bankoa.models.entity.CommentDetail;
import com.meishipintu.bankoa.models.entity.CommentInfo;
import com.meishipintu.bankoa.models.entity.NodeInfoNow;
import com.meishipintu.bankoa.models.entity.RemarkInfo;
import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.bankoa.modules.TaskDetailModule;
import com.meishipintu.bankoa.presenters.TaskDetailPresenterImp;
import com.meishipintu.bankoa.views.adapter.CommentsAdapter;
import com.meishipintu.bankoa.views.adapter.RemarkAdapter;
import com.meishipintu.bankoa.views.adapter.ReplyClickListener;
import com.meishipintu.library.util.DateUtil;
import com.meishipintu.library.util.StringUtils;
import com.meishipintu.library.util.ToastUtils;

import java.util.ArrayList;
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

    private static final String TAG = "BankOA-taskDetail";
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
    @BindView(R.id.bt_finish)
    Button btFinish;

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
    @BindView(R.id.scroll)
    ScrollView scrollView;

    private String sponsorId;       //发起人id
    private String supervisorId;    //监管人id
    private String supervisorLevel;    //监管人level


    private String taskName;        //项目名
    private String taskId;          //任务id
    private String taskLevelNow;
    private RemarkAdapter remarkAdapter;        //备注Adapter
    private CommentsAdapter commentsAdapter;    //评论Adapter
    private String pid = "0";         //默认评论父节点为0
    private InputMethodManager inputService;     //软键盘管理者


    @Inject
    TaskDetailPresenterImp mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        ButterKnife.bind(this);
        Task task = (Task) getIntent().getSerializableExtra("task");
        taskId = task.getId();
        taskName = task.getTask_name();
        sponsorId = task.getSponsor_id();
        supervisorId = getIntent().getStringExtra("supervisor_id");
        supervisorLevel = getIntent().getStringExtra("supervisor_level");
        inputService = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //评论增加监听，在失焦时重置pid
        etRemark.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d(TAG, "focus changed:" + hasFocus);
                if (!hasFocus) {
                    etRemark.setText("");
                    pid = "0";
                    btFinish.requestFocus();
                }
            }
        });
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
                if (Integer.parseInt(taskLevelNow) < OaApplication.nodeNumber) {
                    mPresenter.setTaskNodeFinished(supervisorId == null ? sponsorId : supervisorId, taskId);
                } else {
                    Intent intent = new Intent(this, PaymentEnterActivity.class);
                    intent.putExtra("task_id", taskId);
                    startActivityForResult(intent,Constans.FINISH_AND_INPUT);
                }
                break;
            case R.id.bt_see_all:
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("task_id", taskId);
                intent.putExtra("uid", supervisorId == null ? sponsorId : supervisorId);
                startActivity(intent);
                break;
            case R.id.tv_add_remark:
                String input = etRemark.getText().toString();
                if (StringUtils.isNullOrEmpty(input)) {
                    ToastUtils.show(this, R.string.err_empty_input, true);
                }
                if (supervisorId == null) {
                    mPresenter.addNodeRemarks(taskId, taskLevelNow, input, sponsorId);
                } else {
                    mPresenter.addNodeComment(new CommentInfo(supervisorId, supervisorLevel, taskId
                            , taskLevelNow, input, pid));
                }
                break;
            case R.id.bt_back:
                onBackPressed();
                break;
        }
    }

    private void changSoftInputWindow(boolean close) {
        //如果软键盘在显示，则强制隐藏
        if (close && inputService.isActive()) {
            inputService.hideSoftInputFromWindow(etRemark.getWindowToken(), 0);
        }
        if (!close) {
            inputService.showSoftInput(etRemark,InputMethodManager.SHOW_FORCED);
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
                processLineLeft1.setVisibility(View.INVISIBLE);
                break;
            case 2:
                processLineLeft1.setVisibility(View.INVISIBLE);
                processLineLeft2.setVisibility(View.VISIBLE);
                tvProcessLeft.setVisibility(View.VISIBLE);
                ivProcessLeft.setVisibility(View.VISIBLE);
                break;
            case 24:
                btFinish.setText(R.string.input);
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
            tvOutOfTime.setText("距离此节点截止时间还有 ");
            tvOutOfTime.setTextColor(0xff9ca7b2);
        }
        tvProcessNow.setText(nodeInfoNow.getNodeNowName());
        Log.d(Constans.APP, "time:" + DateUtil.showTimeRemain(nodeInfoNow.getTimeRemain()));
        tvTimeRemain.setText(DateUtil.showTimeRemain(nodeInfoNow.getTimeRemain()));
        tvPercentage.setText("" + ((level-1) * 100 / 24));
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
    public void showComments(List<CommentDetail> commentDetailList) {
        List<CommentDetail> commentDetailData = new ArrayList<>();
        //遍历数组后将线性结构转换为树状结构
        for (CommentDetail commentDetail : commentDetailList) {
            if ("0".equals(commentDetail.getPid())) {
                //一级评论
                commentDetailData.add(commentDetail);
            } else {
                for (CommentDetail firstLevelComment : commentDetailData) {
                    if (commentDetail.getPid().equals(firstLevelComment.getId())) {
                        firstLevelComment.setComment(commentDetail);
                    }
                }
            }
        }
        if (commentsAdapter == null) {
            rvComment.setItemAnimator(new DefaultItemAnimator());
            rvComment.setLayoutManager(new LinearLayoutManager(this));
            commentsAdapter = new CommentsAdapter(this, commentDetailData, new ReplyClickListener() {
                @Override
                public void onClick(String id) {
                    pid = id;
                    etRemark.requestFocus();
                    changSoftInputWindow(false);
                }
            });
            rvComment.setAdapter(commentsAdapter);
        } else {
            List<CommentDetail> dataList = commentsAdapter.getDataList();
            dataList.clear();
            dataList.addAll(commentDetailData);
            commentsAdapter.notifyDataSetChanged();
        }
    }

    //from TaskDetailContract.IView
    @Override
    public void onAddSuccess(int type) {
        ToastUtils.show(this, type == 1 ? R.string.add_comment_success : R.string.add_remark_success, true);
        etRemark.setText("");
        etRemark.clearFocus();
        changSoftInputWindow(true);
        //刷新界面
        mPresenter.getTaskInfo(taskId);
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }

    //from TaskDetailContract.IView
    @Override
    public void onFinishNode() {
        if (taskLevelNow.equals(OaApplication.nodeNumber + "")) {
            setResult(RESULT_OK);
            ToastUtils.show(this, "本项目已完成", true);
            this.finish();
        } else {
            ToastUtils.show(this, "当前步骤已完成", true);
            mPresenter.getTaskInfo(taskId);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constans.FINISH_AND_INPUT && resultCode == RESULT_OK) {
            //完成信息录入
            mPresenter.setTaskNodeFinished(supervisorId == null ? sponsorId : supervisorId, taskId);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && etRemark.hasFocus()) {
            etRemark.clearFocus();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        mPresenter.unSubscrib();
        super.onDestroy();
    }
}

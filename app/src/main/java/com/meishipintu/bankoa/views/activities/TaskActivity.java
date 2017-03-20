package com.meishipintu.bankoa.views.activities;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.meishipintu.bankoa.Constans;
import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.components.DaggerTaskComponent;
import com.meishipintu.bankoa.contracts.TaskContract;
import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.modules.TaskModule;
import com.meishipintu.bankoa.presenters.TaskPresenterImp;
import com.meishipintu.bankoa.views.adapter.TaskListAdapter;
import com.meishipintu.library.util.ToastUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/1.
 * <p>
 * 任务列表页面
 */

public class TaskActivity extends BasicActivity implements TaskContract.IView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.left)
    RadioButton left;
    @BindView(R.id.right)
    RadioButton right;
    @BindView(R.id.vp)
    RecyclerView vp;
    @BindView(R.id.rg_tab)
    RadioGroup rgTab;

    @Inject
    TaskPresenterImp mPresenter;

    private TaskListAdapter adapter;
    private List<Task> dataList;
    private int checkNow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        ButterKnife.bind(this);

        DaggerTaskComponent.builder().taskModule(new TaskModule(this))
                .build().inject(this);
        initUI();
        initVP();
    }

    private void initUI() {
        tvTitle.setText(R.string.task);
        left.setText(R.string.unfinish);
        right.setText(R.string.finish);
    }

    private void initVP() {
        vp.setItemAnimator(new DefaultItemAnimator());
        vp.setLayoutManager(new LinearLayoutManager(this));
        checkNow = R.id.left;
        mPresenter.getTask(1);
    }

    @OnClick({R.id.bt_back, R.id.left, R.id.right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_back:
                onBackPressed();
                break;
            case R.id.left:
                if (checkNow != R.id.left) {
                    checkNow = R.id.left;
                    mPresenter.getTask(1);
                }
                break;
            case R.id.right:
                if (checkNow != R.id.right) {
                    checkNow = R.id.right;
                    mPresenter.getTask(2);
                }
                break;
        }
    }

    //from TaskContract.IView
    @Override
    public void showTask(List<Task> taskList, int type) {
        Log.d(Constans.APP, "show Task:" + taskList.size());
        if (adapter == null) {
            dataList = taskList;
            adapter = new TaskListAdapter(this, dataList, type);
            vp.setAdapter(adapter);
        } else {
            dataList.clear();
            dataList.addAll(taskList);
            adapter.notifyDataSetChanged();
        }
    }

    //from TaskContract.IView
    @Override
    public void showError(String message) {
        ToastUtils.show(this, message, true);
    }

    @Override
    protected void onDestroy() {
        mPresenter.unSubscrib();
        super.onDestroy();
    }
}

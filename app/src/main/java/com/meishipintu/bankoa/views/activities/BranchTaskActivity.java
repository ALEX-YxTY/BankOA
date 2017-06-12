package com.meishipintu.bankoa.views.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.meishipintu.bankoa.OaApplication;
import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.models.PreferenceHelper;
import com.meishipintu.bankoa.models.entity.BranchUserInfo;
import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.models.http.HttpApi;
import com.meishipintu.bankoa.views.adapter.SimpleTaskListAdapter;
import com.meishipintu.library.util.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017/6/12.
 * <p>
 * 主要功能：支行查看的任务列表页
 */

public class BranchTaskActivity extends BasicActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.vp)
    RecyclerView vp;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;

    private CompositeSubscription subscriptions;
    private List<String> centerBranchList;          //储存中心分行名称
    private Map<Integer, Map<Integer, String>> branchList;      //储存支行名称
    private BranchUserInfo branchUserInfo;          //分行信息
    private HttpApi httpApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_branch);
        ButterKnife.bind(this);
        tvTitle.setText("支行任务列表");
        tvEmpty.setText(R.string.no_task);
        subscriptions = new CompositeSubscription();
        httpApi = HttpApi.getInstance();
        branchUserInfo = (BranchUserInfo) getIntent().getExtras().get("branch");
        downLoadResource();

    }

    //下载固定资源
    private void downLoadResource() {
        //获取任务类型
        subscriptions.add(httpApi.getTaskTypeList().subscribeOn(Schedulers.io())
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> strings) {
                        PreferenceHelper.saveTaskType(strings);
                        for(int i=1;i<=strings.size();i++) {
                            final int k = i;
                            subscriptions.add(httpApi.getNodeNameList(i).subscribeOn(Schedulers.io())
                                    .subscribe(new Action1<JSONObject>() {
                                        @Override
                                        public void call(JSONObject jsonObject) {
                                            if (jsonObject.length() > 0) {
                                                PreferenceHelper.saveNodeNameList(k, jsonObject.toString());
                                                OaApplication.nodeNameList.put(k + "", jsonObject);
                                                PreferenceHelper.saveNodeNum(k, jsonObject.length());
                                                OaApplication.nodeNumber.put(k + "", jsonObject.length());
                                            }
                                        }
                                    }));
                        }
                    }
                }));
        //获取中心支行
        centerBranchList = OaApplication.centerBranchList;
        if (centerBranchList == null) {
            subscriptions.add(httpApi.getCenterBranchList().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<String>>() {
                        @Override
                        public void call(List<String> strings) {
                            PreferenceHelper.saveCenterBranch(strings);
                            getBranchList(strings.size());
                        }
                    }));
        } else {
            getBranchList(centerBranchList.size());
        }
    }

    //获取各支行列表
    private void getBranchList(int totalSize) {
        branchList = new HashMap<>();
        for (int i = 1; i <= totalSize; i++) {
            final int index = i;
            Map<Integer, String> itemList = OaApplication.branchList.get(index);
            if (itemList == null) {
                subscriptions.add(httpApi.getBranchList(i).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Map<Integer, String>>() {
                            @Override
                            public void call(Map<Integer, String> strings) {
                                PreferenceHelper.saveBranch(index, strings);
                                OaApplication.branchList.put(index, strings);
                                branchList.put(index, strings);
                            }
                        }));
            } else {
                branchList.put(index, itemList);
            }
        }
        subscriptions.add(httpApi.getBranchTask(branchUserInfo.getCenter_branch(),branchUserInfo.getBranch())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Task>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        ToastUtils.show(BranchTaskActivity.this, "获取任务列表失败，请稍后重试", true);
                    }

                    @Override
                    public void onNext(List<Task> taskList) {
                        if (taskList.size() == 0) {
                            tvEmpty.setVisibility(View.VISIBLE);
                        } else {
                            tvEmpty.setVisibility(View.GONE);
                            SimpleTaskListAdapter simpleTaskListAdapter = new SimpleTaskListAdapter(BranchTaskActivity.this
                                    , taskList, centerBranchList, branchList);
                            vp.setLayoutManager(new LinearLayoutManager(BranchTaskActivity.this));
                            vp.setAdapter(simpleTaskListAdapter);
                        }
                    }
                }));

    }

    @OnClick(R.id.bt_back)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscriptions.clear();
    }
}

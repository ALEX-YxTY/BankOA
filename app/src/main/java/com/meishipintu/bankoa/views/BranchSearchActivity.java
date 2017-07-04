package com.meishipintu.bankoa.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.meishipintu.bankoa.OaApplication;
import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.models.PreferenceHelper;
import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.bankoa.models.http.HttpApi;
import com.meishipintu.bankoa.presenters.SearchPresenterImp;
import com.meishipintu.bankoa.views.activities.BranchTaskActivity;
import com.meishipintu.bankoa.views.activities.SearchActivity;
import com.meishipintu.bankoa.views.adapter.SimpleTaskListAdapter;
import com.meishipintu.library.util.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class BranchSearchActivity extends AppCompatActivity {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.cancel)
    TextView tvCancel;

    private SimpleTaskListAdapter adapter;
    private List<Task> dataList = new ArrayList<>();

    private List<String> centerBranchList;          //储存中心分行名称
    private Map<Integer, Map<Integer, String>> branchList;      //储存支行名称

    private CompositeSubscription subscriptions;
    private HttpApi httpApi;

    private int centerBranchId;
    private int branchId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        centerBranchId = getIntent().getIntExtra("centerBranchId", 1);
        branchId = getIntent().getIntExtra("branchId", 1);
        httpApi = HttpApi.getInstance();
        subscriptions = new CompositeSubscription();
        centerBranchList = OaApplication.centerBranchList;
        rv.setLayoutManager(new LinearLayoutManager(this));
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (centerBranchList == null) {
            subscriptions.add(httpApi.getCenterBranchList().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<String>>() {
                        @Override
                        public void call(List<String> strings) {
                            PreferenceHelper.saveCenterBranch(strings);
                            centerBranchList = strings;
                            getBranchList();
                        }
                    }));
        }else{
            getBranchList();
        }
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if ("".equals(etSearch.getText().toString())) {
                        ToastUtils.show(BranchSearchActivity.this, R.string.err_search_empty, true);
                    } else {
                        if (adapter != null) {
                            dataList.clear();
                            adapter.notifyDataSetChanged();//先清空视图
                        }
                        subscriptions.add(httpApi.getBranchTask(centerBranchId, branchId, 0
                                , etSearch.getText().toString()).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<List<Task>>() {
                                    @Override
                                    public void onCompleted() {
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        e.printStackTrace();
                                        ToastUtils.show(BranchSearchActivity.this, "获取任务列表失败，请稍后重试"
                                                , true);
                                    }

                                    @Override
                                    public void onNext(List<Task> taskList) {
                                        if (taskList.size() == 0) {
                                            tvEmpty.setVisibility(View.VISIBLE);
                                        } else {
                                            tvEmpty.setVisibility(View.GONE);
                                            dataList.clear();
                                            dataList.addAll(taskList);
                                            if (adapter == null) {
                                                adapter = new SimpleTaskListAdapter(BranchSearchActivity.this
                                                        , dataList, centerBranchList, branchList);
                                                rv.setAdapter(adapter);
                                            } else {
                                                adapter.notifyDataSetChanged();
                                            }
                                        }
                                    }
                                }));
                    }
                    return true;
                }
                return false;
            }
        });
    }

    //获取支行列表
    private void getBranchList() {
        branchList = new HashMap<>();
        for (int i = 1; i <= centerBranchList.size(); i++) {
            Map<Integer, String> itemList = OaApplication.branchList.get(i);
            if (itemList == null) {
                final int finalI = i;
                subscriptions.add(httpApi.getBranchList(i).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Map<Integer, String>>() {
                            @Override
                            public void call(Map<Integer, String> strings) {
                                PreferenceHelper.saveBranch(finalI, strings);
                                OaApplication.branchList.put(finalI, strings);
                                branchList.put(finalI, strings);
                            }
                        }));
            } else {
                branchList.put(i, itemList);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscriptions.clear();
    }
}

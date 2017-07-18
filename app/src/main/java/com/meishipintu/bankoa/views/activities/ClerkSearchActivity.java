package com.meishipintu.bankoa.views.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.meishipintu.bankoa.OaApplication;
import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.bankoa.models.http.HttpApi;
import com.meishipintu.bankoa.views.adapter.ClerkListAdapter;
import com.meishipintu.bankoa.views.adapter.SimpleTaskListAdapter;
import com.meishipintu.library.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017/7/17.
 * <p>
 * 主要功能：
 */

public class ClerkSearchActivity extends AppCompatActivity {
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.cancel)
    TextView tvCancel;

    private CompositeSubscription subscriptions;
    private HttpApi httpApi;

    private List<UserInfo> dataList;
    private ClerkListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        httpApi = HttpApi.getInstance();
        subscriptions = new CompositeSubscription();
        dataList = new ArrayList<>();
        ButterKnife.bind(this);

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if ("".equals(etSearch.getText().toString())) {
                        ToastUtils.show(ClerkSearchActivity.this, R.string.err_search_empty, true);
                    } else {
                        if (adapter != null) {
                            dataList.clear();
                            adapter.notifyDataSetChanged();//先清空视图
                        }
                        subscriptions.add(httpApi.searchClerk(etSearch.getText().toString()
                                , OaApplication.getUser().getDepartment_id()
                                , OaApplication.getUser().getLevel()).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<List<UserInfo>>() {
                                    @Override
                                    public void onCompleted() {
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        e.printStackTrace();
                                        ToastUtils.show(ClerkSearchActivity.this, "搜索员工失败，请稍后重试"
                                                , true);
                                    }

                                    @Override
                                    public void onNext(List<UserInfo> data) {
                                        if (data.size() == 0) {
                                            tvEmpty.setVisibility(View.VISIBLE);
                                        } else {
                                            tvEmpty.setVisibility(View.GONE);
                                            dataList.clear();
                                            dataList.addAll(data);
                                            Log.d("test", "dataList.size:" + dataList.size());
                                            if (adapter == null) {
                                                adapter = new ClerkListAdapter(ClerkSearchActivity.this
                                                        ,dataList);
                                                rv.setLayoutManager(new LinearLayoutManager(ClerkSearchActivity.this));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscriptions.clear();
    }
}

package com.meishipintu.bankoa.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.meishipintu.bankoa.Constans;
import com.meishipintu.bankoa.OaApplication;
import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.components.DaggerSearchComponent;
import com.meishipintu.bankoa.contracts.SearchContract;
import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.bankoa.modules.SearchMoudule;
import com.meishipintu.bankoa.presenters.SearchPresenterImp;
import com.meishipintu.bankoa.views.adapter.TaskListAdapter;
import com.meishipintu.library.util.ToastUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created 2017-3-2
 *
 * 主要功能：搜索页面
 */

public class SearchActivity extends BasicActivity implements SearchContract.IView {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;

    @Inject
    SearchPresenterImp mPresenter;

    private RecyclerView.Adapter adapter;
    private List<Task> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        DaggerSearchComponent.builder().searchMoudule(new SearchMoudule(this))
                .build().inject(this);
        setListener();
        initRv();
    }

    private void initRv() {
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());
    }

    private void setListener() {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if ("".equals(etSearch.getText().toString())) {
                        ToastUtils.show(SearchActivity.this, R.string.err_search_empty, true);
                    } else {
                        UserInfo userInfo = OaApplication.getUser();
                        mPresenter.search(userInfo.getLevel(), userInfo.getDepartment_id(), userInfo.getUid()
                                , etSearch.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick(R.id.cancel)
    public void onClick() {
        onBackPressed();
    }

    //from SearchContract.IView
    @Override
    public void showResult(List<Task> taskList) {
        if (adapter == null) {
            //因为此列表包含本人项目和下属项目，所以传值传本人uid和level，
            // 如果sponsorId = supervisorId，则为本人项目，否则为监管项目
            dataList = taskList;
            adapter = new TaskListAdapter(this, dataList, OaApplication.getUser().getUid()
                    , OaApplication.getUser().getLevel());
            rv.setAdapter(adapter);
        } else {
            dataList.clear();
            dataList.addAll(taskList);
            adapter.notifyDataSetChanged();
        }
        if (taskList.size() == 0) {
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            tvEmpty.setVisibility(View.GONE);
        }
    }

    //from BasicView
    @Override
    public void showError(String errMsg) {
        ToastUtils.show(this, errMsg, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constans.PAYMENT) {
            //刷新界面
            UserInfo userInfo = OaApplication.getUser();
            mPresenter.search(userInfo.getLevel(), userInfo.getDepartment_id(), userInfo.getUid()
                    , etSearch.getText().toString());
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.unSubscrib();
        super.onDestroy();
    }
}

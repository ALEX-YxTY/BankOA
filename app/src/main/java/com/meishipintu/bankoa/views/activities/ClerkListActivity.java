package com.meishipintu.bankoa.views.activities;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.meishipintu.bankoa.OaApplication;
import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.components.DaggerClerkComponent;
import com.meishipintu.bankoa.contracts.ClerkContract;
import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.bankoa.modules.ClerkModule;
import com.meishipintu.bankoa.presenters.ClerkPresenterImp;
import com.meishipintu.bankoa.views.adapter.ClerkListAdapter;
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
 * 员工列表页面
 */

public class ClerkListActivity extends BasicActivity implements ClerkContract.IView{

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_clerk)
    RecyclerView rvClerk;

    @Inject
    ClerkPresenterImp mPresenter;

    private List<UserInfo> dataList;
    private ClerkListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clerk_list);
        ButterKnife.bind(this);

        DaggerClerkComponent.builder().clerkModule(new ClerkModule(this))
                .build().inject(this);
        init();
    }

    private void init() {
        tvTitle.setText(R.string.clerk_list);
        mPresenter.getClerk(OaApplication.getUser().getUid(),OaApplication.getUser().getLevel());
    }

    @OnClick(R.id.bt_back)
    public void onClick() {
        onBackPressed();
    }

    //from ClerkListContract.IView
    @Override
    public void showCLerk(List<UserInfo> clerkInfos) {
        if (adapter == null) {
            dataList = new ArrayList<>();
            dataList.addAll(clerkInfos);
            adapter = new ClerkListAdapter(this, dataList);
            rvClerk.setLayoutManager(new LinearLayoutManager(this));
            rvClerk.setItemAnimator(new DefaultItemAnimator());
            rvClerk.setAdapter(adapter);
        } else {
            dataList.clear();
            dataList.addAll(clerkInfos);
            adapter.notifyDataSetChanged();
        }
    }

    //from BasicView
    @Override
    public void showError(String errMsg) {
        ToastUtils.show(this, errMsg, true);
    }

    @Override
    protected void onDestroy() {
        mPresenter.unSubscrib();
        super.onDestroy();
    }
}

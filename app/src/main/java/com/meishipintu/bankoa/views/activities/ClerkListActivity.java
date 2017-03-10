package com.meishipintu.bankoa.views.activities;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.models.entity.ClerkInfo;
import com.meishipintu.bankoa.views.adapter.ClerkListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/1.
 * <p>
 * 员工列表页面
 */

public class ClerkListActivity extends BasicActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_clerk)
    RecyclerView rvClerk;

    private List<ClerkInfo> clerkInfoList;
    private ClerkListAdapter clerkListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clerk_list);
        ButterKnife.bind(this);
        clerkInfoList = new ArrayList<>();
        init();
    }

    private void init() {
        rvClerk.setLayoutManager(new LinearLayoutManager(this));
        rvClerk.setItemAnimator(new DefaultItemAnimator());
        clerkListAdapter = new ClerkListAdapter(this, clerkInfoList);
        rvClerk.setAdapter(clerkListAdapter);
    }

    @OnClick(R.id.bt_back)
    public void onClick() {
        onBackPressed();
    }
}

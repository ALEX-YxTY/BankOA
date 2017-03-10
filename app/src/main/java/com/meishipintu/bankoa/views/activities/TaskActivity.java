package com.meishipintu.bankoa.views.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.meishipintu.bankoa.R;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/1.
 *
 * 任务列表页面
 */

public class TaskActivity extends BasicActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        ButterKnife.bind(this);
    }
}

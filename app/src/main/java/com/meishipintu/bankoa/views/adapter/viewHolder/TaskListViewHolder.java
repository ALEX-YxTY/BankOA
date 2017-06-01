package com.meishipintu.bankoa.views.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.meishipintu.bankoa.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/17.
 * <p>
 * 主要功能：
 */

public class TaskListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.icon)
    public ImageView icon;
    @BindView(R.id.tv_task_name)
    public TextView tvTaskName;
    @BindView(R.id.tv_process_now)
    public TextView tvProcessNow;
    @BindView(R.id.tv_percentage)
    public TextView tvPercentage;
    @BindView(R.id.bt_check)
    public Button btCheck;
    @BindView(R.id.tv_task_type)
    public TextView tvTaskType;
    @BindView(R.id.tv_recommend_name)
    public TextView tvRecommendName;
    @BindView(R.id.tv_apply_money)
    public TextView tvApplyMoney;
    @BindView(R.id.tv_sponsor_name)
    public TextView tvSponsorName;

    public TaskListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}

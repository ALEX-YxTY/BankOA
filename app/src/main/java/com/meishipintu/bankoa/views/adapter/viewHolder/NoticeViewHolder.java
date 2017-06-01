package com.meishipintu.bankoa.views.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meishipintu.bankoa.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/27.
 * <p>
 * 主要功能：
 */

public class NoticeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.icon)
    public ImageView icon;
    @BindView(R.id.tv_title)
    public TextView tvTitle;
    @BindView(R.id.tv_subTitle)
    public TextView tvSubTitle;
    @BindView(R.id.bt_check)
    public Button btCheck;
    @BindView(R.id.tv_time)
    public TextView tvTime;
    @BindView(R.id.tv_apply_money)
    public TextView tvApplyMoney;
    @BindView(R.id.tv_sponsor_name)
    public TextView tvSponsorName;
    @BindView(R.id.ll_sponsor_name)
    public LinearLayout llSponsorName;
    @BindView(R.id.ll_apply_money)
    public LinearLayout llApplayMoney;

    public NoticeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}

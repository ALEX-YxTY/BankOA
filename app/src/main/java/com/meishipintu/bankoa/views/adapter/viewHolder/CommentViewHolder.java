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
 * Created by Administrator on 2017/3/24.
 * <p>
 * 主要功能：
 */

public class CommentViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_head)
    public ImageView ivHead;
    @BindView(R.id.tv_name)
    public TextView tvName;
    @BindView(R.id.tv_time)
    public TextView tvTime;
    @BindView(R.id.tv_content)
    public TextView tvContent;
    @BindView(R.id.tv_repay_name)
    public TextView tvRepayName;
    @BindView(R.id.tv_repay_content)
    public TextView tvRepayContent;
    @BindView(R.id.ll_repay)
    public LinearLayout llRepay;
    @BindView(R.id.bt_check)
    public Button btReply;

    public CommentViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}

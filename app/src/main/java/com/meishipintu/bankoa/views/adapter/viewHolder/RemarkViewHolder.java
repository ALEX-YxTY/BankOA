package com.meishipintu.bankoa.views.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.meishipintu.bankoa.R;

/**
 * Created by Administrator on 2017/3/19.
 * <p>
 * 功能介绍：
 */

public class RemarkViewHolder extends RecyclerView.ViewHolder {

    public TextView tvContent;
    public TextView tvTime;

    public RemarkViewHolder(View itemView) {
        super(itemView);
        tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        tvTime = (TextView) itemView.findViewById(R.id.tv_time);

    }
}

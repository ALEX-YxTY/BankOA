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

public class ClerkInfoViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.icon)
    public ImageView icon;
    @BindView(R.id.tv_name)
    public TextView tvName;
    @BindView(R.id.tv_title)
    public TextView tvTitle;
    @BindView(R.id.tv_number)
    public TextView tvNumber;
    @BindView(R.id.bt_check)
    public Button btCheck;

    public ClerkInfoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}

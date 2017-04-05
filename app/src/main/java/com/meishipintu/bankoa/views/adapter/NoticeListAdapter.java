package com.meishipintu.bankoa.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.models.entity.SysNotic;
import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.models.entity.UpClassRemind;
import com.meishipintu.bankoa.views.activities.TaskDetailActivity;
import com.meishipintu.bankoa.views.adapter.viewHolder.NoticeViewHolder;
import com.meishipintu.library.util.StringUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/3/27.
 * <p>
 * 主要功能：
 */

public class NoticeListAdapter extends RecyclerView.Adapter<NoticeViewHolder> {
    private Context context;
    private List<SysNotic> remindList;


    public NoticeListAdapter(Context context, List<SysNotic> remindList) {
        this.context = context;
        this.remindList = remindList;
    }

    @Override
    public NoticeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoticeViewHolder(LayoutInflater.from(context).inflate(R.layout.item_notic, parent, false));
    }

    @Override
    public void onBindViewHolder(NoticeViewHolder holder, int position) {
        SysNotic sysNotic = remindList.get(position);
        holder.tvTitle.setText(sysNotic.getTitle());
        holder.tvSubTitle.setText(sysNotic.getContent());
        holder.btCheck.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return remindList.size();
    }
}

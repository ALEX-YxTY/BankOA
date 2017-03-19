package com.meishipintu.bankoa.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.models.entity.RemarkInfo;
import com.meishipintu.bankoa.views.adapter.viewHolder.RemarkViewHolder;
import com.meishipintu.library.util.DateUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/3/19.
 * <p>
 * 功能介绍：
 */

public class RemarkAdapter extends RecyclerView.Adapter<RemarkViewHolder> {

    private List<RemarkInfo> remarkInfos;
    private Context context;

    public RemarkAdapter(Context context, List<RemarkInfo> dataList) {
        this.remarkInfos = dataList;
        this.context = context;
    }


    @Override
    public RemarkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RemarkViewHolder(View.inflate(context, R.layout.item_remark, null));
    }

    @Override
    public void onBindViewHolder(RemarkViewHolder holder, int position) {
        RemarkInfo info = remarkInfos.get(position);
        holder.tvContent.setText("asdfgdfghjhf");
        holder.tvTime.setText(DateUtil.getTimeFormart2(info.getRemark_time()));
    }

    @Override
    public int getItemCount() {
        return remarkInfos.size();
    }

    public List<RemarkInfo> getDataList() {
        return this.remarkInfos;
    }

}

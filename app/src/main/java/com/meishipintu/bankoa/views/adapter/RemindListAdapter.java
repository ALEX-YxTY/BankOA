package com.meishipintu.bankoa.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.models.entity.PaymentDetailItem;
import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.models.entity.UpClassRemind;
import com.meishipintu.bankoa.views.activities.PaymentDetailActivity;
import com.meishipintu.bankoa.views.activities.TaskDetailActivity;
import com.meishipintu.bankoa.views.adapter.viewHolder.NoticeViewHolder;
import com.meishipintu.library.util.DateUtil;
import com.meishipintu.library.util.StringUtils;
import com.meishipintu.library.util.ToastUtils;

import java.util.List;


/**
 * Created by Administrator on 2017/3/27.
 * <p>
 * 主要功能：
 */

public class RemindListAdapter extends RecyclerView.Adapter<NoticeViewHolder> {

    private Context context;
    private List<UpClassRemind> remindList;
    private RequestManager requestManager;
    private String supervisorId;
    private String supervisorLevel;

    public RemindListAdapter(Context context, List<UpClassRemind> remindList,String supervisorId,String supervisorLevel) {
        this.context = context;
        this.remindList = remindList;
        requestManager = Glide.with(context);
        this.supervisorId = supervisorId;
        this.supervisorLevel = supervisorLevel;
    }

    @Override
    public NoticeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoticeViewHolder(LayoutInflater.from(context).inflate(R.layout.item_notic, parent, false));
    }

    @Override
    public void onBindViewHolder(NoticeViewHolder holder, int position) {
        final UpClassRemind remind = remindList.get(position);
        Log.d("BankOA", "remind:" + remind.toString());
        if ("1".equals(remind.getType())) {
            requestManager.load(R.drawable.icon_process_notice).into(holder.icon);
        } else if (!StringUtils.isNullOrEmpty(remind.getUrl())) {
            requestManager.load(remind.getUrl()).into(holder.icon);
        } else {
            requestManager.load(R.drawable.icon_avatar).into(holder.icon);
        }
        holder.tvTitle.setText(remind.getNotice_title());
        holder.tvSubTitle.setText(remind.getNotice_content());
        holder.tvTime.setText(DateUtil.formart3(remind.getCreate_time()));
        holder.btCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                if ("1".equals(remind.getTask_info().getIs_del())) {
                    //任务已删除
                    ToastUtils.show(context, R.string.task_deleted, true);
                } else if ("1".equals(remind.getTask_info().getIs_finish())) {
                    //任务已完成
                    intent = new Intent(context, PaymentDetailActivity.class);
                    intent.putExtra("task_id", remind.getTask_id());
                } else {
                    //任务在进行中
                    intent = new Intent(context, TaskDetailActivity.class);
                    intent.putExtra("task", new Task(remind.getTask_id(), remind.getUser_id()));
                    if (supervisorId != null) {
                        intent.putExtra("supervisor_id", supervisorId);
                        intent.putExtra("supervisor_level", supervisorLevel);
                    }
                }
                if (null != intent) {
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return remindList.size();
    }
}

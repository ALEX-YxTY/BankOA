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
import com.meishipintu.bankoa.OaApplication;
import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.models.entity.PaymentDetailItem;
import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.models.entity.UpClassRemind;
import com.meishipintu.bankoa.views.activities.PaymentDetailActivity;
import com.meishipintu.bankoa.views.activities.PaymentEnterActivity;
import com.meishipintu.bankoa.views.activities.TaskDetailActivity;
import com.meishipintu.bankoa.views.adapter.viewHolder.NoticeViewHolder;
import com.meishipintu.library.util.DateUtil;
import com.meishipintu.library.util.StringUtils;
import com.meishipintu.library.util.ToastUtils;

import java.util.List;
import java.util.Set;


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
    private boolean fromMain;
    private Set<String> readList;

    public RemindListAdapter(Context context, List<UpClassRemind> remindList, String supervisorId
            , String supervisorLevel, boolean fromMain, Set<String> readList) {

        this.context = context;
        this.remindList = remindList;
        requestManager = Glide.with(context);
        this.supervisorId = supervisorId;
        this.supervisorLevel = supervisorLevel;
        this.fromMain = fromMain;
        this.readList = readList;
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
            if (fromMain && readList.contains(remind.getId())) {
                //从主页进入并且该消息已读，设为灰色图标
                requestManager.load(R.drawable.icon_process_notice_read).into(holder.icon);
            } else {
                requestManager.load(R.drawable.icon_process_notice).into(holder.icon);
            }
        } else if (!StringUtils.isNullOrEmpty(remind.getUrl())) {
            requestManager.load(remind.getUrl()).into(holder.icon);
        } else {
            requestManager.load(R.drawable.icon_avatar).into(holder.icon);
        }
        holder.tvTitle.setText(remind.getNotice_title());
        holder.tvSubTitle.setText(remind.getNotice_content());
        holder.tvTime.setText(DateUtil.formart3(remind.getCreate_time()));
        holder.llApplayMoney.setVisibility(View.VISIBLE);
        holder.llSponsorName.setVisibility(View.VISIBLE);
        holder.tvApplyMoney.setText(remind.getTask_info().getApply_money());
        holder.tvSponsorName.setText(remind.getTask_info().getSponsor_name());
        holder.btCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                if (fromMain) {
                    //添加到已读列表
                    readList.add(remind.getId());
                }
                if (remind.getTask_info() == null) {
                    ToastUtils.show(context, "该任务不存在", true);
                }else if ("1".equals(remind.getTask_info().getIs_del())) {
                    //任务已删除
                    ToastUtils.show(context, R.string.task_deleted, true);
                } else if ("1".equals(remind.getTask_info().getIs_finish())) {
                    //任务已完成
                    intent = new Intent(context, PaymentEnterActivity.class);
                    intent.putExtra("task_id", remind.getTask_id());
                } else {
                    //任务在进行中
                    intent = new Intent(context, TaskDetailActivity.class);
                    intent.putExtra("task", new Task(remind.getTask_id(), remind.getTask_info().getSponsor_id()));
                    if (supervisorId != null) {
                        //从监管界面进入
                        intent.putExtra("supervisor_id", supervisorId);
                        intent.putExtra("supervisor_level", supervisorLevel);
                    } else if (Integer.parseInt(remind.getTask_info().getSponsor_level())
                            > Integer.parseInt(OaApplication.getUser().getLevel())) {
                        //直接进入，判断任务所有者的level和操作者level关系,如操作者权限更高，自动进入监管模式
                        intent.putExtra("supervisor_id", OaApplication.getUser().getUid());
                        intent.putExtra("supervisor_level", OaApplication.getUser().getLevel());
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

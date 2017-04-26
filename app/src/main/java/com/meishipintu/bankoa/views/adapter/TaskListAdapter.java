package com.meishipintu.bankoa.views.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meishipintu.bankoa.Constans;
import com.meishipintu.bankoa.OaApplication;
import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.models.PreferenceHelper;
import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.views.activities.PaymentDetailActivity;
import com.meishipintu.bankoa.views.activities.PaymentEnterActivity;
import com.meishipintu.bankoa.views.activities.TaskActivity;
import com.meishipintu.bankoa.views.activities.TaskDetailActivity;
import com.meishipintu.bankoa.views.adapter.viewHolder.TaskListViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/17.
 * <p>
 * 主要功能：
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListViewHolder> {

    private static final String TAG = "BankOA-taskListAdapter";
    private List<Task> dataList;
    private Context mContext;
    private String supervisorId = null;     //监管人的uid
    private String supervisorLevel = null;     //监管人的level

    public TaskListAdapter(Context context, List<Task> list,String s_uid,String s_level) {
        this.dataList = list;
        this.mContext = context;
        this.supervisorId = s_uid;
        this.supervisorLevel = s_level;
    }

    @Override
    public TaskListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TaskListViewHolder(View.inflate(mContext, R.layout.item_task, null));
    }

    @Override
    public void onBindViewHolder(TaskListViewHolder holder, final int position) {
        final Task task = dataList.get(position);
        Log.d(TAG, "task:" + task.toString());
        if ("1".equals(task.getIs_finish())) {
            holder.icon.setImageResource(R.drawable.icon_task_finished);
        } else {
            holder.icon.setImageResource(R.drawable.icon_task_unfinished);
        }
        holder.tvTaskName.setText(task.getTask_name());
        JSONObject nodeNameList = OaApplication.nodeNameList.get(task.getTask_type());
        if (nodeNameList != null) {
            try {
                holder.tvProcessNow.setText(nodeNameList.getString(task.getLevel()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String[] taskTypeList = PreferenceHelper.getTakTypeList();

        if (taskTypeList.length > 0 && taskTypeList.length >= Integer.parseInt(task.getTask_type())) {
            holder.tvTaskType.setText(taskTypeList[Integer.parseInt(task.getTask_type())-1]);
        }
        holder.tvCreditName.setText(task.getCredi_name());

        if ("1".equals(task.getIs_finish())) {
            holder.tvPercentage.setText("100%");
        } else {
            Log.d(TAG, "taskType:" + task.getTask_type());
            Log.d(TAG, "totalLevel:" + OaApplication.nodeNumber.get(task.getTask_type()));
            Log.d(TAG, "level Now:" + task.getLevel());

            if (OaApplication.nodeNumber.get(task.getTask_type()) != null
                    && OaApplication.nodeNumber.get(task.getTask_type()) != 0) {
                int percentage = ((Integer.parseInt(task.getLevel()) - 1) * 100 / OaApplication.nodeNumber.get(task.getTask_type()));
                holder.tvPercentage.setText(percentage + "%");
            } else {
                holder.tvPercentage.setText("--%");
            }
        }
        holder.btCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if ("0".equals(task.getIs_finish())) {
                    intent = new Intent(mContext, TaskDetailActivity.class);
                    intent.putExtra("task", task);

                } else {
                    intent = new Intent(mContext, PaymentEnterActivity.class);
                    intent.putExtra("task_id", task.getId());
                }
                if (supervisorId != null) {
                    intent.putExtra("supervisor_id", supervisorId);
                    intent.putExtra("supervisor_level", supervisorLevel);
                }
                Activity activity = (Activity) mContext;
                activity.startActivityForResult(intent,Constans.PAYMENT);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

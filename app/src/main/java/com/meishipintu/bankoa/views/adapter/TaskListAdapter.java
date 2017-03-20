package com.meishipintu.bankoa.views.adapter;

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
import com.meishipintu.bankoa.views.activities.TaskDetailActivity;
import com.meishipintu.bankoa.views.adapter.viewHolder.TaskListViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/17.
 * <p>
 * 主要功能：
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListViewHolder> {

    private List<Task> dataList;
    private Context mContext;
    private int type;               //标注已完成和未完成
    private int nodeNum;                    // 节点数量

    public TaskListAdapter(Context context, List<Task> list, int type) {
        this.dataList = list;
        this.mContext = context;
        this.type = type;

        this.nodeNum = PreferenceHelper.getNodeNum();
    }

    @Override
    public TaskListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TaskListViewHolder(View.inflate(mContext, R.layout.item_task, null));
    }

    @Override
    public void onBindViewHolder(TaskListViewHolder holder, final int position) {
        Task task = dataList.get(position);
        holder.icon.setImageResource(type == 2 ? R.drawable.icon_task_finished : R.drawable.icon_task_unfinished);
        holder.tvTaskName.setText(task.getTask_name());
        JSONObject nodeNameList = OaApplication.nodeNameList;
        if (nodeNameList != null) {
            try {
                holder.tvProcessNow.setText(nodeNameList.getString(task.getLevel()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        holder.tvPercentage.setText((Integer.parseInt(task.getLevel())*100/nodeNum)+"%");
        holder.btCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TaskDetailActivity.class);
                intent.putExtra("task", dataList.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

package com.meishipintu.bankoa.views.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.meishipintu.bankoa.Constans;
import com.meishipintu.bankoa.OaApplication;
import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.models.PreferenceHelper;
import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.views.activities.PaymentEnterActivity;
import com.meishipintu.bankoa.views.activities.TaskDetailActivity;
import com.meishipintu.bankoa.views.adapter.viewHolder.TaskListViewHolder;
import com.meishipintu.library.util.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/17.
 * <p>
 * 主要功能：用于支行查询的列表显示
 */

public class SimpleTaskListAdapter extends RecyclerView.Adapter<TaskListViewHolder> {

    private static final String TAG = "BankOA-taskListAdapter";
    private List<Task> dataList;
    private Context mContext;

    private List<String> centerBranch;              //中心支行列表
    private Map<Integer, Map<Integer, String>> branchList;      //分行列表

    public SimpleTaskListAdapter(Context context, List<Task> list, List<String> centerBranch
            , Map<Integer, Map<Integer, String>> branchList) {
        this.centerBranch = centerBranch;
        this.branchList = branchList;
        this.dataList = list;
        this.mContext = context;
    }

    @Override
    public TaskListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TaskListViewHolder(View.inflate(mContext, R.layout.item_task, null));
    }

    @Override
    public void onBindViewHolder(TaskListViewHolder holder, final int position) {
        final Task task = dataList.get(position);
        Log.d(TAG, "task:" + task.toString());
        holder.tvTaskName.setText(task.getCredi_name());
        JSONObject nodeNameList = OaApplication.nodeNameList.get(task.getTask_type());
        if ("1".equals(task.getIs_finish())) {
            holder.icon.setImageResource(R.drawable.icon_task_finished);
        } else {
            holder.icon.setImageResource(R.drawable.icon_task_unfinished);
        }
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
        holder.tvApplyMoney.setText(task.getApply_money());
        holder.tvSponsorName.setText(task.getSponsor_name());
        int centerBranchId = Integer.parseInt(task.getCredit_center_branch());
        int branchId = Integer.parseInt(task.getCredit_branch());
        String centerBranch = this.centerBranch.get(centerBranchId-1);
        String branch = "";
        Map<Integer, String> branchStrings = this.branchList.get(centerBranchId);
        if (branchStrings != null && branchStrings.size() > 0 && branchStrings.get(branchId) != null) {
            branch = "-" + branchStrings.get(branchId);
        }
        holder.tvRecommendName.setText(centerBranch + branch);

        if (OaApplication.nodeNumber.get(task.getTask_type()) != null
                && OaApplication.nodeNumber.get(task.getTask_type()) != 0) {
            int percentage = ((Integer.parseInt(task.getLevel()) - 1) * 100 / OaApplication.nodeNumber.get(task.getTask_type()));
            holder.tvPercentage.setText(percentage + "%");
        } else {
            holder.tvPercentage.setText("--%");
        }
        holder.btCheck.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

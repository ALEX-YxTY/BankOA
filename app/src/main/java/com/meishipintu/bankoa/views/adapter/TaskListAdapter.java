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
import com.meishipintu.library.util.ToastUtils;

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

    private List<String> centerBranch;              //中心支行列表
    private Map<Integer, String[]> branchList;      //分行列表

    public TaskListAdapter(Context context, List<Task> list, String s_uid, String s_level
            , List<String> centerBranch, Map<Integer, String[]> branchList) {
        this.centerBranch = centerBranch;
        this.branchList = branchList;
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
        holder.tvTaskName.setText(task.getCredi_name());
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
        holder.tvApplyMoney.setText(task.getApply_money());
        holder.tvSponsorName.setText(task.getSponsor_name());
        int centerBranchId = Integer.parseInt(task.getCredit_center_branch());
        int branchId = Integer.parseInt(task.getCredit_branch());
//        Log.d("LoginPresenter", "centerId:" + centerBranchId + ",branchId:" + branchId);
        String centerBranch = this.centerBranch.get(centerBranchId-1);
        String branch = "";
        String[] branchStrings = this.branchList.get(centerBranchId);
        if (branchStrings != null && branchStrings.length > 0 && branchId <= branchStrings.length) {
            branch = "-" + branchStrings[branchId-1];
        }
        holder.tvRecommendName.setText(centerBranch + branch);
        if ("1".equals(task.getIs_finish())) {
            holder.tvPercentage.setText("100%");
        } else {
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
                Intent intent = null;
                if ("0".equals(task.getIs_finish())) {
                    intent = new Intent(mContext, TaskDetailActivity.class);
                    intent.putExtra("task", task);

                } else {
                    if (task.getTask_type().equals("3")) {
                        //类型三任务完成后无下级页面
                        ToastUtils.show(mContext, "苏科贷项目无还款节点", true);
                    } else {
                        intent = new Intent(mContext, PaymentEnterActivity.class);
                        intent.putExtra("task_id", task.getId());
                    }
                }
                if (intent != null) {
                    if (supervisorId != null) {
                        intent.putExtra("supervisor_id", supervisorId);
                        intent.putExtra("supervisor_level", supervisorLevel);
                    }
                    Activity activity = (Activity) mContext;
                    activity.startActivityForResult(intent,Constans.PAYMENT);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

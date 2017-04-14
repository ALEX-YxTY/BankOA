package com.meishipintu.bankoa.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.meishipintu.bankoa.OaApplication;
import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.bankoa.views.activities.SupervisorActivity;
import com.meishipintu.bankoa.views.adapter.viewHolder.ClerkInfoViewHolder;
import com.meishipintu.library.util.StringUtils;

import org.json.JSONException;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/7.
 * <p>
 * 主要功能：
 */

public class ClerkListAdapter extends RecyclerView.Adapter<ClerkInfoViewHolder> {


    private Context mContext;
    private List<UserInfo> clerkInfoList;
    private RequestManager requestManager;

    public ClerkListAdapter(Context mContext, List<UserInfo> clerkInfoList) {
        this.mContext = mContext;
        this.clerkInfoList = clerkInfoList;
        requestManager = Glide.with(mContext);
    }

    @Override
    public ClerkInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ClerkInfoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_clerk, parent, false));
    }

    @Override
    public void onBindViewHolder(ClerkInfoViewHolder holder, int position) {
        final UserInfo user = clerkInfoList.get(position);
        holder.tvName.setText(user.getUser_name());
        holder.tvNumber.setText("工号：" + user.getJob_number());
        if (!StringUtils.isNullOrEmpty(user.getUrl())) {
            requestManager.load(user.getUrl()).into(holder.icon);
        }
        String department,title;
        if (user.getLevel().equals("1")) {
            department = "";
        } else {
            try {
                department = OaApplication.departmentList.getString(user.getDepartment_id());
            } catch (Exception e) {
                e.printStackTrace();
                department = "";
            }
        }
        switch (Integer.parseInt(user.getLevel())) {
            case 1:
                title = "行长";
                break;
            case 2:
                title = "经理";
                break;
            default:
                title = "业务员";
                break;
        }
        holder.tvTitle.setText(department + " " + title);
        holder.btCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SupervisorActivity.class);
                intent.putExtra("uid", user.getUid());
                intent.putExtra("user_level", user.getLevel());
                intent.putExtra("supervisor_id", OaApplication.getUser().getUid());
                intent.putExtra("supervisor_level", OaApplication.getUser().getLevel());
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return clerkInfoList.size();
    }

}



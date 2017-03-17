package com.meishipintu.bankoa.views.adapter;

import android.app.VoiceInteractor;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.meishipintu.bankoa.models.entity.ClerkInfo;
import com.meishipintu.bankoa.views.adapter.viewHolder.ClerkInfoViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 * <p>
 * 主要功能：
 */

public class ClerkListAdapter extends RecyclerView.Adapter<ClerkInfoViewHolder> {

    private Context mContext;
    private List<ClerkInfo> clerkInfoList;

    public ClerkListAdapter(Context mContext, List<ClerkInfo> clerkInfoList) {
        this.mContext = mContext;
        this.clerkInfoList = clerkInfoList;
    }

    @Override
    public ClerkInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ClerkInfoViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 0;
    }
}



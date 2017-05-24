package com.meishipintu.bankoa.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.models.entity.CommentDetail;
import com.meishipintu.bankoa.views.adapter.viewHolder.CommentViewHolder;
import com.meishipintu.library.util.DateUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/3/24.
 * <p>
 * 主要功能：
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentViewHolder> {


    private Context mContext;
    private List<CommentDetail> dataList;
    private RequestManager requestManager;
    private ReplyClickListener listener;


    public CommentsAdapter(Context mContext, List<CommentDetail> dataList, ReplyClickListener listener) {
        this.mContext = mContext;
        this.dataList = dataList;
        requestManager = Glide.with(mContext);
        this.listener = listener;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        final CommentDetail commentDetail = dataList.get(position);
        if (commentDetail.getUrl() != null) {
            requestManager.load(commentDetail.getUrl()).into(holder.ivHead);
        }
        holder.tvName.setText(commentDetail.getUser_name());
        holder.tvContent.setText(commentDetail.getComment_content());
        holder.tvTime.setText(DateUtil.formart3(commentDetail.getComment_time()));
        if (commentDetail.getComment() != null) {
            holder.btReply.setVisibility(View.GONE);
            holder.llRepay.setVisibility(View.VISIBLE);
            holder.tvRepayName.setText(commentDetail.getComment().getUser_name());
            holder.tvRepayContent.setText("回复： " + commentDetail.getComment().getComment_content());
        } else {
            holder.llRepay.setVisibility(View.GONE);
            holder.btReply.setVisibility(View.VISIBLE);
            holder.btReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(commentDetail);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

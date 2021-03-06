package com.meishipintu.bankoa.contracts;

import com.meishipintu.bankoa.models.entity.CenterBranch;
import com.meishipintu.bankoa.models.entity.CommentDetail;
import com.meishipintu.bankoa.models.entity.CommentInfo;
import com.meishipintu.bankoa.models.entity.NodeInfoNow;
import com.meishipintu.bankoa.models.entity.RemarkInfo;
import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.bankoa.presenters.BasicPresenter;
import com.meishipintu.bankoa.views.BasicView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/5.
 * <p>
 * 主要功能：TaskDetail页的接口管理
 */

public interface TaskDetailContract {

    interface IPresenter extends BasicPresenter {

        void getTaskInfo(String taskId);

        void setTaskNodeFinished(String uid, String taskId);

        void addNodeRemarks(String taskId, String taskLevel, String taskContent, String userId);

        void addNodeComment(CommentInfo commentInfo);

        void deletTask(String taskId);
    }


    interface IView extends BasicView {

        void onFinishNode();

        void recoverBtFinish();

        void recoverAdRemarkFinish();

        void onAddSuccess(int type);

        void showGraphic(NodeInfoNow nodeInfoNow);

        void showUserInfo(UserInfo userInfo);

        void showRemarks(List<RemarkInfo> remarkInfoList);

        void showComments(List<CommentDetail> commentDetailList);

        void onDeletSuccess();
    }

}

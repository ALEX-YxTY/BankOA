package com.meishipintu.bankoa.contracts;

import com.meishipintu.bankoa.models.entity.NodeInfoNow;
import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.bankoa.presenters.BasicPresenter;
import com.meishipintu.bankoa.views.BasicView;

/**
 * Created by Administrator on 2017/3/5.
 * <p>
 * 主要功能：TaskDetail页的接口管理
 */

public interface TaskDetailContract {

    interface IPresenter extends BasicPresenter {

        void getTaskInfo(String taskId);

        void setTaskNodeFinished();

        void addNodeRemarks(String taskId, String taskLevel, String remark, String userId);

        void addNodeComment(String supervisorId, String comment);
    }


    interface IView extends BasicView {

        void showTaskDetail();

        void showError(String errMsg);

        void showGraphic(NodeInfoNow nodeInfoNow);

        void showUserInfo(UserInfo userInfo);

    }

}

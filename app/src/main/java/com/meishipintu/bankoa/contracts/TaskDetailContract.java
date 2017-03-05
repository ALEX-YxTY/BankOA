package com.meishipintu.bankoa.contracts;

import com.meishipintu.bankoa.presenters.BasicPresenter;
import com.meishipintu.bankoa.views.BasicView;

/**
 * Created by Administrator on 2017/3/5.
 * <p>
 * 主要功能：TaskDetail页的接口管理
 */

public interface TaskDetailContract {

    interface IPresenter extends BasicPresenter {

        void getTaskDetail();

        void setTaskNodeFinished();

        void addNodeRemarks(String remark);
    }


    interface IView extends BasicView {

        void showRemarks(String remark);
    }

}

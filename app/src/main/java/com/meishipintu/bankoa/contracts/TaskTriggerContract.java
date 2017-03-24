package com.meishipintu.bankoa.contracts;

import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.presenters.BasicPresenter;
import com.meishipintu.bankoa.views.BasicView;

import java.util.List;

/**
 * Created by Administrator on 2017/3/2.
 * <p>
 * 主要功能：TaskTrigger页的view和presenter接口管理
 */

public interface TaskTriggerContract {

    interface IPresenter extends BasicPresenter {

        void getCenteralBranches();

        void getBranches();

        void getTaskType();

        void triggerTask(String loanerName, String loanMoney, String centerBranchType, String branchType
                , String taskType, String taskName, String recommendManager, String uid, String level);
    }


    interface IView extends BasicView {

        void showCenteralBranches(List<String> districts);

        void showBranches(List<String> branches);

        void showTaskType(List<String> type);

        void showTriggerResult(String result, boolean success,Task task);
    }

}

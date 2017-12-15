package com.meishipintu.bankoa.contracts;

import com.meishipintu.bankoa.models.entity.CenterBranch;
import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.presenters.BasicPresenter;
import com.meishipintu.bankoa.views.BasicView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/2.
 * <p>
 * 主要功能：TaskTrigger页的view和presenter接口管理
 */

public interface TaskTriggerContract {

    interface IPresenter extends BasicPresenter {

        void getCenteralBranches();

        void getBranches(int centerBranch);

        void getTaskType();

        void triggerTask(String loanerName, String loanMoney, int centerBranchType, int branchType
                , int taskType,String recommendManager, String uid, String level);
    }


    interface IView extends BasicView {

        void showCenteralBranches(List<CenterBranch> centerBranchList);

        void showBranches(Map<Integer, String> branches);

        void showTaskType(String[] types);

        void showTriggerResult(String result, boolean success,Task task);
    }

}

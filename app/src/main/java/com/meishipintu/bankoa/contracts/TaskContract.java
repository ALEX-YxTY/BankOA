package com.meishipintu.bankoa.contracts;

import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.presenters.BasicPresenter;
import com.meishipintu.bankoa.views.BasicView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/17.
 * <p>
 * 主要功能：
 */

public interface TaskContract {

    interface IPresenter extends BasicPresenter {
        void getTask(String uid, int type);

        void getCenterBranch();

        void getBranchList(int totalNum);
    }

    interface IView extends BasicView {

        void showTask(List<Task> taskList);

        void onCenterBranchGet(List<String> centerBranch);

        void onBranchListGet(int index, String[] branchList);

    }
}

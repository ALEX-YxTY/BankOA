package com.meishipintu.bankoa.contracts;

import com.meishipintu.bankoa.models.entity.TaskTriggerInfo;
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

        void getDistrcts();

        void getBranches();

        void triggerTask(TaskTriggerInfo info);
    }


    interface IView extends BasicView {

        void showDistricts(List<String> districts);

        void showBranches(List<String> branches);
    }

}

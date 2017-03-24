package com.meishipintu.bankoa.contracts;

import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.presenters.BasicPresenter;
import com.meishipintu.bankoa.views.BasicView;

import java.util.List;

/**
 * Created by Administrator on 2017/3/17.
 * <p>
 * 主要功能：
 */

public interface TaskContract {

    interface IPresenter extends BasicPresenter {
        void getTask(String uid, int type);
    }

    interface IView extends BasicView {

        void showTask(List<Task> taskList);

    }
}

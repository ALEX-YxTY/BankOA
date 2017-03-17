package com.meishipintu.bankoa.presenters;

import com.meishipintu.bankoa.contracts.TaskContract;
import com.meishipintu.bankoa.models.entity.Task;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/3/17.
 * <p>
 * 主要功能：
 */

public class TaskPresenterImp implements TaskContract.IPresenter {

    private TaskContract.IView iView;

    @Inject
    TaskPresenterImp(TaskContract.IView view) {
        this.iView = view;
    }

    @Override
    public void getTask(int type) {
        Task task = new Task();
        task.setId("7");
        task.setSponsor_id("70");
        task.setTask_name("贷款1");
        List<Task> list = new ArrayList<>();
        list.add(task);
        iView.showTask(list);

    }

    //from BasicPresenter.IPresenter
    @Override
    public void unSubscrib() {

    }

}

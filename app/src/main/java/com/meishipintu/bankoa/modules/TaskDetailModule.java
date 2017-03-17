package com.meishipintu.bankoa.modules;

import com.meishipintu.bankoa.contracts.TaskDetailContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/3/17.
 * <p>
 * 主要功能：
 */

@Module
public class TaskDetailModule {
    private TaskDetailContract.IView iView;

    public TaskDetailModule(TaskDetailContract.IView view) {
        this.iView = view;
    }

    @Provides
    TaskDetailContract.IView provideTaskDetailIView() {
        return this.iView;
    }
}

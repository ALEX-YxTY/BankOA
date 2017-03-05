package com.meishipintu.bankoa.modules;

import com.meishipintu.bankoa.contracts.TaskTriggerContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/3/2.
 * <p>
 * 主要功能：
 */

@Module()
public class TaskTriggrtModule {

    private TaskTriggerContract.IView iView;

    public TaskTriggrtModule(TaskTriggerContract.IView view) {
        this.iView = view;
    }

    @Provides
    TaskTriggerContract.IView provideView() {
        return iView;
    }
}

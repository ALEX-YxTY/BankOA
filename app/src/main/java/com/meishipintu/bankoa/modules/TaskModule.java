package com.meishipintu.bankoa.modules;

import com.meishipintu.bankoa.contracts.TaskContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/3/17.
 * <p>
 * 主要功能：
 */

@Module()
public class TaskModule {

    private TaskContract.IView iView;

    public TaskModule(TaskContract.IView view) {
        this.iView = view;
    }

    @Provides
    TaskContract.IView provideView() {
        return this.iView;
    }
}

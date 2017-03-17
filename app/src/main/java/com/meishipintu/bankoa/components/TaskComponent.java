package com.meishipintu.bankoa.components;

import com.meishipintu.bankoa.modules.TaskModule;
import com.meishipintu.bankoa.views.activities.TaskActivity;

import dagger.Component;

/**
 * Created by Administrator on 2017/3/17.
 * <p>
 * 主要功能：
 */

@Component(modules = TaskModule.class)
public interface TaskComponent {

    void inject(TaskActivity taskActivity);
}

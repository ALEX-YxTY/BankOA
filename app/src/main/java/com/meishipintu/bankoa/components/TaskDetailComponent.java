package com.meishipintu.bankoa.components;

import com.meishipintu.bankoa.modules.TaskDetailModule;
import com.meishipintu.bankoa.views.activities.TaskDetailActivity;

import dagger.Component;

/**
 * Created by Administrator on 2017/3/17.
 * <p>
 * 主要功能：
 */

@Component(modules = TaskDetailModule.class)
public interface TaskDetailComponent {

    void inject(TaskDetailActivity taskDetailActivity);
}

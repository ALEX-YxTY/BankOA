package com.meishipintu.bankoa.components;

import com.meishipintu.bankoa.modules.TaskTriggrtModule;
import com.meishipintu.bankoa.views.activities.TaskTriggerActivity;

import dagger.Component;

/**
 * Created by Administrator on 2017/3/2.
 * <p>
 * 主要功能： Dagger TaskTrigger页的Component
 */

@Component(modules = TaskTriggrtModule.class, dependencies = ApplicationComponent.class)
public interface TaskTriggrtComponent {

    void inject(TaskTriggerActivity triggerActivity);
}

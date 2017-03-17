package com.meishipintu.bankoa.components;

import com.meishipintu.bankoa.modules.LoginModule;
import com.meishipintu.bankoa.modules.TaskTriggrtModule;
import com.meishipintu.bankoa.views.activities.LoginActivity;
import com.meishipintu.bankoa.views.activities.TaskTriggerActivity;

import dagger.Component;

/**
 * Created by Administrator on 2017/3/2.
 * <p>
 * 主要功能： Dagger TaskTrigger页的Component
 */

@Component(modules = LoginModule.class)
public interface LoginComponent {

    void inject(LoginActivity loginActivity);
}

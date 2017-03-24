package com.meishipintu.bankoa.components;

import com.meishipintu.bankoa.modules.MainModule;
import com.meishipintu.bankoa.modules.SupervisorModule;
import com.meishipintu.bankoa.views.activities.MainActivity;
import com.meishipintu.bankoa.views.activities.SupervisorActivity;

import dagger.Component;

/**
 * Created by Administrator on 2017/3/23.
 * <p>
 * 主要功能：
 */

@Component(modules = SupervisorModule.class)
public interface SupervisorComponent {

    void inject(SupervisorActivity activity);
}

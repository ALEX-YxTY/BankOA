package com.meishipintu.bankoa.components;

import com.meishipintu.bankoa.modules.RegisterModule;
import com.meishipintu.bankoa.views.activities.RegisterActivity;

import dagger.Component;

/**
 * Created by Administrator on 2017/3/14.
 * <p>
 * 主要功能：
 */

@Component(modules = RegisterModule.class)
public interface RegisterComponent {

    void inject(RegisterActivity activity);
}

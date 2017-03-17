package com.meishipintu.bankoa.components;

import com.meishipintu.bankoa.modules.SettingModule;
import com.meishipintu.bankoa.views.activities.SettingActivity;

import dagger.Component;

/**
 * Created by Administrator on 2017/3/15.
 * <p>
 * 主要功能：
 */

@Component(modules = SettingModule.class)
public interface SettingComponent {

    void inject(SettingActivity settingActivity);
}

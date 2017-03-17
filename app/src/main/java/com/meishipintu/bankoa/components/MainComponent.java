package com.meishipintu.bankoa.components;

import com.meishipintu.bankoa.modules.MainModule;
import com.meishipintu.bankoa.views.activities.MainActivity;

import dagger.Component;
import dagger.Module;

/**
 * Created by Administrator on 2017/3/16.
 * <p>
 * 主要功能：
 */

@Component(modules = MainModule.class)
public interface MainComponent {

    void inject(MainActivity mainActivity);
}

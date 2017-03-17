package com.meishipintu.bankoa.components;

import android.content.Context;

import com.meishipintu.bankoa.modules.ApplicationModule;

import dagger.Component;

/**
 * Created by Administrator on 2017/3/3.
 * <p>
 * 主要功能：
 */

@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Context getContext();
}

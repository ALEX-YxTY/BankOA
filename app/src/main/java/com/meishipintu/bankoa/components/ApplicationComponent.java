package com.meishipintu.bankoa.components;

import android.content.Context;

import com.meishipintu.bankoa.modules.ApplicationModlue;

import dagger.Component;

/**
 * Created by Administrator on 2017/3/3.
 * <p>
 * 主要功能：
 */

@Component(modules = ApplicationModlue.class)
public interface ApplicationComponent {
    Context getContext();
}

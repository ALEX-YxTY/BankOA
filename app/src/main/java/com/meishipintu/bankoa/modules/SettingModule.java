package com.meishipintu.bankoa.modules;

import com.meishipintu.bankoa.contracts.SettingContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/3/15.
 * <p>
 * 主要功能：
 */

@Module
public class SettingModule {

    private SettingContract.IView iView;

    public SettingModule(SettingContract.IView view) {
        this.iView = view;
    }

    @Provides
    SettingContract.IView provideSettingIView() {
        return this.iView;
    }
}

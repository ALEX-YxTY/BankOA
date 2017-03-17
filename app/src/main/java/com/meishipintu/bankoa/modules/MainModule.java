package com.meishipintu.bankoa.modules;

import com.meishipintu.bankoa.contracts.MainContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/3/16.
 * <p>
 * 主要功能：
 */

@Module
public class MainModule {

    private MainContract.IView view;

    public MainModule(MainContract.IView iView) {
        this.view = iView;
    }

    @Provides
    MainContract.IView provideMianView() {
        return this.view;
    }
}

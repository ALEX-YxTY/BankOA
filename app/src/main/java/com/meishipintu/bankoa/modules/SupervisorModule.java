package com.meishipintu.bankoa.modules;

import com.meishipintu.bankoa.contracts.MainContract;
import com.meishipintu.bankoa.contracts.SupervisorContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/3/23.
 * <p>
 * 主要功能：
 */

@Module
public class SupervisorModule {

    private SupervisorContract.IView view;

    public SupervisorModule(SupervisorContract.IView iView) {
        this.view = iView;
    }

    @Provides
    SupervisorContract.IView provideMianView() {
        return this.view;
    }
}

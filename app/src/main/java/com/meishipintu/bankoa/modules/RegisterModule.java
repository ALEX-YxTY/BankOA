package com.meishipintu.bankoa.modules;

import com.meishipintu.bankoa.contracts.RegisterContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/3/14.
 * <p>
 * 主要功能：
 */

@Module
public class RegisterModule {
    private RegisterContract.IView iView;

    public RegisterModule(RegisterContract.IView view) {
        this.iView = view;
    }

    @Provides
    RegisterContract.IView provideIView() {
        return this.iView;
    }
}

package com.meishipintu.bankoa.modules;

import com.meishipintu.bankoa.contracts.LoginContract;
import com.meishipintu.bankoa.contracts.TaskTriggerContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/3/2.
 * <p>
 * 主要功能：
 */

@Module()
public class LoginModule {

    private LoginContract.IView iView;

    public LoginModule(LoginContract.IView view) {
        this.iView = view;
    }

    @Provides
    LoginContract.IView provideView() {
        return iView;
    }
}

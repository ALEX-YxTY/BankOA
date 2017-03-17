package com.meishipintu.bankoa.modules;

import com.meishipintu.bankoa.contracts.ClerkContract;
import com.meishipintu.bankoa.contracts.LoginContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/3/15.
 * <p>
 * 主要功能：
 */

@Module()
public class ClerkModule {

    private ClerkContract.IView iView;

    public ClerkModule(ClerkContract.IView view) {
        this.iView = view;
    }

    @Provides
    ClerkContract.IView provideView() {
        return iView;
    }
}

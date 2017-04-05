package com.meishipintu.bankoa.modules;

import com.meishipintu.bankoa.contracts.NoticContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/3/27.
 * <p>
 * 主要功能：
 */

@Module
public class NoticeModule {

    private NoticContract.IView iView;

    public NoticeModule(NoticContract.IView view) {
        this.iView = view;
    }

    @Provides
    public NoticContract.IView provideView() {
        return this.iView;
    }
}

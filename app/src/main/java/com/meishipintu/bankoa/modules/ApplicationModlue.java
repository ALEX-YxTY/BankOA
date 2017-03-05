package com.meishipintu.bankoa.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/3/3.
 * <p>
 * 主要功能：
 */

@Module
public class ApplicationModlue {

    private Context context;

    public ApplicationModlue(Context context) {
        this.context = context;
    }

    @Provides
    Context provideContext() {
        return this.context;
    }
}

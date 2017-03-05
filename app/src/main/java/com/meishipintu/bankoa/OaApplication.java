package com.meishipintu.bankoa;

import android.app.Application;

import com.meishipintu.bankoa.components.ApplicationComponent;
import com.meishipintu.bankoa.components.DaggerApplicationComponent;
import com.meishipintu.bankoa.modules.ApplicationModlue;

/**
 * Created by Administrator on 2017/3/1.
 */

public class OaApplication extends Application {

    private ApplicationComponent applicationComponent;
    private static OaApplication instance;

    public static OaApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModlue(new ApplicationModlue(this))
                .build();
        instance = this;
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}

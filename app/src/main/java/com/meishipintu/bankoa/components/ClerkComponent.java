package com.meishipintu.bankoa.components;

import com.meishipintu.bankoa.modules.ClerkModule;
import com.meishipintu.bankoa.modules.LoginModule;
import com.meishipintu.bankoa.views.activities.ClerkListActivity;
import com.meishipintu.bankoa.views.activities.LoginActivity;
import com.meishipintu.bankoa.views.adapter.ClerkListAdapter;

import dagger.Component;

/**
 * Created by Administrator on 2017/3/15.
 * <p>
 * 主要功能： Dagger ClerkList页的Component
 */

@Component(modules = ClerkModule.class)
public interface ClerkComponent {

    void inject(ClerkListActivity loginActivity);
}

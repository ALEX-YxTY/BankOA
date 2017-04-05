package com.meishipintu.bankoa.components;

import com.meishipintu.bankoa.modules.NoticeModule;
import com.meishipintu.bankoa.views.activities.NoticActivity;

import dagger.Component;

/**
 * Created by Administrator on 2017/3/27.
 * <p>
 * 主要功能：
 */

@Component(modules = NoticeModule.class)
public interface NoticeComponent {

    void inject(NoticActivity activity);
}

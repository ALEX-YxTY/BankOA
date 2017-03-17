package com.meishipintu.bankoa.components;

import com.meishipintu.bankoa.modules.SearchMoudule;
import com.meishipintu.bankoa.views.activities.SearchActivity;

import dagger.Component;
import dagger.Module;

/**
 * Created by Administrator on 2017/3/15.
 * <p>
 * 主要功能：
 */

@Component(modules = SearchMoudule.class)
public interface SearchComponent {

    void inject(SearchActivity searchActivity);
}

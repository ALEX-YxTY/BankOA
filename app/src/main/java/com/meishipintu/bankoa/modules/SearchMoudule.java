package com.meishipintu.bankoa.modules;

import com.meishipintu.bankoa.contracts.SearchContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/3/15.
 * <p>
 * 主要功能：
 */

@Module
public class SearchMoudule {

    private SearchContract.IView iView;

    public SearchMoudule(SearchContract.IView view) {
        this.iView = view;
    }

    @Provides
    SearchContract.IView provideSearchIView() {
        return this.iView;
    }
}

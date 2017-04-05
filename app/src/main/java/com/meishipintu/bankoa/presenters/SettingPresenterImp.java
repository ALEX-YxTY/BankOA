package com.meishipintu.bankoa.presenters;

import com.meishipintu.bankoa.contracts.SettingContract;
import com.meishipintu.bankoa.models.PreferenceHelper;

import javax.inject.Inject;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/3/2.
 * <p>
 *
 * 主要功能：TaskTrigger页的依赖Module
 */

public class SettingPresenterImp implements SettingContract.IPresenter {

    private SettingContract.IView view;

    @Inject
    SettingPresenterImp(SettingContract.IView view) {
        this.view = view;
    }

    @Override
    public void clearCache() {
        //清理缓存
        PreferenceHelper.clear();
        view.showError("内存清理成功");
    }

    @Override
    public void logout() {
        PreferenceHelper.clear();
        view.onLogoutSuccess();
    }

    //from BasicPresenter
    @Override
    public void unSubscrib() {

    }
}

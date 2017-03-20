package com.meishipintu.bankoa.presenters;

import android.content.Context;

import com.meishipintu.bankoa.contracts.SettingContract;
import com.meishipintu.bankoa.contracts.TaskTriggerContract;
import com.meishipintu.bankoa.models.PreferenceHelper;
import com.meishipintu.bankoa.models.entity.TaskTriggerInfo;
import com.meishipintu.bankoa.models.http.HttpApi;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

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
        //TODO 清理缓存
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

package com.meishipintu.bankoa.presenters;

import com.meishipintu.bankoa.contracts.MainContract;
import com.meishipintu.bankoa.models.PreferenceHelper;
import com.meishipintu.bankoa.models.entity.UserInfo;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/3/16.
 * <p>
 * 主要功能：
 */

public class MainPresenterImp implements MainContract.IPresenter {

    private MainContract.IView iView;

    @Inject
    public MainPresenterImp(MainContract.IView view) {
        this.iView = view;
    }

    @Override
    public void getUserInfo() {
        UserInfo userInfo = PreferenceHelper.getUserInfo();
        iView.refreshUI(userInfo);
    }

    //from BasicPresenter.IPresenter
    @Override
    public void unSubscrib() {

    }
}

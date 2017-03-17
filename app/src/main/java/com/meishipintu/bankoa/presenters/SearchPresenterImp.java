package com.meishipintu.bankoa.presenters;

import com.meishipintu.bankoa.contracts.SearchContract;
import com.meishipintu.bankoa.models.http.HttpApi;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/3/15.
 * <p>
 * 主要功能：
 */

public class SearchPresenterImp implements SearchContract.IPresenter {

    private SearchContract.IView iView;
    private HttpApi httpApi;

    @Inject
    SearchPresenterImp(SearchContract.IView view) {
        this.iView = view;
        httpApi = HttpApi.getInstance();
    }

    @Override
    public void search(String searchContent) {
        //TODO 调用search接口
    }

    //from BasicPresenter
    @Override
    public void unSubscrib() {

    }
}

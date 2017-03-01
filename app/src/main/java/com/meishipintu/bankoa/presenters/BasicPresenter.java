package com.meishipintu.bankoa.presenters;

/**
 * Created by Administrator on 2017/3/1.
 */

public interface BasicPresenter {

    //订阅网络数据
    void subscrib();

    //解绑Observable
    void unSubscrib();
}

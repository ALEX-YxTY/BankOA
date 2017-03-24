package com.meishipintu.bankoa.contracts;

import com.meishipintu.bankoa.presenters.BasicPresenter;
import com.meishipintu.bankoa.views.BasicView;

/**
 * Created by Administrator on 2017/3/13.
 * <p>
 * 主要功能：
 */

public interface LoginContract {

    interface IPresenter extends BasicPresenter {

        void login(String tel, String psw, boolean savePsw);
    }

    interface IView extends BasicView {

        void startMain();

    }
}

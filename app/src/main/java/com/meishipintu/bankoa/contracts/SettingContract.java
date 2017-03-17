package com.meishipintu.bankoa.contracts;

import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.bankoa.presenters.BasicPresenter;
import com.meishipintu.bankoa.views.BasicView;

/**
 * Created by Administrator on 2017/3/13.
 * <p>
 * 主要功能：
 */

public interface SettingContract {

    interface IPresenter extends BasicPresenter {

        void clearCache();

    }

    interface IView extends BasicView {

        void showResult();
    }
}

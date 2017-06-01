package com.meishipintu.bankoa.contracts;

import com.meishipintu.bankoa.models.entity.UserInfo;
import com.meishipintu.bankoa.presenters.BasicPresenter;
import com.meishipintu.bankoa.views.BasicView;

/**
 * Created by Administrator on 2017/3/1.
 *
 * 综合管理MainActivity下的presenter和view接口
 */

public interface MainContract {

    interface IPresenter extends BasicPresenter {

        void getUserInfo();

        void getNewestNotice();

        void getNewestRemind(String uid);

        void getCenterBranchList();

        void getTaskTypeList();

        void getDepartmentList();
    }

    interface IView extends BasicView {

        void refreshUI(UserInfo userInfo);

        void dealNewestNotice(int number);

        void dealNewestRemind(int number);

    }

}

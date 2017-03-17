package com.meishipintu.bankoa.contracts;

import com.meishipintu.bankoa.models.entity.TaskTriggerInfo;
import com.meishipintu.bankoa.presenters.BasicPresenter;
import com.meishipintu.bankoa.views.BasicView;

import java.util.List;

/**
 * Created by Administrator on 2017/3/13.
 * <p>
 * 主要功能：注册页接口管理
 */

public interface RegisterContract {

    interface IPresenter extends BasicPresenter {

        void getVerifyCode(String tel);

        void register(String tel, String verifyCode, String psw);

        void changePsw(String tel, String verifyCode, String pswNew);

        void rebingTel(String telNew, String verifyCode, String psw);
    }


    interface IView extends BasicView {

        void showBtEnableTime(String codeGet, boolean sucess);

        void showResult(String result);

        void showToast(String toast);
    }

}

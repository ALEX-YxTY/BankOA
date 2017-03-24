package com.meishipintu.bankoa.contracts;

import com.meishipintu.bankoa.models.entity.PaymentInfo;
import com.meishipintu.bankoa.presenters.BasicPresenter;
import com.meishipintu.bankoa.views.BasicView;

/**
 * Created by Administrator on 2017/3/21.
 * <p>
 * 主要功能：
 */

public interface PaymentEnterContract {

    interface IPresenter extends BasicPresenter {

        void enterPayment(PaymentInfo info);
    }

    interface IView extends BasicView {

        void onEnterSuccess();

    }
}

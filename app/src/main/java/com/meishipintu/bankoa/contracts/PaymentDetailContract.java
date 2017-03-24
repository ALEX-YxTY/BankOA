package com.meishipintu.bankoa.contracts;

import com.meishipintu.bankoa.models.entity.PaymentInfo;
import com.meishipintu.bankoa.presenters.BasicPresenter;
import com.meishipintu.bankoa.views.BasicView;

/**
 * Created by Administrator on 2017/3/22.
 * <p>
 * 主要功能：
 */

public interface PaymentDetailContract {

    interface IPresenter extends BasicPresenter {

        void getPaymentInfo(String taskId);

        void finishPaymen(String taskid, String paymentId, String money);
    }

    interface IView extends BasicView {

        void onPaymentFinish();

        void onPaymentInfoGet(PaymentInfo info);
    }
}

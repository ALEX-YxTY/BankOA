package com.meishipintu.bankoa.modules;

import com.meishipintu.bankoa.contracts.PaymentDetailContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/3/22.
 * <p>
 * 主要功能：
 */

@Module
public class PaymentDetailModule {
    private PaymentDetailContract.IView iView;

    public PaymentDetailModule(PaymentDetailContract.IView iView) {
        this.iView = iView;
    }

    @Provides
    PaymentDetailContract.IView providePaymentDetialIView() {
        return this.iView;
    }
}

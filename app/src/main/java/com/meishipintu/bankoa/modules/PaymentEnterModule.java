package com.meishipintu.bankoa.modules;

import com.meishipintu.bankoa.contracts.PaymentEnterContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/3/21.
 * <p>
 * 主要功能：
 */

@Module
public class PaymentEnterModule {

    private PaymentEnterContract.IView view;

    public PaymentEnterModule(PaymentEnterContract.IView iView) {
        this.view = iView;
    }

    @Provides
    PaymentEnterContract.IView providePaymentEnterIView() {
        return this.view;
    }
}

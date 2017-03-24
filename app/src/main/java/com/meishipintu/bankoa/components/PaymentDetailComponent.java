package com.meishipintu.bankoa.components;

import com.meishipintu.bankoa.modules.PaymentDetailModule;
import com.meishipintu.bankoa.views.activities.PaymentDetailActivity;

import dagger.Component;

/**
 * Created by Administrator on 2017/3/22.
 * <p>
 * 主要功能：
 */

@Component(modules = PaymentDetailModule.class)
public interface PaymentDetailComponent {

    void inject(PaymentDetailActivity activity);
}

package com.meishipintu.bankoa.components;

import com.meishipintu.bankoa.modules.PaymentEnterModule;
import com.meishipintu.bankoa.views.activities.PaymentEnterActivity;

import dagger.Component;

/**
 * Created by Administrator on 2017/3/21.
 * <p>
 * 主要功能：
 */

@Component(modules = PaymentEnterModule.class)
public interface PaymentEnterComponent {

    void inject(PaymentEnterActivity activity);
}

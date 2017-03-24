package com.meishipintu.bankoa.models.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/22.
 * <p>
 * 主要功能：
 */

public class paymentItem implements Serializable {
    private static final long serialVersionUID = 10L;          //序列化验证码

    private String repayment_time;
    private String repayment_money;

    public paymentItem(String repayment_time, String repayment_money) {
        this.repayment_time = repayment_time;
        this.repayment_money = repayment_money;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getRepayment_time() {
        return repayment_time;
    }

    public void setRepayment_time(String repayment_time) {
        this.repayment_time = repayment_time;
    }

    public String getRepayment_money() {
        return repayment_money;
    }

    public void setRepayment_money(String repayment_money) {
        this.repayment_money = repayment_money;
    }
}

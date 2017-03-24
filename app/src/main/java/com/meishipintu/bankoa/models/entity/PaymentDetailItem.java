package com.meishipintu.bankoa.models.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/22.
 * <p>
 * 主要功能：
 */

public class PaymentDetailItem implements Serializable {
    private static final long serialVersionUID = 10L;          //序列化验证码

    private String id;
    private String repayment_time;
    private String repayment_money;
    private boolean is_finish;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean is_finish() {
        return is_finish;
    }

    public void setIs_finish(boolean is_finish) {
        this.is_finish = is_finish;
    }

    public PaymentDetailItem(String id,String repayment_time, String repayment_money,String isfinish) {
        this.id = id;
        this.repayment_time = repayment_time;
        this.repayment_money = repayment_money;
        this.is_finish = "1".equals(isfinish);
    }

    public PaymentDetailItem(String repayment_time, String repayment_money) {
        this.repayment_time = repayment_time;
        this.repayment_money = repayment_money;
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

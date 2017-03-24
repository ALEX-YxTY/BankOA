package com.meishipintu.bankoa.models.entity;

import android.widget.ListView;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 * <p>
 * 主要功能：
 */

public class PaymentInfo implements Serializable {
    private static final long serialVersionUID = 10L;          //序列化验证码

    private String check_money;
    private String result_money;
    private String loan_time;
    private String task_id;
    private List<PaymentDetailItem> repayment_json;

    public PaymentInfo() {
    }

    public PaymentInfo(String check_money, String result_money, String load_time, String task_id, List<PaymentDetailItem> repayment_json) {
        this.check_money = check_money;
        this.result_money = result_money;
        this.loan_time = load_time;
        this.task_id = task_id;
        this.repayment_json = repayment_json;
    }

    public String getCheck_money() {
        return check_money;
    }

    public void setCheck_money(String check_money) {
        this.check_money = check_money;
    }

    public String getResult_money() {
        return result_money;
    }

    public void setResult_money(String result_money) {
        this.result_money = result_money;
    }

    public String getLoad_time() {
        return loan_time;
    }

    public void setLoad_time(String load_time) {
        this.loan_time = load_time;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public List<PaymentDetailItem> getRepayment_json() {
        return repayment_json;
    }

    public void setRepayment_json(List<PaymentDetailItem> repayment_json) {
        this.repayment_json = repayment_json;
    }

    @Override
    public String toString() {
        return "PaymentInfo{" +
                "check_money='" + check_money + '\'' +
                ", result_money='" + result_money + '\'' +
                ", loan_time='" + loan_time + '\'' +
                ", task_id='" + task_id + '\'' +
                ", repayment_json=" + repayment_json +
                '}';
    }
}

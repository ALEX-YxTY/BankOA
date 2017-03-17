package com.meishipintu.bankoa.models.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/15.
 * <p>
 * 主要功能：
 */

public class Task implements Serializable{

    private static final long serialVerisionUID = 10L;          //序列化验证码

    private String id;                  //taskId
    private String credi_name;
    private String apply_money;
    private String credit_center_branch;
    private String credit_branch;
    private String task_type;
    private String task_name;
    private String sponsor_id;
    private String sponsor_level;
    private String create_time;         //任务生成时间
    private String check_money;         //最终审批金额
    private String result_money;        //最终贷款金额
    private String finish_time;         //任务完成时间
    private String loan_time;           //发放贷款时间


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCredi_name() {
        return credi_name;
    }

    public void setCredi_name(String credi_name) {
        this.credi_name = credi_name;
    }

    public String getApply_money() {
        return apply_money;
    }

    public void setApply_money(String apply_money) {
        this.apply_money = apply_money;
    }

    public String getCredit_center_branch() {
        return credit_center_branch;
    }

    public void setCredit_center_branch(String credit_center_branch) {
        this.credit_center_branch = credit_center_branch;
    }

    public String getCredit_branch() {
        return credit_branch;
    }

    public void setCredit_branch(String credit_branch) {
        this.credit_branch = credit_branch;
    }

    public String getTask_type() {
        return task_type;
    }

    public void setTask_type(String task_type) {
        this.task_type = task_type;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getSponsor_id() {
        return sponsor_id;
    }

    public void setSponsor_id(String sponsor_id) {
        this.sponsor_id = sponsor_id;
    }

    public String getSponsor_level() {
        return sponsor_level;
    }

    public void setSponsor_level(String sponsor_level) {
        this.sponsor_level = sponsor_level;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
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

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public String getLoan_time() {
        return loan_time;
    }

    public void setLoan_time(String loan_time) {
        this.loan_time = loan_time;
    }
}

package com.meishipintu.bankoa.models.entity;

/**
 * Created by Administrator on 2017/3/2.
 * <p>
 * 主要功能：
 */

public class TaskTriggerInfo {

    private String credi_name;
    private String apply_money;
    private String credit_center_branch;
    private String credit_branch;
    private String task_type;
    private String task_name;
    private String credit_manager;
    private String sponsor_id;
    private String sponsor_level;

    public TaskTriggerInfo(String credi_name, String apply_money, String credit_center_branch
            , String credit_branch, String task_type, String task_name, String credit_manager
            , String sponsor_id, String sponsor_level) {
        this.credi_name = credi_name;
        this.apply_money = apply_money;
        this.credit_center_branch = credit_center_branch;
        this.credit_branch = credit_branch;
        this.task_type = task_type;
        this.task_name = task_name;
        this.credit_manager = credit_manager;
        this.sponsor_id = sponsor_id;
        this.sponsor_level = sponsor_level;
    }
}

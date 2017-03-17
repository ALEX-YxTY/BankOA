package com.meishipintu.bankoa.models.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/17.
 * <p>
 * 主要功能：
 */

public class RemarkInfo implements Serializable {

    private String id;
    private String task_id;
    private String task_level;
    private String remark_content;
    private String remark_user_id;
    private String remark_time;

    public RemarkInfo(String taskId, String taskLevelNow, String input, String sponsorId) {
        this.task_id = taskId;
        this.task_level = taskLevelNow;
        this.remark_content = input;
        this.remark_user_id = sponsorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getRemark_content() {
        return remark_content;
    }

    public void setRemark_content(String remark_content) {
        this.remark_content = remark_content;
    }

    public String getRemark_user_id() {
        return remark_user_id;
    }

    public void setRemark_user_id(String remark_user_id) {
        this.remark_user_id = remark_user_id;
    }

    public String getRemark_time() {
        return remark_time;
    }

    public void setRemark_time(String remark_time) {
        this.remark_time = remark_time;
    }

    public String getTask_level() {
        return task_level;
    }

    public void setTask_level(String task_level) {
        this.task_level = task_level;
    }
}

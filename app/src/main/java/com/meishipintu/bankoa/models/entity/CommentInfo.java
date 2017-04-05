package com.meishipintu.bankoa.models.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/24.
 * <p>
 * 主要功能：
 */

public class CommentInfo implements Serializable {

    private static final long serialVersionUID = 10L;          //序列化验证码

    private String taskOwner;
    private String userId;
    private String userLevel;
    private String task_id;
    private String task_level;
    private String comment_content;
    private String pid;                 //父节点id，默认为0

    public String getTaskOwner() {
        return taskOwner;
    }

    public void setTaskOwner(String taskOwner) {
        this.taskOwner = taskOwner;
    }

    public CommentInfo(String taskOwner, String userId, String userLevel, String task_id, String task_level, String comment_content, String pid) {
        this.taskOwner = taskOwner;
        this.userId = userId;
        this.userLevel = userLevel;
        this.task_id = task_id;
        this.task_level = task_level;
        this.comment_content = comment_content;
        this.pid = pid;
    }

    public CommentInfo(String userId, String userLevel, String task_id, String task_level, String comment_content) {
        this.userId = userId;
        this.userLevel = userLevel;
        this.task_id = task_id;
        this.task_level = task_level;
        this.comment_content = comment_content;
        this.pid = "0";
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getTask_level() {
        return task_level;
    }

    public void setTask_level(String task_level) {
        this.task_level = task_level;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "CommentInfo{" +
                "userId='" + userId + '\'' +
                ", userLevel='" + userLevel + '\'' +
                ", task_id='" + task_id + '\'' +
                ", task_level='" + task_level + '\'' +
                ", comment_content='" + comment_content + '\'' +
                ", pid='" + pid + '\'' +
                '}';
    }
}

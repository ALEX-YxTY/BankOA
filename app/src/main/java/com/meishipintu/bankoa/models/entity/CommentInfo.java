package com.meishipintu.bankoa.models.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/24.
 * <p>
 * 主要功能：
 */

public class CommentInfo implements Serializable {

    private static final long serialVersionUID = 10L;          //序列化验证码

    private String commend_user_ed;        //被评论人
    private String commend_user_owner;     //评论人
    private String commend_user_owner_level;              //评论人level
    private String task_id;
    private String task_level;
    private String comment_content;
    private String pid;                 //父节点id，默认为0

    public String getCommend_user_ed() {
        return commend_user_ed;
    }

    public void setCommend_user_ed(String commend_user_ed) {
        this.commend_user_ed = commend_user_ed;
    }

    public CommentInfo(String commend_user_ed, String commend_user_owner, String commend_user_owner_level, String task_id, String task_level, String comment_content, String pid) {
        this.commend_user_ed = commend_user_ed;
        this.commend_user_owner = commend_user_owner;
        this.commend_user_owner_level = commend_user_owner_level;
        this.task_id = task_id;
        this.task_level = task_level;
        this.comment_content = comment_content;
        this.pid = pid;
    }

    public CommentInfo(String commend_user_owner, String commend_user_owner_level, String task_id, String task_level, String comment_content) {
        this.commend_user_owner = commend_user_owner;
        this.commend_user_owner_level = commend_user_owner_level;
        this.task_id = task_id;
        this.task_level = task_level;
        this.comment_content = comment_content;
        this.pid = "0";
    }

    public String getCommend_user_owner() {
        return commend_user_owner;
    }

    public void setCommend_user_owner(String commend_user_owner) {
        this.commend_user_owner = commend_user_owner;
    }

    public String getCommend_user_owner_level() {
        return commend_user_owner_level;
    }

    public void setCommend_user_owner_level(String commend_user_owner_level) {
        this.commend_user_owner_level = commend_user_owner_level;
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
                "commend_user_owner='" + commend_user_owner + '\'' +
                ", commend_user_owner_level='" + commend_user_owner_level + '\'' +
                ", task_id='" + task_id + '\'' +
                ", task_level='" + task_level + '\'' +
                ", comment_content='" + comment_content + '\'' +
                ", pid='" + pid + '\'' +
                '}';
    }
}

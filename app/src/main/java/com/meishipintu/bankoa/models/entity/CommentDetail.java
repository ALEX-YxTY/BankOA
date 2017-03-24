package com.meishipintu.bankoa.models.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/3/24.
 * <p>
 * 主要功能：
 */

public class CommentDetail implements Serializable{

    private static final long serialVersionUID = 10L;          //序列化验证码

    private String id;
    private String comment_user_id;
    private String comment_content;
    private String comment_time;
    private String pid;
    private String user_name;
    private String url;
    private CommentDetail comment;

    public CommentDetail getComment() {
        return comment;
    }

    public void setComment(CommentDetail comment) {
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment_user_id() {
        return comment_user_id;
    }

    public void setComment_user_id(String comment_user_id) {
        this.comment_user_id = comment_user_id;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "CommentDetail{" +
                "id='" + id + '\'' +
                ", comment_user_id='" + comment_user_id + '\'' +
                ", comment_content='" + comment_content + '\'' +
                ", comment_time='" + comment_time + '\'' +
                ", pid='" + pid + '\'' +
                ", user_name='" + user_name + '\'' +
                ", url='" + url + '\'' +
                ", comment=" + comment +
                '}';
    }
}

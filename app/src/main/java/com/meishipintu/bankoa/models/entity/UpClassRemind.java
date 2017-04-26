package com.meishipintu.bankoa.models.entity;

/**
 * Created by Administrator on 2017/3/27.
 * <p>
 * 主要功能： 上级提醒封装类
 */

public class UpClassRemind {

    private String id;
    private String notice_title;
    private String notice_content;
    private String create_time;
    private String task_id;
    private String url;
    private String user_id;
    private String type;
    private Task task_info;

    public Task getTask_info() {
        return task_info;
    }

    public void setTask_info(Task task_info) {
        this.task_info = task_info;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotice_title() {
        return notice_title;
    }

    public void setNotice_title(String notice_title) {
        this.notice_title = notice_title;
    }

    public String getNotice_content() {
        return notice_content;
    }

    public void setNotice_content(String notice_content) {
        this.notice_content = notice_content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "UpClassRemind{" +
                "id='" + id + '\'' +
                ", notice_title='" + notice_title + '\'' +
                ", notice_content='" + notice_content + '\'' +
                ", create_time='" + create_time + '\'' +
                ", task_id='" + task_id + '\'' +
                ", url='" + url + '\'' +
                ", user_id='" + user_id + '\'' +
                ", type='" + type + '\'' +
                ", task_info=" + (task_info==null?"null":task_info.toString()) +
                '}';
    }
}

package com.meishipintu.bankoa.models.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/13.
 * <p>
 * 主要功能： 个人信息实体类
 */

public class UserInfo implements Serializable{

    private String id;
    private String uid;
    private String user_name;
    private String department_id;
    private String job_number;
    private String credit_number;
    private String level;
    private String mobile;
    private String url;
    private String department_name;

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }

    public String getJob_number() {
        return job_number;
    }

    public void setJob_number(String job_number) {
        this.job_number = job_number;
    }

    public String getCredit_number() {
        return credit_number;
    }

    public void setCredit_number(String credit_number) {
        this.credit_number = credit_number;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", user_name='" + user_name + '\'' +
                ", department_id='" + department_id + '\'' +
                ", job_number='" + job_number + '\'' +
                ", credit_number='" + credit_number + '\'' +
                ", level='" + level + '\'' +
                ", mobile='" + mobile + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

package com.meishipintu.bankoa.models.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/15.
 * <p>
 * 主要功能：
 */

public class CenterBranch implements Serializable {
    private int id;
    private String branch;

    public CenterBranch() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    @Override
    public String toString() {
        return "CenterBranch{" +
                "id=" + id +
                ", branch='" + branch + '\'' +
                '}';
    }
}

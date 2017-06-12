package com.meishipintu.bankoa.models.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/12.
 * <p>
 * 主要功能：
 */

public class BranchUserInfo implements Serializable {

    private String account;
    private int center_branch;
    private int branch;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getCenter_branch() {
        return center_branch;
    }

    public void setCenter_branch(int center_branch) {
        this.center_branch = center_branch;
    }

    public int getBranch() {
        return branch;
    }

    public void setBranch(int branch) {
        this.branch = branch;
    }
}

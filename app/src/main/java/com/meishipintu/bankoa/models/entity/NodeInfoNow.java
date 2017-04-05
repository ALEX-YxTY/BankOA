package com.meishipintu.bankoa.models.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/17.
 * <p>
 * 主要功能：
 */

public class NodeInfoNow implements Serializable {
    private String nodeNowLevel;
    private String nodeNowName;
    private String nodeBeforeName;
    private boolean nodeBeforeCs;
    private String nodeAfterName;
    private String timeRemain;
    private String taskname;

    public NodeInfoNow(String nodeNowLevel, String nodeNowName, String nodeBeforeName, boolean nodeBeforeCs
            , String nodeAfterName, String timeRemain, String taskname) {
        this.nodeNowLevel = nodeNowLevel;
        this.nodeNowName = nodeNowName;
        this.nodeBeforeName = nodeBeforeName;
        this.nodeBeforeCs = nodeBeforeCs;
        this.nodeAfterName = nodeAfterName;
        this.timeRemain = timeRemain;
        this.taskname = taskname;
    }

    public boolean isNodeBeforeCs() {
        return nodeBeforeCs;
    }

    public void setNodeBeforeCs(boolean nodeBeforeCs) {
        this.nodeBeforeCs = nodeBeforeCs;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getNodeNowLevel() {
        return nodeNowLevel;
    }

    public void setNodeNowLevel(String nodeNowLevel) {
        this.nodeNowLevel = nodeNowLevel;
    }

    public String getNodeNowName() {
        return nodeNowName;
    }

    public void setNodeNowName(String nodeNowName) {
        this.nodeNowName = nodeNowName;
    }

    public String getNodeBeforeName() {
        return nodeBeforeName;
    }

    public void setNodeBeforeName(String nodeBeforeName) {
        this.nodeBeforeName = nodeBeforeName;
    }

    public String getNodeAfterName() {
        return nodeAfterName;
    }

    public void setNodeAfterName(String nodeAfterName) {
        this.nodeAfterName = nodeAfterName;
    }

    public String getTimeRemain() {
        return timeRemain;
    }

    public void setTimeRemain(String timeRemain) {
        this.timeRemain = timeRemain;
    }

    @Override
    public String toString() {
        return "NodeInfoNow{" +
                "nodeNowLevel='" + nodeNowLevel + '\'' +
                ", nodeNowName='" + nodeNowName + '\'' +
                ", nodeBeforeName='" + nodeBeforeName + '\'' +
                ", nodeAfterName='" + nodeAfterName + '\'' +
                ", timeRemain='" + timeRemain + '\'' +
                '}';
    }
}

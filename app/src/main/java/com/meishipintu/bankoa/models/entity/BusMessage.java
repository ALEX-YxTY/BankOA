package com.meishipintu.bankoa.models.entity;

/**
 * Created by Administrator on 2017/2/16.
 */

public class BusMessage {

    private int messageType;
    private String plusMessage;
    private int plusInt;

    public BusMessage(int messageType) {
        this.messageType = messageType;
    }

    public BusMessage(int messageType, String plusMessage) {
        this.messageType = messageType;
        this.plusMessage = plusMessage;
    }

    public BusMessage(int messageType, int plusInt) {
        this.messageType = messageType;
        this.plusInt = plusInt;
    }

    public int getPlusInt() {
        return plusInt;
    }

    public void setPlusInt(int plusInt) {
        this.plusInt = plusInt;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getPlusMessage() {
        return plusMessage;
    }

    public void setPlusMessage(String plusMessage) {
        this.plusMessage = plusMessage;
    }
}

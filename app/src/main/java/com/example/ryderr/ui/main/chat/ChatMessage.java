package com.example.ryderr.ui.main.chat;

public class ChatMessage {
    private String msgText;
    private String msgTime;

    public ChatMessage() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String userId;
    public final static String MSG_RECEIVED = "MSG_RECEIVED";
    public final static String MSG_SENT = "MSG_SENT";
    private String msgType;


    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public ChatMessage(String msgText, String msgTime, String userId) {
        this.msgText = msgText;
        this.msgTime = msgTime;
        this.userId = userId;
    }

    public ChatMessage(String msgText, String msgTime){
        this.msgText = msgText;
        this.msgTime = msgTime;
        this.msgType = msgType;
    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    public String getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }
}

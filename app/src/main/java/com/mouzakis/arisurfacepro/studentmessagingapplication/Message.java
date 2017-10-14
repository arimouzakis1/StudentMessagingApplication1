package com.mouzakis.arisurfacepro.studentmessagingapplication;

import java.util.Date;

/**
 * Created by AriSurfacePro on 14/10/2017.
 */

public class Message {
    private String user;
    private String messageText;
    private long messageTime;

    public Message(String user, String messageText) {
        this.user = user;
        this.messageText = messageText;
        this.messageTime = new Date().getTime();
    }

    public Message() {

    }

    public String getUser() {
        return user;
    }

    public void setMessage(String message) {
        this.user = message;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public String getMessageTimeString() {
        return Long.toString(messageTime);
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}

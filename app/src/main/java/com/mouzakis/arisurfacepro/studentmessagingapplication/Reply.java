package com.mouzakis.arisurfacepro.studentmessagingapplication;

import java.util.Date;

/**
 * Created by AriSurfacePro on 19/10/2017.
 */

public class Reply {
    private String replyingUser;
    private long timeOfReply;
    private String replyText;

    public Reply(String replyingUser, String replyText) {
        this.replyingUser = replyingUser;
        this.timeOfReply = new Date().getTime();
        this.replyText = replyText;
    }

    public Reply() {

    }

    public String getReplyingUser() {
        return replyingUser;
    }

    public long getTimeOfReply() {
        return timeOfReply;
    }

    public String getReplyText() {
        return replyText;
    }
}

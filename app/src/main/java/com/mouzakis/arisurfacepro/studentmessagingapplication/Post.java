package com.mouzakis.arisurfacepro.studentmessagingapplication;

import java.util.Date;

/**
 * Created by AriSurfacePro on 18/10/2017.
 */

public class Post {
    private String posteeName;
    private String postHeading;
    private String postQuestion;
    private long postTime;
    private Integer numberOfRepliesCount;

    public Post(String posteeName, String postHeading, String postQuestion) {
        this.posteeName = posteeName;
        this.postHeading = postHeading;
        this.postQuestion = postQuestion;
        this.postTime = new Date().getTime();
        this.numberOfRepliesCount = 0;
    }

    public Post() {

    }

    public String getPosteeName() {
        return posteeName;
    }

    public void setPosteeName(String posteeName) {
        this.posteeName = posteeName;
    }

    public String getPostHeading() {
        return postHeading;
    }

    public void setPostHeading(String postHeading) {
        this.postHeading = postHeading;
    }

    public long getPostTime() {
        return postTime;
    }

    public void setPostTime(long postTime) {
        this.postTime = postTime;
    }

    public Integer getNumberOfRepliesCount() {
        return numberOfRepliesCount;
    }

    public void setNumberOfRepliesCount(Integer numberOfRepliesCount) {
        this.numberOfRepliesCount = numberOfRepliesCount;
    }

    public String getPostQuestion() {
        return postQuestion;
    }

    public void setPostQuestion(String postQuestion) {
        this.postQuestion = postQuestion;
    }
}

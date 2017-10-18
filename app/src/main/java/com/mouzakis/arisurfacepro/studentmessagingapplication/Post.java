package com.mouzakis.arisurfacepro.studentmessagingapplication;

/**
 * Created by AriSurfacePro on 18/10/2017.
 */

public class Post {
    private String posteeName;
    private String postHeading;
    private long postTime;
    private int numberOfRepliesCount;

    public Post(String posteeName, String postHeading, long postTime, int numberOfRepliesCount) {
        this.posteeName = posteeName;
        this.postHeading = postHeading;
        this.postTime = postTime;
        this.numberOfRepliesCount = numberOfRepliesCount;
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

    public int getNumberOfRepliesCount() {
        return numberOfRepliesCount;
    }

    public void setNumberOfRepliesCount(int numberOfRepliesCount) {
        this.numberOfRepliesCount = numberOfRepliesCount;
    }
}

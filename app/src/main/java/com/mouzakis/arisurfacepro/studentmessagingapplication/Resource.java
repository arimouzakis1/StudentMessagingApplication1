package com.mouzakis.arisurfacepro.studentmessagingapplication;

import java.util.Date;

/**
 * Created by AriSurfacePro on 21/10/2017.
 */

public class Resource {
    private String name;
    private String resourceDescription;
    private String resourceLink;
    private long resourceTime;

    public Resource(String name, String resourceDescription, String resourceLink) {
        this.name = name;
        this.resourceDescription = resourceDescription;
        this.resourceLink = resourceLink;
        this.resourceTime = new Date().getTime();
    }

    public Resource() {

    }

    public String getName() {
        return name;
    }

    public String getResourceDescription() {
        return resourceDescription;
    }

    public String getResourceLink() {
        return resourceLink;
    }

    public long getResourceTime() {
        return resourceTime;
    }
}

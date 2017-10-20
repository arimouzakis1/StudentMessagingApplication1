package com.mouzakis.arisurfacepro.studentmessagingapplication;

/**
 * Created by AriSurfacePro on 14/10/2017.
 */

public class User {
    private String name;
    private String screenName;
    private int password;
    private String email;
    private String tutorialCode;

    public User(String name, String screenName, String email, int password, String tutorialCode) {
        this.name = name;
        this.screenName = screenName;
        this.password = password;
        this.email = email;
        this.tutorialCode = tutorialCode;
    }

    public User() {

    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public int getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getTutorialCode() {
        return tutorialCode;
    }
}

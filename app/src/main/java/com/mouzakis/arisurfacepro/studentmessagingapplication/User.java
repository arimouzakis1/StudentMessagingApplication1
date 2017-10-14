package com.mouzakis.arisurfacepro.studentmessagingapplication;

/**
 * Created by AriSurfacePro on 14/10/2017.
 */

public class User {
    //    private int userId;
    private String name;
    private String screenName;
    private int password;
    private String email;
//    private static int userCount = 1;

    public User(String name, String screenName, String email, int password) {
//        this.userId = userCount;
        this.name = name;
        this.screenName = screenName;
        this.password = password;
        this.email = email;
//        userCount++;
    }

//    public static int getUserCount() {
//        return userCount;
//    }

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


//    public int getUserId() {
//        return userId;
//    }
}

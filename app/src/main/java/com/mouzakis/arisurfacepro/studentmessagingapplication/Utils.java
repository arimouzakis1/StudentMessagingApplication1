package com.mouzakis.arisurfacepro.studentmessagingapplication;

/**
 * Created by AriSurfacePro on 20/10/2017.
 */
//A class for some helpful methods throughout the project
public class Utils {
    //Returns the details of the user logged in (through the use of a User object)
    public static User getLoggedInUser() {
        User loggedInUser = null;
        if (RegisterAccount.loggedInUser != null) {
            loggedInUser = RegisterAccount.loggedInUser;
            return loggedInUser;
        } else if (LoginActivity.loggedInUser != null) {
            loggedInUser = LoginActivity.loggedInUser;
            return loggedInUser;
        } else {
            return loggedInUser;
        }
    }
}

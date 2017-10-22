package com.mouzakis.arisurfacepro.studentmessagingapplication;

import android.content.Context;
import android.content.Intent;

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

    public static void signOut(Context context) {
        RegisterAccount.loggedInUser = null;
        LoginActivity.loggedInUser = null;
        LoginActivity.loggedInSuccessfully = false;

        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void manageProfile(Context context) {
//        Intent intent = new Intent(context, ManageProfile.class);
//        context.startActivity(intent);
    }
}

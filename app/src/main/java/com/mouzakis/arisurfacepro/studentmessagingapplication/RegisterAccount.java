package com.mouzakis.arisurfacepro.studentmessagingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class RegisterAccount extends AppCompatActivity {


    public Button mRegisterButton;
    public EditText mNameField;
    public EditText mScreenNameField;
    public EditText mPasswordField;
    public EditText mConfirmPasswordField;
    public EditText mEmailField;
    public EditText mTutorialCodeField;
    public static User loggedInUser = null;
    public DatabaseReference mDatabase;
    private String LOG_TAG = "RegisterAccount: ";
    private boolean isEmailUsed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);

        mRegisterButton = (Button) findViewById(R.id.email_register_button);
        mNameField = (EditText) findViewById(R.id.name);
        mScreenNameField = (EditText) findViewById(R.id.screen_name);
        mPasswordField = (EditText) findViewById(R.id.password);
        mConfirmPasswordField = (EditText) findViewById(R.id.confirm_password);
        mEmailField = (EditText) findViewById(R.id.email);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mTutorialCodeField = (EditText) findViewById(R.id.tutorial_code);


        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean newUserSuccessfullyCreated = createNewUser();

                if (newUserSuccessfullyCreated) {
                    Intent intent = new Intent(getApplicationContext(), HubActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private boolean createNewUser() {
        //Test for null fields
        if (extractString(mNameField).isEmpty()) {
            mNameField.setError(getString(R.string.required_field));
            mNameField.requestFocus();
            return false;
        }
        if (extractString(mScreenNameField).isEmpty()) {
            mScreenNameField.setError(getString(R.string.required_field));
            mScreenNameField.requestFocus();
            return false;
        }
        if (extractString(mEmailField).isEmpty()) {
            mEmailField.setError(getString(R.string.required_field));
            mEmailField.requestFocus();
            return false;
        }
        if (!isEmailValid(extractString(mEmailField))) {
            mEmailField.setError(getString(R.string.error_invalid_email));
            mEmailField.requestFocus();
            return false;
        }
        if (extractString(mPasswordField).isEmpty()) {
            mPasswordField.setError(getString(R.string.required_field));
            mPasswordField.requestFocus();
            return false;
        }
        if (!isPasswordValid(extractString(mPasswordField))) {
            mPasswordField.setError(getString(R.string.error_invalid_password));
            mPasswordField.requestFocus();
            return false;
        }
        if (extractString(mConfirmPasswordField).isEmpty()) {
            mConfirmPasswordField.setError(getString(R.string.required_field));
            mConfirmPasswordField.requestFocus();
            return false;
        }

        //Test to ensure that the password fields are the same
        if (!(mConfirmPasswordField.getText().toString().hashCode() == (mPasswordField.getText().toString().hashCode()))) {
            mPasswordField.setText("");
            mConfirmPasswordField.setText("");
            mPasswordField.setError("The two passwords entered do NOT match");
            mPasswordField.requestFocus();
            return false;
        }

        //Test if tutorial field is Null
        if (extractString(mTutorialCodeField).isEmpty()) {
            mTutorialCodeField.setError(getString(R.string.required_field));
            mTutorialCodeField.requestFocus();
            return false;
        }

        //Test if email is already being used
        boolean isEmailInUse = isEmailInUse(extractString(mEmailField).toLowerCase());

        if (!isEmailInUse) {
            mEmailField.setError("Email already in use - please choose a unique email");
            mEmailField.requestFocus();
            return false;
        }


        boolean userSuccessfullyAdded = addUserToDatabase(extractString(mNameField), extractString(mScreenNameField),
                extractString(mEmailField).toLowerCase(), extractString(mPasswordField).hashCode(),
                extractString(mTutorialCodeField).toUpperCase());

        //If user is NOT successfully added then remove the loggedInUser screen name from "cache"
        if (!userSuccessfullyAdded) {
            loggedInUser = null;
        }

        return userSuccessfullyAdded;
    }

    //Check if email is in Use
    private boolean isEmailInUse(final String mEmail) {
        isEmailUsed = false;
        mDatabase.child("users").child("ID").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(LOG_TAG, "Current mEmail field value " + mEmail);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String email = snapshot.child("email").getValue().toString();


                    if (mEmail.trim().toLowerCase().matches(email)) {
                        isEmailUsed = true;
                        Log.d(LOG_TAG, "Email Address Found! " + email);
                        break;
                    } else {
                        Log.d(LOG_TAG, "Email Address NOT found. " + email);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return isEmailUsed;
    }

    //TODO: if you want this to send an email please add functionality
    private boolean addUserToDatabase(String name, String screenName, String email, int password, String tutorialCode) {
        loggedInUser = new User(name, screenName, email, password, tutorialCode);
        DatabaseReference userReference = mDatabase.child("users").child("ID");
        Map<String, Object> users = new HashMap<String, Object>();
        users.put(email.replace(".", ""), loggedInUser);

        userReference.updateChildren(users);
        return true;
    }

    public String extractString(EditText field) {
        return field.getText().toString().trim();
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.toLowerCase().contains("@ad.unsw.edu.au");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 8;
    }

}

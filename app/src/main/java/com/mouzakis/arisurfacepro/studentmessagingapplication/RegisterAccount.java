package com.mouzakis.arisurfacepro.studentmessagingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterAccount extends AppCompatActivity {


    public Button mRegisterButton;
    public EditText mNameField;
    public EditText mScreenNameField;
    public EditText mPasswordField;
    public EditText mConfirmPasswordField;
    public EditText mEmailField;
    public DatabaseReference mDatabase;


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


        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean registeredUser = createNewUser();

                if (registeredUser) {
                    Intent intent = new Intent(getApplicationContext(), MessagingActivity.class);
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


        addUserToDatabase(extractString(mNameField), extractString(mScreenNameField), extractString(mEmailField), extractString(mPasswordField).hashCode());

        return true;
    }

    //TODO: This method continuously overwrites ID 1!!! A Fix is needed!
    private void addUserToDatabase(String name, String screenName, String email, int password) {
        User user = new User(name, screenName, email, password);
        DatabaseReference userReference = mDatabase.child("users").child("ID");
        Map<String, Object> users = new HashMap<String, Object>();

        users.put(email.replace(".", ""), (Object) user);

        userReference.updateChildren(users);
    }

    public String extractString(EditText field) {
        return field.getText().toString();
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@ad.unsw.edu.au");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 8;
    }

}

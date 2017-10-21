package com.mouzakis.arisurfacepro.studentmessagingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class PostingActivity extends AppCompatActivity {
    private EditText postSubject;
    private EditText postQuestion;
    private FloatingActionButton fab;
    private User mUser;
    public static final String USER = "user";
    public static final String SUBJECT = "subject";
    public static final String QUESTION = "question";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);

        postSubject = (EditText) findViewById(R.id.resource_description);
        postQuestion = (EditText) findViewById(R.id.hyperlink_text);
        fab = (FloatingActionButton) findViewById(R.id.post_resource_button);
        fab.setImageDrawable(getResources().getDrawable(R.drawable.post_icon));
        mUser = Utils.getLoggedInUser();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean allFieldsCompleted = checkRequiredFields();

                if (allFieldsCompleted) {
                    Intent intent = new Intent();

                    //Add the data to the intent to be sent back to main activity
                    intent.putExtra(USER, mUser.getName());
                    intent.putExtra(SUBJECT, extractString(postSubject));
                    intent.putExtra(QUESTION, extractString(postQuestion));

                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    //Checks if the posting fields are null. If they are prompts will tell the user to finish the fields
    private boolean checkRequiredFields() {
        if (extractString(postSubject).trim().isEmpty()) {
            postSubject.setError(getString(R.string.required_field));
            postSubject.requestFocus();
            return false;
        }

        if (extractString(postQuestion).trim().isEmpty()) {
            postQuestion.setError(getString(R.string.required_field));
            postQuestion.requestFocus();
            return false;
        }

        return true;
    }

    public String extractString(EditText field) {
        return field.getText().toString().trim();
    }
}

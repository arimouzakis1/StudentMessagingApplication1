package com.mouzakis.arisurfacepro.studentmessagingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class ResourcePostingActivity extends AppCompatActivity {
    private EditText resourceDescription;
    private EditText resourceHyperlink;
    private FloatingActionButton fab;
    private User mUser;
    public static final String USER = "user";
    public static final String DESCRIPTION = "description";
    public static final String HYPERLINK = "hyperlink";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_posting);

        resourceDescription = (EditText) findViewById(R.id.resource_description);
        resourceHyperlink = (EditText) findViewById(R.id.hyperlink_text);
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
                    intent.putExtra(USER, Utils.getLoggedInUser().getName());
                    intent.putExtra(DESCRIPTION, extractString(resourceDescription));
                    intent.putExtra(HYPERLINK, extractString(resourceHyperlink));

                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    //Checks if the posting fields are null. If they are prompts will tell the user to finish the fields
    private boolean checkRequiredFields() {
        if (extractString(resourceDescription).trim().isEmpty()) {
            resourceDescription.setError(getString(R.string.required_field));
            resourceDescription.requestFocus();
            return false;
        }

        if (extractString(resourceHyperlink).trim().isEmpty()) {
            resourceHyperlink.setError(getString(R.string.required_field));
            resourceHyperlink.requestFocus();
            return false;
        }

        boolean containsInternetPrefix = false;
        if (extractString(resourceHyperlink).trim().toLowerCase().startsWith("http://") || extractString(resourceHyperlink).trim().toLowerCase().startsWith("https://")) {
            containsInternetPrefix = true;
        }
        if (!containsInternetPrefix) {
            resourceHyperlink.setError(getString(R.string.resource_invalid_error));
            resourceHyperlink.requestFocus();
            return false;
        }


        return true;
    }

    public String extractString(EditText field) {
        return field.getText().toString().trim();
    }
}

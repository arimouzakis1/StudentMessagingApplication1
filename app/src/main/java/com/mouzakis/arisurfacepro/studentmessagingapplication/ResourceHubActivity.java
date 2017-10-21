package com.mouzakis.arisurfacepro.studentmessagingapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class ResourceHubActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    public static final int CREATE_NEW_RESOURCE = 1;
    private static final String LOG_TAG = "ResourceHubActivity: ";
    private DatabaseReference mDatabase;
    private FirebaseListAdapter<Resource> mFirebaseResourceAdapter;
    private TextView resourceUser;
    private TextView resourceDescription;
    private TextView resourceDate;
    private TextView resourceTime;
    private TextView resourceHyperlink;
    private ListView resourceView;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_hub);

        fab = (FloatingActionButton) findViewById(R.id.create_new_resource);
        mUser = Utils.getLoggedInUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("resource").child(mUser.getTutorialCode());

        resourceView = (ListView) findViewById(R.id.list_of_resources);

        displayPosts();

        //On Floating Action Button Click allow the user to post a new Resource by taking them to the ResourcePostingActivity
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResourcePostingActivity.class);
                startActivityForResult(intent, CREATE_NEW_RESOURCE);
            }
        });

        //On click of the resource open the links webpage
        resourceView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Uri uri = Uri.parse(mFirebaseResourceAdapter.getItem(i).getResourceLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }


    //Checks if the user created a post in the "PostingActivity"
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_NEW_RESOURCE) {
            if (resultCode == RESULT_OK) {
                String name = data.getStringExtra(ResourcePostingActivity.USER);
                String description = data.getStringExtra(ResourcePostingActivity.DESCRIPTION);
                String hyperlink = data.getStringExtra(ResourcePostingActivity.HYPERLINK);

                Resource resource = new Resource(name, description, hyperlink);
                postResource(resource);
            }
        }
    }

    private void displayPosts() {
        mFirebaseResourceAdapter = new FirebaseListAdapter<Resource>(this, Resource.class, R.layout.resource, mDatabase) {
            @Override
            protected void populateView(View v, Resource model, int position) {
                resourceUser = v.findViewById(R.id.resource_postee_name);
                resourceDescription = v.findViewById(R.id.resource_description);
                resourceDate = v.findViewById(R.id.resource_post_date);
                resourceTime = v.findViewById(R.id.resource_post_time);
                resourceHyperlink = v.findViewById(R.id.resource_hyperlink);

                //Fill the fields with values and display on the screen
                resourceUser.setText(model.getName());
                resourceDescription.setText(model.getResourceDescription());
                resourceHyperlink.setText(model.getResourceLink());

                //Format and get correct timezone for Australia
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ssa");
                dateFormat.setTimeZone(TimeZone.getTimeZone("Australia/NSW"));
                timeFormat.setTimeZone(TimeZone.getTimeZone("Australia/NSW"));

                resourceDate.setText(dateFormat.format(model.getResourceTime()));
                resourceTime.setText(timeFormat.format(model.getResourceTime()));
            }
        };
        resourceView.setAdapter(mFirebaseResourceAdapter);
    }

    private void postResource(Resource resource) {
        mDatabase.push().setValue(resource);
    }

}

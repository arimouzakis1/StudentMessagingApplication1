package com.mouzakis.arisurfacepro.studentmessagingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class QuestionBoardActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    public static final int CREATE_NEW_POST = 1;
    private DatabaseReference mDatabase;
    private FirebaseListAdapter<Post> mFirebasePostAdapter;
    private TextView postUser;
    private TextView postNumberOfReplies;
    private TextView postSubject;
    private TextView postDate;
    private TextView postTime;
    private ListView postView;
    private Button deletePostButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_board);

        fab = (FloatingActionButton) findViewById(R.id.create_new_posting);
        if (RegisterAccount.loggedInUser != null) {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("post").child(RegisterAccount.loggedInUser.getTutorialCode());
        }
        //TODO: add login reference from a user NOT creating a new account
        else {
//            mDatabase = FirebaseDatabase.getInstance().getReference().child("post").child();
        }
        postView = (ListView) findViewById(R.id.list_of_posts);

        displayPosts();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PostingActivity.class);
                startActivityForResult(intent, CREATE_NEW_POST);
            }
        });
    }


    //Checks if the user created a post in the "PostingActivity"
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_NEW_POST) {
            if (resultCode == RESULT_OK) {
                String user = data.getStringExtra(PostingActivity.USER);
                String subject = data.getStringExtra(PostingActivity.SUBJECT);
                String question = data.getStringExtra(PostingActivity.QUESTION);

                Post post = new Post(user, subject, question);
                postQuestion(post);
            }
        }
    }

    private void displayPosts() {
        mFirebasePostAdapter = new FirebaseListAdapter<Post>(this, Post.class, R.layout.post, mDatabase) {
            @Override
            protected void populateView(View v, Post model, int position) {
                postUser = v.findViewById(R.id.postee_name);
                postNumberOfReplies = v.findViewById(R.id.number_of_replies);
                postSubject = v.findViewById(R.id.post_heading);
                postDate = v.findViewById(R.id.post_date);
                postTime = v.findViewById(R.id.post_time);

                //Fill the fields with values and display on the screen
                postUser.setText(model.getPosteeName());
                postNumberOfReplies.setText(model.getNumberOfRepliesCount().toString());
                postSubject.setText(model.getPostHeading());

                //Format and get correct timezone for Australia
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ssa");
                dateFormat.setTimeZone(TimeZone.getTimeZone("Australia/NSW"));
                timeFormat.setTimeZone(TimeZone.getTimeZone("Australia/NSW"));

                postDate.setText(dateFormat.format(model.getPostTime()));
                postTime.setText(timeFormat.format(model.getPostTime()));

                //TODO: add in a "OR" statement in the below if statement to check if its the user that logged in
                if (RegisterAccount.loggedInUser.getName() == extractString(postUser)) {
                    deletePostButton.setVisibility(View.VISIBLE);
                }
            }
        };
        postView.setAdapter(mFirebasePostAdapter);
    }


    private void postQuestion(Post post) {
        mDatabase.push().setValue(post);
    }

    public String extractString(TextView field) {
        return field.getText().toString().trim();
    }
}

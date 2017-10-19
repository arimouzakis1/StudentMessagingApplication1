package com.mouzakis.arisurfacepro.studentmessagingapplication;

import android.content.Intent;
import android.os.Bundle;
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

public class SeeRepliesToPostActivity extends AppCompatActivity {
    private Button replyButton;
    public static final int CREATE_POST_REPLY = 2;
    private TextView postUser;
    private TextView postSubject;
    private TextView postDate;
    private TextView postTime;
    private Button deletePostButton;
    private TextView questionText;
    private DatabaseReference mDatabase;
    private FirebaseListAdapter<Reply> mFirebaseReplyAdapter;
    private ListView replyView;
    private TextView replyingUser;
    private TextView replyTime;
    private TextView replyDate;
    private TextView replyText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_replies_to_post);

        replyButton = (Button) findViewById(R.id.reply_to_post_button);
        deletePostButton = (Button) findViewById(R.id.delete_post_button);
        replyView = (ListView) findViewById(R.id.post_replies_view);

        //TODO: create this for logged in user
        if (RegisterAccount.loggedInUser != null) {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("reply").
                    child(RegisterAccount.loggedInUser.getTutorialCode());
        }

        //TODO: add in an if statement below to check if its the user that logged in (if not make the visibility to GONE)
        if (!RegisterAccount.loggedInUser.getName().toLowerCase().matches(QuestionBoardActivity.post.getPosteeName().toLowerCase())) {
            deletePostButton.setVisibility(View.GONE);
        }

        displayQuestion();
        displayReplies();

        replyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreatePostReply.class);
                startActivityForResult(intent, CREATE_POST_REPLY);
            }
        });
    }

    //Gets the result from the intent and uses this data to create a new reply to a post
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_POST_REPLY) {
            if (resultCode == RESULT_OK) {
                String replyText = data.getStringExtra(CreatePostReply.REPLY);
                String replyingUser = data.getStringExtra(CreatePostReply.REPLYING_USER);

                Reply reply = new Reply(replyingUser, replyText);

                mDatabase.push().setValue(reply);
            }
        }
    }

    private void displayQuestion() {
        postUser = (TextView) findViewById(R.id.postee_name);
        postSubject = (TextView) findViewById(R.id.post_heading);
        postDate = (TextView) findViewById(R.id.post_date);
        postTime = (TextView) findViewById(R.id.post_time);
        questionText = (TextView) findViewById(R.id.question_text);

        //Show text in the application by filling the views
        postUser.setText(QuestionBoardActivity.post.getPosteeName());
        postSubject.setText(QuestionBoardActivity.post.getPostHeading());

        //Format time/date and display on screen
        //Format and get correct timezone for Australia
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ssa");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Australia/NSW"));
        timeFormat.setTimeZone(TimeZone.getTimeZone("Australia/NSW"));

        postDate.setText(dateFormat.format(QuestionBoardActivity.post.getPostTime()));
        postTime.setText(timeFormat.format(QuestionBoardActivity.post.getPostTime()));

        questionText.setText(QuestionBoardActivity.post.getPostQuestion());
    }

    private void displayReplies() {
        mFirebaseReplyAdapter = new FirebaseListAdapter<Reply>(this, Reply.class, R.layout.reply, mDatabase) {
            @Override
            protected void populateView(View v, Reply model, int position) {
                replyingUser = v.findViewById(R.id.replying_user_name);
                replyDate = v.findViewById(R.id.reply_date);
                replyTime = v.findViewById(R.id.reply_time);
                replyText = v.findViewById(R.id.reply_text);

                replyingUser.setText(model.getReplyingUser());

                //Format time/date and display on screen
                //Format and get correct timezone for Australia
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ssa");
                dateFormat.setTimeZone(TimeZone.getTimeZone("Australia/NSW"));
                timeFormat.setTimeZone(TimeZone.getTimeZone("Australia/NSW"));

                replyDate.setText(dateFormat.format(model.getTimeOfReply()));
                replyTime.setText(timeFormat.format(model.getTimeOfReply()));

                replyText.setText(model.getReplyText());
            }
        };
        replyView.setAdapter(mFirebaseReplyAdapter);

    }

    public String extractString(TextView field) {
        return field.getText().toString().trim();
    }
}

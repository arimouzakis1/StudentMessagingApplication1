package com.mouzakis.arisurfacepro.studentmessagingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
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
    public static final int REPLIES = 20;
    private static final String LOG_TAG = "QuestionBoardActivity: ";
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseReferenceForRepliesCount;
    private FirebaseListAdapter<Post> mFirebasePostAdapter;
    private TextView postUser;
    private TextView postNumberOfReplies;
    private TextView postSubject;
    private TextView postDate;
    private TextView postTime;
    private ListView postView;
    private Button deletePostButton;
    public static Post post;
    public static long postId;
    private User mUser;
//    private long replyCount;
private ImageButton chatRoomButton;
    private ImageButton resourceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_board);

        fab = (FloatingActionButton) findViewById(R.id.create_new_posting);
        fab.setImageDrawable(getResources().getDrawable(R.drawable.plus_icon));
        mUser = Utils.getLoggedInUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("post").child(mUser.getTutorialCode());
        postView = (ListView) findViewById(R.id.list_of_posts);
        chatRoomButton = (ImageButton) findViewById(R.id.question_chat_room_image_button);
        resourceButton = (ImageButton) findViewById(R.id.question_student_resources_image_button);

        displayPosts();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PostingActivity.class);
                startActivityForResult(intent, CREATE_NEW_POST);
            }
        });

        postView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                post = (Post) postView.getAdapter().getItem(i);
                postId = postView.getAdapter().getItemId(i);
                Intent intent = new Intent(getApplicationContext(), SeeRepliesToPostActivity.class);
                startActivityForResult(intent, REPLIES);
            }
        });

        chatRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MessagingActivity.class);
                startActivity(intent);
            }
        });

        resourceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResourceHubActivity.class);
                startActivity(intent);
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
//                postNumberOfReplies = v.findViewById(R.id.number_of_replies);
                postSubject = v.findViewById(R.id.resource_description);
                postDate = v.findViewById(R.id.resource_post_date);
                postTime = v.findViewById(R.id.resource_post_time);
                deletePostButton = v.findViewById(R.id.delete_post_button);

                //Fill the fields with values and display on the screen
                postUser.setText(model.getPosteeName());
                postSubject.setText(model.getPostHeading());

                //Format and get correct timezone for Australia
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ssa");
                dateFormat.setTimeZone(TimeZone.getTimeZone("Australia/NSW"));
                timeFormat.setTimeZone(TimeZone.getTimeZone("Australia/NSW"));

                postDate.setText(dateFormat.format(model.getPostTime()));
                postTime.setText(timeFormat.format(model.getPostTime()));

                //Calculate the number of replies
                //calculateNumberOfReplies(model);
//                postNumberOfReplies.setText(model.getNumberOfRepliesCount().toString());
//                Log.d(LOG_TAG, "number of replies " + model.getNumberOfRepliesCount());

                /*if (!mUser.getName().toLowerCase().matches(extractString(postUser).toLowerCase())) {
                    deletePostButton.setVisibility(View.GONE);
                }*/
            }
        };
        postView.setAdapter(mFirebasePostAdapter);
    }

    /*private void calculateNumberOfReplies(final Post post) {
        //Format and get correct timezone for Australia
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ssa");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Australia/NSW"));
        timeFormat.setTimeZone(TimeZone.getTimeZone("Australia/NSW"));


        mDatabaseReferenceForRepliesCount = FirebaseDatabase.getInstance().getReference().child("reply")
                .child(mUser.getTutorialCode()).child(post.getPostHeading() + " " + dateFormat.format(post.getPostTime()) + " "
                        + timeFormat.format(post.getPostTime()));
        Log.d(LOG_TAG, post.getPostHeading() + " " + dateFormat.format(post.getPostTime()) + " "
                + timeFormat.format(post.getPostTime()));

        Query query = mDatabaseReferenceForRepliesCount.orderByKey();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    replyCount++;
                    Log.d(LOG_TAG, "dataSnapshot has been taken!" + dataSnapshot);
                    Log.d(LOG_TAG, "replyCount is " + replyCount);
                }
                post.setNumberOfRepliesCount((int) replyCount);
                Log.d("Reply Count: ", Long.toString(replyCount));
                replyCount = 0;
                Log.d(LOG_TAG, "replyCount reset");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/


    private void postQuestion(Post post) {
        mDatabase.push().setValue(post);
    }

    public String extractString(TextView field) {
        return field.getText().toString().trim();
    }
}

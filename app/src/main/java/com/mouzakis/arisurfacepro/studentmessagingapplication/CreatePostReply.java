package com.mouzakis.arisurfacepro.studentmessagingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CreatePostReply extends AppCompatActivity {
    private TextView question;
    private EditText replyField;
    private FloatingActionButton fab;
    private User mUser;
    public static final String REPLY = "reply";
    public static final String REPLYING_USER = "replying_user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post_reply);
        mUser = Utils.getLoggedInUser();

        question = (TextView) findViewById(R.id.question_text);
        replyField = (EditText) findViewById(R.id.reply_field);
        fab = (FloatingActionButton) findViewById(R.id.post_reply_button);
        fab.setImageDrawable(getResources().getDrawable(R.drawable.reply_icon));

        question.setText(QuestionBoardActivity.post.getPostQuestion());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                if (!extractString(replyField).isEmpty()) {
                    intent.putExtra(REPLY, extractString(replyField));
                }
                intent.putExtra(REPLYING_USER, mUser.getName());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public String extractString(TextView field) {
        return field.getText().toString().trim();
    }
}

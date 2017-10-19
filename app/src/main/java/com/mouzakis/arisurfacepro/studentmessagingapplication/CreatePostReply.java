package com.mouzakis.arisurfacepro.studentmessagingapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

public class CreatePostReply extends AppCompatActivity {
    private TextView question;
    private EditText replyField;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post_reply);

        question = (TextView) findViewById(R.id.question_text);
        replyField = (EditText) findViewById(R.id.reply_field);
        fab = (FloatingActionButton) findViewById(R.id.post_reply_button);

        question.setText(QuestionBoardActivity.post.getPostQuestion());

    }
}

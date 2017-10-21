package com.mouzakis.arisurfacepro.studentmessagingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);

        Button questionPostButton = (Button) findViewById(R.id.post_resource_button);
        Button chatRoomButton = (Button) findViewById(R.id.chat_room_button);
        Button studentResourceButton = (Button) findViewById(R.id.student_resources_button);

        questionPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), QuestionBoardActivity.class);
                startActivity(intent);
            }
        });

        chatRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MessagingActivity.class);
                startActivity(intent);
            }
        });

        studentResourceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResourceHubActivity.class);
                startActivity(intent);
            }
        });
    }
}

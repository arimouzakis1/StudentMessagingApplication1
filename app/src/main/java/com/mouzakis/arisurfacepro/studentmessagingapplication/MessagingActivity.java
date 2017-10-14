package com.mouzakis.arisurfacepro.studentmessagingapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class MessagingActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private EditText messageField;
    private FirebaseListAdapter<Message> mFirebaseMessageAdapter;
    private ListView messageView;
    private TextView user;
    private TextView messageText;
    private TextView messageTime;
    private TextView messageDate;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);


        fab = (FloatingActionButton) findViewById(R.id.floating_action_button);
        messageField = (EditText) findViewById(R.id.message_to_be_sent);
        messageView = (ListView) findViewById(R.id.list_of_messages);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("message");


        displayChatMessages();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Make a way to find the user that in currently logged in
                Message message = new Message("User1", messageField.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("message").push().setValue(message);

                messageField.setText("");
//                messageView.setSelection(messageView.getAdapter().getCount()-3);
            }
        });


    }

    public void displayChatMessages() {
        mFirebaseMessageAdapter = new FirebaseListAdapter<Message>(this, Message.class, R.layout.messages, mDatabase) {
            @Override
            protected void populateView(View v, Message model, int position) {
                user = v.findViewById(R.id.user);
                messageText = v.findViewById(R.id.message_text);
                messageDate = v.findViewById(R.id.message_date);
                messageTime = v.findViewById(R.id.message_time);

                messageText.setText(model.getMessageText());
                user.setText(model.getUser());

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ssa");
                dateFormat.setTimeZone(TimeZone.getTimeZone("Australia/NSW"));
                timeFormat.setTimeZone(TimeZone.getTimeZone("Australia/NSW"));

                messageDate.setText(dateFormat.format(model.getMessageTime()));
                messageTime.setText(timeFormat.format(model.getMessageTime()));
            }
        };
        messageView.setAdapter(mFirebaseMessageAdapter);

    }
}

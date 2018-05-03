package cga3798.tcss450.uw.edu.challengeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        if(savedInstanceState == null) {
            if (findViewById(R.id.chatContainer) != null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.chatContainer, new ChatFragment())
                        .commit();
            }
        }
    }
}

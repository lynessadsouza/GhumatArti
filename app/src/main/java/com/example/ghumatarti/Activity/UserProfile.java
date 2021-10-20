package com.example.ghumatarti.Activity;

import static com.example.ghumatarti.Activity.LoginActivity.loggedemail;
import static com.example.ghumatarti.Activity.LoginActivity.loggedname;
import static com.example.ghumatarti.Activity.LoginActivity.loggedphone;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ghumatarti.R;

public class UserProfile extends AppCompatActivity {
    TextView email, name, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        email = (TextView) findViewById(R.id.profile_email);
        name = (TextView) findViewById(R.id.profile_name);
        phone = (TextView) findViewById(R.id.profile_phone);

        email.setText(loggedemail);
        name.setText(loggedname);
        phone.setText(loggedphone);

    }
}
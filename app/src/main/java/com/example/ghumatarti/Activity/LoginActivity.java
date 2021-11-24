package com.example.ghumatarti.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ghumatarti.ModelClass.UserModel;
import com.example.ghumatarti.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    public static String loggedemail;
    static String loggedname;
    static String loggedphone;
    TextInputEditText emaill, passwordl;
    MaterialButton login;
    DatabaseReference getusers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUi();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getusers = FirebaseDatabase.getInstance().getReference("Users");
                //     Query query = getusers.child("Users").orderByChild("email").equalTo(e);
                Log.d("before query", "bfore query");
                Log.d("before query", "" + emaill.getText().toString().trim());

                //   Query q=

                Query query = getusers.orderByChild("email").equalTo(emaill.getText().toString().trim());

                Log.d("query", "" + query);
//query starts here
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("datasnapshoot", "" + dataSnapshot);

                        if (dataSnapshot.exists()) {
                            // dataSnapshot is the "issue" node with all children with id 0

                            for (DataSnapshot user : dataSnapshot.getChildren()) {
                                // do something with the individual "issues"

                                Log.d("user", "" + user);


                                UserModel userModel = user.getValue(UserModel.class);
                                Log.d("user model password", "" + userModel.getPassword());
                                Log.d("email.gdt text", "" + emaill.getText().toString().trim());

                                if (userModel.getPassword().equals(passwordl.getText().toString().trim())) {
                                    Log.d("user model getpass", "" + userModel.getPassword());
                                    Log.d("user model password", "" + userModel.getEmail());
                                    Log.d("user model password", "" + userModel.getName());

                                    loggedemail = userModel.getEmail();
                                    loggedname = userModel.getName();
                                    loggedphone = userModel.getPhone();

                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Password is wrong", Toast.LENGTH_LONG).show();
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
//ends here database reference
            }
        });


    }

    //Init UI
    private void initUi() {
        emaill = (TextInputEditText) findViewById(R.id.email_login);
        passwordl = (TextInputEditText) findViewById(R.id.password_login);
        login = (MaterialButton) findViewById(R.id.loginbtn);
    }
    //Init UI ends here
}
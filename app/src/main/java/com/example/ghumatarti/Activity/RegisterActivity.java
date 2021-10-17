package com.example.ghumatarti.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ghumatarti.ModelClass.UserModel;
import com.example.ghumatarti.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    MaterialButton register;
    TextInputEditText name1, phone1, email1, password1, cnf_password;

    MaterialTextView signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        initUI();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validateFields();


                Log.d("validated", "Validated");


            }

            private void validateFields() {

                if (name1.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter name", Toast.LENGTH_LONG).show();
                    //   name.requestFocus();
                    name1.requestFocus();
                } else if( (phone1.getText().toString().isEmpty()) ||( phone1.getText().toString().length()<10)||( phone1.getText().toString().length()>10) ){
                        Toast.makeText(getApplicationContext(), "Please Enter Phone Number currectly", Toast.LENGTH_LONG).show();
                    //   name.requestFocus();
                    phone1.requestFocus();
                } else if (email1.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter email", Toast.LENGTH_LONG).show();
                    //   name.requestFocus();
                    email1.requestFocus();

                } else if (password1.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter password", Toast.LENGTH_LONG).show();
                    //   name.requestFocus();
                    password1.requestFocus();
                } else if (cnf_password.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter name", Toast.LENGTH_LONG).show();
                    //   name.requestFocus();
                    cnf_password.requestFocus();
                } else if (password1.getText().toString().equals(cnf_password.getText().toString()) != true) {
                    Toast.makeText(getApplicationContext(), "PASSWORD AND CONFIRM PASSWORD NOT MATCHING", Toast.LENGTH_LONG).show();
                    cnf_password.requestFocus();
                } else {
                    Toast.makeText(getApplicationContext(), "Validated", Toast.LENGTH_LONG).show();

                    //Save Users To database here
                    
                    StoreUsers();



                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(i);

                }

            }
        });
    }

    private void StoreUsers() {
        String name;
        String phone;
        String email;
        String password;

        name=name1.getText().toString().trim();
         phone=phone1.getText().toString().trim();
         email=email1.getText().toString().trim();
         password=password1.getText().toString().trim();



        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("Users");

       // encodeEmail(email);
        //encode--> cant add . to the database root node
String id= email.replace(".",",");


//to decode
  /*      String id= email.replace(",",".");*/



        UserModel addNewUser = new UserModel(name, phone , email, password);
        reference.child(id).setValue(addNewUser);

        //We will also create a Session here in next videos to keep the user logged In

        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();

    }

 /*   private String encodeEmail(String email) {
        return email.replace(".", ",");


    }
*/
    private void initUI() {

        register = (MaterialButton) findViewById(R.id.register);

        name1 = (TextInputEditText) findViewById(R.id.name);
        phone1 = (TextInputEditText) findViewById(R.id.phone);
        email1 = (TextInputEditText) findViewById(R.id.email);
        password1 = (TextInputEditText) findViewById(R.id.password);
        cnf_password = (TextInputEditText) findViewById(R.id.cnf_password);
        signin = (MaterialTextView) findViewById(R.id.redirect_to_login);

    }
}

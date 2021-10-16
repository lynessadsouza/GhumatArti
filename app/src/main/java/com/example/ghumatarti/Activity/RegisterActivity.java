package com.example.ghumatarti.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ghumatarti.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    MaterialButton register;
    TextInputEditText name, phone, email, password, cnf_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        initUI();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               validateFields();


                Log.d("validated","Validated");


            }

            private void validateFields() {

                if (name.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter name", Toast.LENGTH_LONG).show();
                    //   name.requestFocus();
                    name.setFocusable(true);
                } else if (phone.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter name", Toast.LENGTH_LONG).show();
                    //   name.requestFocus();
                    phone.setFocusable(true);
                } else if (email.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter name", Toast.LENGTH_LONG).show();
                    //   name.requestFocus();
                    email.setFocusable(true);
                } else if (password.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter name", Toast.LENGTH_LONG).show();
                    //   name.requestFocus();
                    password.setFocusable(true);
                } else if (cnf_password.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter name", Toast.LENGTH_LONG).show();
                    //   name.requestFocus();
                    cnf_password.setFocusable(true);
                } else if (password.getText().toString().equals(cnf_password.getText().toString()) != true) {
                    Toast.makeText(getApplicationContext(), "PASSWORD AND CONFIRM PASSWORD NOT MATCHING", Toast.LENGTH_LONG).show();
                    cnf_password.setFocusable(true);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Validated", Toast.LENGTH_LONG).show();
                    Intent i= new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(i);

                }

            }
        });
    }

    private void initUI() {

        register = (MaterialButton) findViewById(R.id.register);

        name = (TextInputEditText) findViewById(R.id.name);
        phone = (TextInputEditText) findViewById(R.id.phone);
        email = (TextInputEditText) findViewById(R.id.email);
        password = (TextInputEditText) findViewById(R.id.password);
        cnf_password = (TextInputEditText) findViewById(R.id.cnf_password);

    }
}

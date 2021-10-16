package com.example.ghumatarti.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.ghumatarti.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    MaterialButton register;
    TextInputEditText name, phone, email,password, cnf_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

      
        initUI();
        

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                validateFields();
                
                
            }

            private void validateFields() {
                if(name.getText().toString().isEmpty()==true || )


            }
        });
    }

    private void initUI() {

        register=(MaterialButton) findViewById(R.id.register);

        name=(TextInputEditText) findViewById(R.id.name);
        phone=(TextInputEditText) findViewById(R.id.phone);
        email=(TextInputEditText) findViewById(R.id.email);
        password=(TextInputEditText) findViewById(R.id.password);
        cnf_password=(TextInputEditText) findViewById(R.id.cnf_password);

    }
}
package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {


    TextInputEditText log_username,log_password;
    Button b1;
    FirebaseAuth mAuth;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        log_username=findViewById(R.id.loginusername);
        log_password=findViewById(R.id.loginpassword);
        b1=findViewById(R.id.login_btn);


        b1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
               mAuth = FirebaseAuth.getInstance();
                String email = log_username.getText().toString();
                String password = log_password.getText().toString();



                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful())
                            {
                                // Handle successful login
                                Toast.makeText(login.this,"login sucessfully",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(login.this,MainActivity.class);
                                startActivity(i);
                            } else {
                                // Handle errors
                                Toast.makeText(login.this,"login Failed",Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });


    }

}
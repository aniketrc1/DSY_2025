package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity {


    TextInputEditText reg_username,reg_password;
    FirebaseAuth f1;
    Button b1;
    TextView gotologin;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        reg_password=findViewById(R.id.regpassword);
        reg_username=findViewById(R.id.regusername);
        b1=findViewById(R.id.reg_btn);
        f1=FirebaseAuth.getInstance();
        gotologin=findViewById(R.id.login);

        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(register.this, login.class);
                startActivity(i);
                finish();

            }
        });


        b1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String email=reg_username.getText().toString();
                String pass=reg_password.getText().toString();



                f1.createUserWithEmailAndPassword(email,
                                pass)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful())
                            {
                                // Handle successful registration
                                Toast.makeText(register.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                Intent i= new Intent(register.this,login.class);
                                startActivity(i);
                                finish();

                            } else {
                                // Handle errors
                                Toast.makeText(register.this, "Failed to Register", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });


    }

}
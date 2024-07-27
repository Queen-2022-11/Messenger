package com.example.chatting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    FirebaseAuth auth;
    Button login;
    EditText email,password;
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    TextView signup;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        login= findViewById(R.id.signup);
        email= findViewById(R.id.email);
        password= findViewById(R.id.password);
        signup=findViewById(R.id.login2);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Registration.class);
                startActivity(intent);
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email=email.getText().toString();
                String Password=password.getText().toString();
                if(TextUtils.isEmpty(Email)){
                    Toast.makeText(Login.this,"Please Enter the email",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(Password)){
                    Toast.makeText(Login.this,"Please Enter the password",Toast.LENGTH_SHORT).show();
                }else if(!Email.matches(emailPattern)){
                    Toast.makeText(Login.this,"Please Enter the correct email address",Toast.LENGTH_SHORT).show();
                }else if(Password.length()<6){
                    Toast.makeText(Login.this,"Password should be more then 6 characters",Toast.LENGTH_SHORT).show();
                }else{
                    auth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                try {
                                    Intent intent = new Intent(Login.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }catch (Exception e){
                                    Toast.makeText(Login.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(Login.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }


                });
            }}
        });
    }
}
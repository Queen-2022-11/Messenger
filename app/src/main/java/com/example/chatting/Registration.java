package com.example.chatting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;
public class Registration extends AppCompatActivity {
    FirebaseAuth auth;
    Button signup;
    EditText email1,password1,password2,username;
    CircleImageView profile;
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    TextView login2;
    Uri imageuri;
    String imageUri;
    FirebaseStorage storage;
    FirebaseDatabase database;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        signup =findViewById(R.id.signup);
        email1 =findViewById(R.id.email1);
        password1 =findViewById(R.id.password1);
        password2 =findViewById(R.id.reenterpassword);
        username =findViewById(R.id.username);
        login2=findViewById(R.id.login2);
        profile=findViewById(R.id.profile);
        auth=FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();
        login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this,Login.class);
                startActivity(intent);
                finish();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select a profile picture"),10);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email=email1.getText().toString();
                String Password=password1.getText().toString();
                String RePassword=password1.getText().toString();
                String Username=username.getText().toString();
                String status="Hey I am using this application";
                if(TextUtils.isEmpty(Email) || TextUtils.isEmpty(Password) || TextUtils.isEmpty(RePassword) || TextUtils.isEmpty(Username)){
                    Toast.makeText(Registration.this,"Please Enter all the details",Toast.LENGTH_SHORT).show();
                }else if(!Email.matches(emailPattern)){
                    Toast.makeText(Registration.this,"Please Enter the correct email address",Toast.LENGTH_SHORT).show();
                }else if(Password.length()<6){
                    Toast.makeText(Registration.this,"Password should be more then 6 characters",Toast.LENGTH_SHORT).show();
                } else if (!RePassword.equals(Password)) {
                    Toast.makeText(Registration.this,"Password Doesn't match",Toast.LENGTH_SHORT).show();
                }else{
                    auth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                             if(task.isSuccessful()){
                                 String id= task.getResult().getUser().getUid();
                                 DatabaseReference databaseReference= database.getReference().child("user").child(id);
                                 StorageReference storageReference= storage.getReference().child("Upload").child(id);
                                 if(imageUri!=null){
                                     storageReference.putFile(imageuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                         @Override
                                         public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                             if(task.isSuccessful()){
                                                 storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                     @Override
                                                     public void onSuccess(Uri uri) {
                                                         imageUri=uri.toString();
                                                         Users users=new Users(imageUri,Email,Username,Password,status,id);
                                                         databaseReference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                             @Override
                                                             public void onComplete(@NonNull Task<Void> task) {
                                                                  if(task.isSuccessful()){
                                                                      Toast.makeText(Registration.this,"The account has been created",Toast.LENGTH_SHORT).show();
                                                                  }else{
                                                                      Toast.makeText(Registration.this,"Fail to create user",Toast.LENGTH_SHORT).show();
                                                                  }
                                                             }
                                                         });
                                                     }
                                                 });
                                             }
                                         }
                                     });
                                 }else {
                                     imageUri="https://firebasestorage.googleapis.com/v0/b/chatting-9837d.appspot.com/o/avatar.png?alt=media&token=9fdbfd8c-d0ff-4e98-9c74-0b30d9bfd06f";
                                     Users users=new Users(imageUri,Email,Username,Password,status,id);
                                     databaseReference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                         @Override
                                         public void onComplete(@NonNull Task<Void> task) {
                                             if(task.isSuccessful()){
                                                 Toast.makeText(Registration.this,"The account has been created",Toast.LENGTH_SHORT).show();
                                             }else{
                                                 Toast.makeText(Registration.this,"Fail to create user",Toast.LENGTH_SHORT).show();
                                             }
                                         }
                                     });}
                             }else{
                                 Toast.makeText(Registration.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                             }
                        }
                    });
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10){
            if(data!=null){
                imageuri=data.getData();
                profile.setImageURI(imageuri);
            }
        }
    }
}
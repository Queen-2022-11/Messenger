package com.example.chatting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    RecyclerView recyclerView;
    UserAdapter adapter;
    FirebaseDatabase database;
    ArrayList<Users> users;
    ImageView imglogout;
    ImageView cumbut,setbut;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the ArrayList before creating the adapter
        users = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("user");
        recyclerView = findViewById(R.id.main);
        cumbut = findViewById(R.id.camera);
        setbut = findViewById(R.id.setting);
        imglogout=findViewById(R.id.logout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter after the users ArrayList is created
        adapter = new UserAdapter(MainActivity.this, users);
        recyclerView.setAdapter(adapter);

        auth = FirebaseAuth.getInstance();
        Log.d("MainActivity", "Users list size: " + users.size());

        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear(); // Clear the list before adding new data

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Users users1 = dataSnapshot.getValue(Users.class);
                    if (users1 != null) {
                        Log.d("MainActivity", "User: " + users1.getUserName());

                        // Additional null checks for user properties
                        if (users1.getUserName() != null) {
                            // If userName is not null, add the user to the list
                            users.add(users1);
                        } else {
                            Log.e("MainActivity", "User has a null userName");
                        }
                    } else {
                        Log.e("MainActivity", "User is null");
                    }
                }

                // Notify the adapter only if there is a change in the data
                if (!users.isEmpty()) {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MainActivity", "Data fetching failed: " + error.getMessage());
            }
        });


        imglogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(MainActivity.this,R.style.dialoge);
                dialog.setContentView(R.layout.dailog_layout);
                Button no,yes;
                yes = dialog.findViewById(R.id.yesbnt);
                no = dialog.findViewById(R.id.nobnt);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(MainActivity.this,Login.class);
                        startActivity(intent);
                        finish();
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        setbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, setting.class);
                startActivity(intent);
            }
        });

        cumbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,10);
            }
        });
        if (auth.getCurrentUser() == null) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish(); // Optional: finish the activity if a login is required
        }
    }
}

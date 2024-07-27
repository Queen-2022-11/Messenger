package com.example.chatting;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewholder> {
    Context mainActivity;
    ArrayList<Users> users;

    public UserAdapter(MainActivity mainActivity, ArrayList<Users> users) {
        this.mainActivity = mainActivity;
        this.users = users;
    }

    @NonNull
    @Override
    public UserAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mainActivity).inflate(R.layout.users,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.viewholder holder, int position) {
        Users users1=users.get(position);
        holder.uname.setText(users1.userName);
        holder.ustatus.setText(users1.status);
        Picasso.get().load(users1.profilepic).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity, chatwindo.class);
                intent.putExtra("nameeee",users1.getUserName());
                intent.putExtra("reciverImg",users1.getProfilepic());
                intent.putExtra("uid",users1.getUserId());
                mainActivity.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return  users != null ? users.size() : 0;
    }

    public class viewholder extends RecyclerView.ViewHolder{
        CircleImageView imageView;
        TextView uname;
        TextView ustatus;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.profile2);
            uname=itemView.findViewById(R.id.uname);
            ustatus=itemView.findViewById(R.id.ustatus);
        }
    }
}

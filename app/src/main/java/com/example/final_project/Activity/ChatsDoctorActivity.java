package com.example.final_project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.final_project.R;
import com.example.final_project.adapter.AdapterChats;
import com.example.final_project.adapter.AdapterSearchDoctor;
import com.example.final_project.databinding.ActivityChatsDoctorBinding;
import com.example.final_project.model.ModelChats;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatsDoctorActivity extends AppCompatActivity {

    private ActivityChatsDoctorBinding binding;
    private RecyclerView chatsList;
    private DatabaseReference chatsRef, usersRef;
    private FirebaseAuth mAuth;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatsDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth=FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();
        chatsRef= FirebaseDatabase.getInstance().getReference().child("Messages").child(currentUserId);
        usersRef=FirebaseDatabase.getInstance().getReference().child("Users");

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<ModelChats> options=
                new FirebaseRecyclerOptions.Builder<ModelChats>()
                        .setQuery(chatsRef,ModelChats.class)
                        .build();

        FirebaseRecyclerAdapter<ModelChats, chatsViewHolder> adapter=
                new FirebaseRecyclerAdapter<ModelChats, chatsViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull chatsViewHolder holder, int position, @NonNull ModelChats model) {

                        final String usersIDs=getRef(position).getKey();
                        final String[] retImage = {"default_image"};
                        usersRef.child(usersIDs).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                if (snapshot.exists()){
                                    if(snapshot.hasChild("profileImage")){
                                        retImage[0] =snapshot.child("profileImage").getValue().toString();


                                        Glide.with(ChatsDoctorActivity.this)
                                                .load(retImage[0])
                                                .placeholder(R.drawable.ic_person_gray)
                                                .into(holder.profileImage);
                                    }

                                    final String retName=snapshot.child("name").getValue().toString();
                                    final String doctorUid = snapshot.child("uid").getValue().toString();

                                    holder.userName.setText(retName);

                                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent ChatsIntent = new Intent(ChatsDoctorActivity.this, ChatActivity.class);
                                            ChatsIntent.putExtra("ID",doctorUid);
                                            startActivity(ChatsIntent);
                                        }
                                    });


                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                    @NonNull
                    @Override
                    public chatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_chats_doctor,parent,false);
                        return new chatsViewHolder(view);
                    }
                };
        binding.doctorRv.setAdapter(adapter);
        adapter.startListening();
    }

    public static class chatsViewHolder extends RecyclerView.ViewHolder{

        ImageView profileImage;
        TextView userName, userStatus;

        public chatsViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage=itemView.findViewById(R.id.profileIv);
            userName=itemView.findViewById(R.id.titleTv);

        }
    }
}
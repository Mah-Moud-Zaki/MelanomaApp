package com.example.final_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.final_project.Activity.DoctorDetailsActivity;
import com.example.final_project.R;
import com.example.final_project.databinding.CustomChatsDoctorBinding;
import com.example.final_project.model.ModelChats;

import java.util.ArrayList;

public class AdapterChats extends RecyclerView.Adapter<AdapterChats.HolderChats> {

    private Context context;
    private CustomChatsDoctorBinding binding;
    private ArrayList<ModelChats> chatsArrayList;

    public AdapterChats(Context context, ArrayList<ModelChats> chatsArrayList) {
        this.context = context;
        this.chatsArrayList = chatsArrayList;
    }

    @NonNull
    @Override
    public HolderChats onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = CustomChatsDoctorBinding.inflate(LayoutInflater.from(context), parent,false);

        return new HolderChats(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderChats holder, int position) {

        ModelChats model = chatsArrayList.get(position);
        String doctorName = model.getName();
        String profileImage= model.getProfileImage();
        String doctorUid= model.getUid();

        holder.titleTv.setText(doctorName);

        Glide.with(context)
                .load(profileImage)
                .placeholder(R.drawable.ic_person_gray)
                .into(holder.profileIv);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {

        return chatsArrayList.size();
    }

    class HolderChats extends RecyclerView.ViewHolder {
        ImageView profileIv;
        TextView titleTv;
        public HolderChats(@NonNull View itemView) {
            super(itemView);

            profileIv =binding.profileIv;
            titleTv = binding.titleTv;

        }
    }
}

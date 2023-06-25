package com.example.final_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.final_project.Activity.DoctorDetailsActivity;
import com.example.final_project.R;
import com.example.final_project.databinding.RowDoctorsBinding;
import com.example.final_project.filters.FilterDoctors;
import com.example.final_project.model.ModelSearch;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class AdapterSearchDoctor extends RecyclerView.Adapter<AdapterSearchDoctor.HolderSearchDoctor> implements Filterable {

    private Context context;
    private RowDoctorsBinding binding;
    public ArrayList<ModelSearch> doctorArrayList, filterList;
    private FilterDoctors filter;


    public AdapterSearchDoctor(Context context, ArrayList<ModelSearch> doctorArrayList) {
        this.context = context;
        this.doctorArrayList = doctorArrayList;
        this.filterList = doctorArrayList;
    }

    @NonNull
    @Override
    public HolderSearchDoctor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RowDoctorsBinding.inflate(LayoutInflater.from(context), parent, false);
        return new HolderSearchDoctor(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderSearchDoctor holder, int position) {
        //get data
        ModelSearch model = doctorArrayList.get(position);
        String doctorName = model.getName();
        String addressDoctor = model.getAddress();
        String phoneDoctor = model.getPhone();
        String profileImage= model.getProfileImage();
        String doctorUid= model.getUid();

        //set data
        holder.titleTv.setText(doctorName);
        holder.addressEt.setText(addressDoctor);
        holder.phoneEt.setText(phoneDoctor);

        Glide.with(context)
                .load(profileImage)
                .placeholder(R.drawable.ic_person_gray)
                .into(holder.profileIv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DoctorDetailsActivity.class);
                intent.putExtra("uid",doctorUid);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctorArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if (filter ==null){
            filter=new FilterDoctors(filterList,this);
        }
        return filter;
    }


    class HolderSearchDoctor extends RecyclerView.ViewHolder{

        ImageView profileIv;
        TextView titleTv, addressEt, phoneEt;

        public HolderSearchDoctor(@NonNull CardView itemView) {
            super(itemView);

            profileIv =binding.profileIv;
            titleTv = binding.titleTv;
            addressEt = binding.addressEt;
            phoneEt = binding.phoneEv;


        }
    }


}

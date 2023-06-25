package com.example.final_project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.final_project.MainActivity;
import com.example.final_project.R;
import com.example.final_project.databinding.ActivityProfilForDoctorBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilActivityForDoctor extends AppCompatActivity {

    private ActivityProfilForDoctorBinding binding;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfilForDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth=FirebaseAuth.getInstance();


        loadUserInfo();

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilActivityForDoctor.this, MainActivity.class));
            }
        });

        binding.profileEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilActivityForDoctor.this,ProfileEditActivityForDoctor.class));
            }
        });
        
    }

    private void loadUserInfo() {

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String email=""+snapshot.child("email").getValue();
                        String name=""+snapshot.child("name").getValue();
                        String phone=""+snapshot.child("phone").getValue();
                        String address=""+snapshot.child("address").getValue();
                        String university=""+snapshot.child("university").getValue();
                        String certificate=""+snapshot.child("certificates").getValue();
                        String profileImage=""+snapshot.child("profileImage").getValue();
                        String about=""+snapshot.child("about").getValue();

                        binding.nameTv.setText(name);
                        binding.emailTv.setText(email);
                        binding.phoneNum.setText(phone);
                        binding.addressCity.setText(address);
                        binding.universityEt.setText(university);
                        binding.certificateEt.setText(certificate);
                        binding.aboutEt.setText(about);

                        Glide.with(ProfilActivityForDoctor.this)
                                .load(profileImage)
                                .placeholder(R.drawable.ic_person_gray)
                                .into(binding.profileIv);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}
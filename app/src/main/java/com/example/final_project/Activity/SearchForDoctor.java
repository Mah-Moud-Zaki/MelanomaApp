package com.example.final_project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.example.final_project.R;
import com.example.final_project.adapter.AdapterSearchDoctor;
import com.example.final_project.databinding.ActivitySearchForDoctorBinding;
import com.example.final_project.model.ModelSearch;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchForDoctor extends AppCompatActivity {

    private ActivitySearchForDoctorBinding binding;
    private ArrayList<ModelSearch> doctorArrayList;
    private AdapterSearchDoctor adapterSearchDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchForDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadPdfList();

        binding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    adapterSearchDoctor.getFilter().filter(s);

                }
                catch (Exception e){


                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void loadPdfList() {

        doctorArrayList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.orderByChild("userType").equalTo("Doctor")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        doctorArrayList.clear();
                        for (DataSnapshot ds: snapshot.getChildren()) {
                            ModelSearch model = ds.getValue(ModelSearch.class);
                            doctorArrayList.add(model);
                        }

                        adapterSearchDoctor = new AdapterSearchDoctor(SearchForDoctor.this, doctorArrayList);
                        binding.doctorRv.setAdapter(adapterSearchDoctor);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}
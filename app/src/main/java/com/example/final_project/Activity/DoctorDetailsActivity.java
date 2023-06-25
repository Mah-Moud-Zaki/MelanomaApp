package com.example.final_project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.final_project.R;
import com.example.final_project.adapter.AdapterComment;
import com.example.final_project.databinding.ActivityDoctorDetailsBinding;
import com.example.final_project.databinding.DialogCommentAddBinding;
import com.example.final_project.model.ModelComment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorDetailsActivity extends AppCompatActivity {

    private ActivityDoctorDetailsBinding binding;
    String doctorUid;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private String currentUserId;

    private ArrayList<ModelComment> commentArrayList;
    private AdapterComment adapterComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent=getIntent();
        doctorUid=intent.getStringExtra("uid");

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        firebaseAuth=FirebaseAuth.getInstance();
        currentUserId=firebaseAuth.getCurrentUser().getUid();

        if (currentUserId.equals(doctorUid)){
            binding.sendMessage.setVisibility(View.INVISIBLE);
        }

        loadDoctorDetails();
        loadComment();

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.addCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    addCommentDialog();

            }
        });

        binding.sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorDetailsActivity.this, ChatActivity.class);
                intent.putExtra("ID",doctorUid);
                startActivity(intent);
            }
        });

    }

    private void loadComment() {
        commentArrayList=new ArrayList<>();

        DatabaseReference ref =FirebaseDatabase.getInstance().getReference("Users");
        ref.child(doctorUid).child("Comments")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        commentArrayList.clear();
                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                            ModelComment model = ds.getValue(ModelComment.class);
                            commentArrayList.add(model);
                        }
                        adapterComment=new AdapterComment(DoctorDetailsActivity.this, commentArrayList);
                        binding.commentsRv.setAdapter(adapterComment);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private String comment="";
    private void addCommentDialog() {
        DialogCommentAddBinding commentAddBinding=DialogCommentAddBinding.inflate(LayoutInflater.from(this));

        AlertDialog.Builder builder=new AlertDialog.Builder(this,R.style.CustomDialog);
        builder.setView(commentAddBinding.getRoot());
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

        commentAddBinding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        commentAddBinding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment=commentAddBinding.commentEt.getText().toString().trim();

                if(TextUtils.isEmpty(comment)){
                    Toast.makeText(DoctorDetailsActivity.this, "Enter your Comment...", Toast.LENGTH_SHORT).show();
                }
                else{
                    alertDialog.dismiss();
                    addComment();
                }
            }
        });
    }

    private void addComment() {
        progressDialog.setMessage("Adding comment...");
        progressDialog.show();

        String timestamp=""+System.currentTimeMillis();

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("id",""+timestamp);
        hashMap.put("doctorUid",""+doctorUid);
        hashMap.put("timestamp",""+timestamp);
        hashMap.put("comment",""+comment);
        hashMap.put("uid",firebaseAuth.getUid());

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(doctorUid).child("Comments").child(timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void unused) {
                         Toast.makeText(DoctorDetailsActivity.this, "Comment Added...", Toast.LENGTH_SHORT).show();
                         progressDialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(DoctorDetailsActivity.this, "Failed to add comment due to..."+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadDoctorDetails() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(doctorUid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String doctorName = ""+snapshot.child("name").getValue();
                        String address = ""+snapshot.child("address").getValue();
                        String phone = ""+snapshot.child("phone").getValue();
                        String aboutDoctor = ""+snapshot.child("about").getValue();
                        String profileImage = ""+snapshot.child("profileImage").getValue();


                        binding.titleTv.setText(doctorName);
                        binding.addressEt.setText(address);
                        binding.phoneEv.setText(phone);
                        if (aboutDoctor == null){
                            binding.aboutEt.setText(" ");
                        }else {
                            binding.aboutEt.setText(aboutDoctor);
                        }

                        Glide.with(DoctorDetailsActivity.this)
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
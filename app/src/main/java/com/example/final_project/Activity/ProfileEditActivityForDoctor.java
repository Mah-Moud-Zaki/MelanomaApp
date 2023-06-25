package com.example.final_project.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.final_project.R;
import com.example.final_project.databinding.ActivityProfileEditForDoctorBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class ProfileEditActivityForDoctor extends AppCompatActivity {

    private ActivityProfileEditForDoctorBinding binding;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private Uri imageUri=null;
    private String name="", phone="", address="",about="",university="",certificate="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityProfileEditForDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        firebaseAuth=FirebaseAuth.getInstance();
        loadUserInfo();

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.profileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageAttachMenu();

            }
        });


        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
    }
    private void loadUserInfo(){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String email=""+dataSnapshot.child("email").getValue();
                        String name=""+dataSnapshot.child("name").getValue();
                        String phone=""+dataSnapshot.child("phone").getValue();
                        String address=""+dataSnapshot.child("address").getValue();
                        String university=""+dataSnapshot.child("university").getValue();
                        String certificate=""+dataSnapshot.child("certificates").getValue();
                        String profileImage=""+dataSnapshot.child("profileImage").getValue();
                        String about=""+dataSnapshot.child("about").getValue();

                        binding.nameEt.setText(name);
                        binding.phoneEt.setText(phone);
                        binding.addressEt.setText(address);
                        binding.universityEt.setText(university);
                        binding.certificateEt.setText(certificate);
                        binding.aboutEt.setText(about);

                        Glide.with(ProfileEditActivityForDoctor.this)
                                .load(profileImage)
                                .placeholder(R.drawable.ic_person_gray)
                                .into(binding.profileIv);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void validateData(){
        name=binding.nameEt.getText().toString().trim();
        phone=binding.phoneEt.getText().toString().trim();
        address=binding.addressEt.getText().toString().trim();
        about=binding.aboutEt.getText().toString().trim();
        university=binding.universityEt.getText().toString().trim();
        certificate=binding.certificateEt.getText().toString().trim();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Enter Name...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Enter Phone...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(address)){
            Toast.makeText(this, "Enter Address...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(about)){
            Toast.makeText(this, "Enter About...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(university)){
            Toast.makeText(this, "Enter University...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(certificate)){
            Toast.makeText(this, "Enter Certificate...", Toast.LENGTH_SHORT).show();
        }
        else{
            if(imageUri==null){
                updateProfile("");
            }
            else {
                uploadImage();
            }
        }
    }

    private void uploadImage(){
        progressDialog.setMessage("Updating Profile image");
        progressDialog.show();

        String filePathAndName="ProfileImages/"+firebaseAuth.getUid();

        StorageReference reference= FirebaseStorage.getInstance().getReference(filePathAndName);
        reference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        String uploadImageUrl=""+uriTask.getResult();
                        updateProfile(uploadImageUrl);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(ProfileEditActivityForDoctor.this, "failed to upload image due to"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateProfile(String imageUrl){

        progressDialog.setMessage("Updating User Profile");
        progressDialog.show();

        HashMap<String, Object> hashMap=new HashMap<>();
        hashMap.put("name",""+name);
        hashMap.put("address", address);
        hashMap.put("phone", phone);
        hashMap.put("university", university);
        hashMap.put("certificates", certificate);
        hashMap.put("about", about);
        if(imageUri != null){
            hashMap.put("profileImage",""+imageUrl);
        }

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(firebaseAuth.getUid())
                .updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(ProfileEditActivityForDoctor.this, "Profile updating...", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(ProfileEditActivityForDoctor.this, "Failed to Update db due to"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void showImageAttachMenu(){
        PopupMenu popupMenu=new PopupMenu(this,binding.profileIv);
        popupMenu.getMenu().add(Menu.NONE, 0, 0,"Camera");
        popupMenu.getMenu().add(Menu.NONE, 1, 1,"Gallery");

        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int which=item.getItemId();
                if(which==0){
                    //camera
                    pickImageCamera();
                }
                else if (which==1){
                    //gallery
                    pickImageGallery();
                }

                return false;
            }
        });
    }

    private void pickImageCamera(){
        ContentValues values=new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "new pick");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Sample Image Description");
        imageUri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        cameraActivityResultLauncher.launch(intent);
    }

    private void pickImageGallery(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryActivityResultLauncher.launch(intent);
    }

    private ActivityResultLauncher<Intent> cameraActivityResultLauncher =registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()== Activity.RESULT_OK){
                        Intent data=result.getData();
                        binding.profileIv.setImageURI(imageUri);
                    }
                    else {
                        Toast.makeText(ProfileEditActivityForDoctor.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }

                }
            }
    );
    private ActivityResultLauncher<Intent> galleryActivityResultLauncher =registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()== Activity.RESULT_OK){
                        Intent data=result.getData();
                        imageUri=data.getData();
                        binding.profileIv.setImageURI(imageUri);
                    }
                    else {
                        Toast.makeText(ProfileEditActivityForDoctor.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }

                }
            }
    );
}
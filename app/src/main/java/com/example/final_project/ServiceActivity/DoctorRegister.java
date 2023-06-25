package com.example.final_project.ServiceActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.final_project.MainActivity;
import com.example.final_project.R;
import com.example.final_project.databinding.ActivityDoctorRegisterBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DoctorRegister extends AppCompatActivity {

    private ActivityDoctorRegisterBinding binding;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private String selectedGender="male";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth=FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorRegister.this,LoginActivity.class));
            }
        });
        binding.alreadyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorRegister.this,LoginActivity.class));
            }
        });

        binding.genderEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionDaialog();
            }

            private void showOptionDaialog() {
                String[] genders={"Male","Female"};
                AlertDialog.Builder builder=new AlertDialog.Builder(DoctorRegister.this);
                builder.setTitle("Chose Gender");
                builder.setSingleChoiceItems(genders, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedGender=genders[which];
                        binding.genderEt.setText(selectedGender);

                    }
                });
                builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }
        });

        binding.cirRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();

            }
        });

    }

    private String name="",email="",password="",address="",phone="",university="",certificates="";
    private void validateData() {

        name = binding.nameEt.getText().toString().trim();
        email = binding.emailEt.getText().toString().trim();
        password = binding.passwordEt.getText().toString().trim();
        String cPassword = binding.cPasswordEt.getText().toString().trim();
        address = binding.addressEt.getText().toString().trim();
        phone = binding.phoneEt.getText().toString().trim();
        university = binding.universityEt.getText().toString().trim();
        certificates = binding.certificateEt.getText().toString().trim();

        if (TextUtils.isEmpty(name)){
            Toast.makeText(this,"Enter your name...", Toast.LENGTH_SHORT).show();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"Invalid Email Pattern...!",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Enter Password...!",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(cPassword)){
            Toast.makeText(this,"Confirm Password...!",Toast.LENGTH_SHORT).show();
        }
        else if(!password.equals(cPassword)){
            Toast.makeText(this,"Password doesn't match",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(selectedGender)){
            Toast.makeText(this, "Pick Gender...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(address)){
            Toast.makeText(this, "Enter your Address...!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Enter your Phone Number...!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(university)){
            Toast.makeText(this, "Enter your University", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(certificates)){
            Toast.makeText(this, "Enter your Certificates", Toast.LENGTH_SHORT).show();
        }
        else {
            createUserAccount();
        }

    }

    private void createUserAccount() {

        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //account creation success
                        updateUserInfo();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure( Exception e) {
                        //account creation failed
                        progressDialog.dismiss();
                        Toast.makeText(DoctorRegister.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void updateUserInfo() {

        progressDialog.setMessage("Saving User Info");
        long timestamp = System.currentTimeMillis();
        String uid = firebaseAuth.getUid();
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("uid", uid);
        hashMap.put("email", email);
        hashMap.put("name", name);
        hashMap.put("gender", selectedGender);
        hashMap.put("address", address);
        hashMap.put("phone", phone);
        hashMap.put("university", university);
        hashMap.put("certificates", certificates);
        hashMap.put("profileImage", "");
        hashMap.put("about", " ");
        hashMap.put("userType", "Doctor");
        hashMap.put("timestamp", timestamp);

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users");
        ref.child(uid)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(DoctorRegister.this,"Account created...",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DoctorRegister.this, MainActivity.class));
                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure( Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(DoctorRegister.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
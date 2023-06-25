package com.example.final_project.nada;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.final_project.Activity.ChatsDoctorActivity;
import com.example.final_project.Activity.ProfilActivityForDoctor;
import com.example.final_project.Activity.ProfileEditActivityForDoctor;
import com.example.final_project.Activity.ProfileEditActivityForPatient;
import com.example.final_project.Activity.SearchForDoctor;
import com.example.final_project.MainActivity;
import com.example.final_project.ModelAi.ModelActivity;
import com.example.final_project.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingActivity extends AppCompatActivity {
    Toolbar toolbar;
     Switch switcher;
   public boolean nighMODE;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageView imageView;
    TextView namePF, edit;
    private FirebaseAuth firebaseAuth;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton btn_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        firebaseAuth = FirebaseAuth.getInstance();

        loadUserInfo();

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){

            // when night mode is equal to use set dark mode
            setTheme(R.style.Theme_Final_project_dark);

        }else{
            // when light mode is equal to use set light mode
            setTheme(R.style.Theme_Final_project);


        }

        imageView = findViewById(R.id.imageVW);
        namePF = findViewById(R.id.name);
        edit = findViewById(R.id.editPR);

        toolbar = findViewById(R.id.Settingbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_btn_back);
        getSupportActionBar().setTitle("Setting");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // --------------------- night mode -------------------------
        switcher = findViewById(R.id.switcher);
        // we use  sharedPreferences to save mode if exit the app and go back again

        darkModeButton();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
                reference.child(firebaseAuth.getUid())
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String user = ""+snapshot.child("userType").getValue();

                                        if(user.equals("Doctor")){
                                            startActivity(new Intent(SettingActivity.this, ProfileEditActivityForDoctor.class));
                                        }
                                        if(user.equals("patient")){
                                            startActivity(new Intent(SettingActivity.this, ProfileEditActivityForPatient.class));
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

            }
        });

        btn_home = findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , MainActivity.class);
                startActivity(intent);
            }
        });

        bottomNavigationView = findViewById(R.id.buttomNavigationView);
        bottomNavigationView.setBackground(null);

        // set home selector
        bottomNavigationView.setSelectedItemId(R.id.nav_setting);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.nav_chat:
                        Intent intent0 = new Intent(getApplicationContext() , ChatsDoctorActivity.class);
                        startActivity(intent0);
                        overridePendingTransition(0,0);
                        return  true;
                    case R.id.nav_doctors:
                        Intent intent_0 = new Intent(getApplicationContext() , SearchForDoctor.class);
                        startActivity(intent_0);
                        return  true;
                    case R.id.nav_check:
                        Intent intent_1 = new Intent(getApplicationContext() , ModelActivity.class);
                        startActivity(intent_1);
                        overridePendingTransition(0,0);
                        return  true;
                    case R.id.btn_home:
                        Intent intent = new Intent(getApplicationContext() , MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return  true;

                    case R.id.nav_setting:
                        return  true;


                }


                return false;
            }
        });


    }

    public boolean  onSupportNavigateUp(){

        Intent intent = new Intent(this , MainActivity.class);
        finish();
        startActivity(intent);
        return true;
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

                        namePF.setText(name);


                        Glide.with(SettingActivity.this)
                                .load(profileImage)
                                .placeholder(R.drawable.ic_person_gray)
                                .into(imageView);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void darkModeButton() {


        if (loadMode()) {
            switcher.setChecked(true);
        }
        switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                    saveMode(true);
                    recreate();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                    saveMode(false);
                }
            }
        });
    }

    // كود حفظ الوضغ الذي اختاره المستخدم وتخزينه
    private void saveMode(Boolean state){
        SharedPreferences sharedPreferences = getSharedPreferences("Mode", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("NightMode", state);
        editor.apply();
    }

    // كود جلب الوضع الذي اختاره المستخدم والبدء به بالتطبيق
    private Boolean loadMode(){
        SharedPreferences sharedPreferences = getSharedPreferences("Mode", MODE_PRIVATE);
        Boolean state = sharedPreferences.getBoolean("NightMode", false);
        return state;
    }


}
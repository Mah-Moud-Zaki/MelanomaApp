package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.final_project.Activity.ChatsDoctorActivity;
import com.example.final_project.Activity.ProfilActivityForDoctor;
import com.example.final_project.Activity.ProfilActivityForPatient;
import com.example.final_project.Activity.ProfileEditActivityForPatient;
import com.example.final_project.Activity.SearchForDoctor;
import com.example.final_project.ModelAi.ModelActivity;
import com.example.final_project.ServiceActivity.LoginActivity;
import com.example.final_project.databinding.ActivityMainBinding;
import com.example.final_project.nada.Actinin_Details_Activity;
import com.example.final_project.nada.Basal_Details_Activity;
import com.example.final_project.nada.Begin_Details_Activity;
import com.example.final_project.nada.Dermato_Details_Activity;
import com.example.final_project.nada.DetailsActivity;
import com.example.final_project.nada.Nevus_details_Activity;
import com.example.final_project.nada.SettingActivity;
import com.example.final_project.nada.Vascular_Details_Activity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    CardView cardMelanoma;
    CardView cardNevus;
    CardView cardbegin;
    CardView cardVascular;
    CardView cardBacal;
    CardView cardActinin;
    CardView cardDermato;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* -----------------------*/

        firebaseAuth = FirebaseAuth.getInstance();

        /*--------------------------------------*/
        cardMelanoma = findViewById(R.id.cardmelano);
        cardNevus = findViewById(R.id.cardnevus);
        cardbegin = findViewById(R.id.cardbegin);
        cardVascular = findViewById(R.id.cardvascular);
        cardBacal = findViewById(R.id.cardbasal);
        cardActinin = findViewById(R.id.cardactinin);
        cardDermato = findViewById(R.id.carddermato);

        cardMelanoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent intent = new Intent(getApplicationContext() , MelanomaActivity.class);
                //   startActivity(intent);
                Intent intent = new Intent(getApplicationContext() , DetailsActivity.class);
                startActivity(intent);
            }
        });


        cardNevus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , Nevus_details_Activity.class);
                startActivity(intent);

            }
        });

        cardbegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , Begin_Details_Activity.class);
                startActivity(intent);

            }
        });

        cardVascular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , Vascular_Details_Activity.class);
                startActivity(intent);

            }
        });

        cardBacal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , Basal_Details_Activity.class);
                startActivity(intent);

            }
        });

        cardActinin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , Actinin_Details_Activity.class);
                startActivity(intent);

            }
        });


        cardDermato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , Dermato_Details_Activity.class);
                startActivity(intent);


            }
        });



        /*-------------Hooks---------------------*/
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);


        bottomNavigationView = findViewById(R.id.buttomNavigationView);
        bottomNavigationView.setBackground(null);

        //--------------------------------------
        ListView listView;



        //-----------------------------------------------------
        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){

                case R.id.nav_chat:
                    Intent intent0 = new Intent(getApplicationContext() , ChatsDoctorActivity.class);
                    startActivity(intent0);
                    break;
                case R.id.nav_doctors:
                    Intent intent_0 = new Intent(getApplicationContext() , SearchForDoctor.class);
                    startActivity(intent_0);
                    break;
                case R.id.nav_check:
                    Intent intent_1 = new Intent(getApplicationContext() , ModelActivity.class);
                    startActivity(intent_1);
                    break;
                case R.id.nav_home:
                    Intent intent = new Intent(getApplicationContext() , MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_setting:
                    Intent intent1 = new Intent(getApplicationContext() , SettingActivity.class);
                    startActivity(intent1);

                    break;




            }
            return  true;
        });
        /*-------------------Tool Bar -------------*/
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_24);

        /*--------------Navigation Drawer Menu -------*/
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this , drawerLayout , toolbar , R.string.navigation_Drawer_open , R.string.navigation_Drawer_close);
        ActionBarDrawerToggle toggle2 = new ActionBarDrawerToggle(this , drawerLayout , toolbar , R.string.navigation_Drawer_open , R.string.navigation_Drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle2.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toggle.syncState();
        toggle2.syncState();
        navigationView.setNavigationItemSelectedListener(this);


    }


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){

            drawerLayout.closeDrawer(GravityCompat.START);

        }else{

            super.onBackPressed();

        }


    }







    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){

            case R.id.nav_share:
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String body = "Your body here";
                String sub = "Your Subject";
                myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
                myIntent.putExtra(Intent.EXTRA_TEXT,body);
                startActivity(Intent.createChooser(myIntent, "Share Using"));
                break;

            case R.id.Rateus:
                try {
                    Uri uri =Uri.parse("market://details?id=" + getPackageName());
                    Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }catch (ActivityNotFoundException e){

                    Uri uri = Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName());
                    Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

                break;



            case R.id.nav_home:

                Intent intent = new Intent(getApplicationContext() , MainActivity.class);
                startActivity(intent);

                break;


            case R.id.nav_profile:

                checkUser();

                break;
            case R.id.nav_logout:

                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();

                break;


        }


        return true;
    }

    private void checkUser() {


        FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot snapshot) {
                        String userType = ""+snapshot.child("userType").getValue();
                        if (userType.equals("patient")){
                            startActivity(new Intent(getApplicationContext(), ProfilActivityForPatient.class));

                        }
                        else if(userType.equals("Doctor")){
                            startActivity(new Intent(getApplicationContext(),ProfilActivityForDoctor.class));

                        }


                    }

                    @Override
                    public void onCancelled( DatabaseError error) {

                    }
                });
    }










}

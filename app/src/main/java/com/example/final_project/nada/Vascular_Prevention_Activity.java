package com.example.final_project.nada;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.final_project.R;

public class Vascular_Prevention_Activity extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vascular_prevention);

        toolbar = findViewById(R.id.Vascular_prevention_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_btn_back);
        getSupportActionBar().setTitle("Prevention");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



    }
    public boolean  onSupportNavigateUp(){

        Intent intent = new Intent(this ,Vascular_Details_Activity.class);
        finish();
        startActivity(intent);
        return true;
    }



}
package com.example.final_project.nada;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.final_project.R;

public class Actinin_treatment_Activity extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actinin_treatment);

        toolbar = findViewById(R.id.Actinin_treatment_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_btn_back);
        getSupportActionBar().setTitle("Treatment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



    }

    public boolean  onSupportNavigateUp(){

        Intent intent = new Intent(this ,Actinin_Details_Activity.class);
        finish();
        startActivity(intent);
        return true;
    }





}
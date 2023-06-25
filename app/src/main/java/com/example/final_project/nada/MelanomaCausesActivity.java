package com.example.final_project.nada;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.final_project.R;

public class MelanomaCausesActivity extends AppCompatActivity {

    CardView cardcaues_1;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_melanoma_causes);

        toolbar = findViewById(R.id.Causestoolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        getSupportActionBar().setTitle("Causes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_btn_back);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        cardcaues_1 = findViewById(R.id.card_1);





    }

    public boolean  onSupportNavigateUp(){

        Intent intent = new Intent(this , DetailsActivity.class);
        finish();
        startActivity(intent);
        return true;
    }






}
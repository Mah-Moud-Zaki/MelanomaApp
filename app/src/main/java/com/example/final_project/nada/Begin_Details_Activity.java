package com.example.final_project.nada;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.final_project.MainActivity;
import com.example.final_project.R;

public class Begin_Details_Activity extends AppCompatActivity {

    CardView cardcaues  , cardsymptoms  , cardtreatment , cardprevention;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin_details);

        toolbar = findViewById(R.id.Begintoolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_btn_back);
        getSupportActionBar().setTitle("GuideLines");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        cardcaues = findViewById(R.id.cardCauses);

        cardcaues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , Begin_causes_Activity.class);
                startActivity(intent);

            }
        });
        cardsymptoms = findViewById(R.id.cardSymptoms);

        cardsymptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , Begin_Symptoms_Activity.class);
                startActivity(intent);

            }
        });


        cardtreatment = findViewById(R.id.cardTreatment);

        cardtreatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , Begin_Treatment_Activity.class);
                startActivity(intent);

            }
        });

        cardprevention = findViewById(R.id.cardPrevention);

        cardprevention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , Begin_Prevention_Activity.class);
                startActivity(intent);

            }
        });



    }

    public boolean  onSupportNavigateUp(){

        Intent intent = new Intent(this , MainActivity.class);
        finish();
        startActivity(intent);
        return true;
    }


}
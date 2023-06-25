package com.example.final_project.nada;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.final_project.R;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);




       final ArrayList<CancerType> arrayList = new ArrayList<CancerType>();


        // add all the values from 1 to 15 to the arrayList
        // the items are of the type NumbersView
        arrayList.add(new CancerType(R.drawable.info, "Malignant Melanoma", "Melanoma is not the most common type of skin \n" +
                "cancer, but it is the most serious because it often \n" +
                "spreads. Risk factors for melanoma include \n" +
                "overexposure to the sun."));
        arrayList.add(new CancerType(R.drawable.info, "Melanocyctic nevus", "Melanoma is not the most common type of skin \n" +
                "cancer, but it is the most serious because it often \n" +
                "spreads. Risk factors for melanoma include \n" +
                "overexposure to the sun."));
        arrayList.add(new CancerType(R.drawable.info, "Benign keratosis", "Melanoma is not the most common type of skin \n" +
                "cancer, but it is the most serious because it often \n" +
                "spreads. Risk factors for melanoma include \n" +
                "overexposure to the sun."));
        arrayList.add(new CancerType(R.drawable.info, "Vascular lesion", "Melanoma is not the most common type of skin \n" +
                "cancer, but it is the most serious because it often \n" +
                "spreads. Risk factors for melanoma include \n" +
                "overexposure to the sun."));
        arrayList.add(new CancerType(R.drawable.info, "Basal cell carcinoma", "Basal cell carcinoma is a type of skin cancer. Basal cell carcinoma begins in the basal cells — a type of cell within the skin that produces new skin cells as old ones die off."));
        arrayList.add(new CancerType(R.drawable.info, "Actinin keratosis", "Melanoma is not the most common type of skin \n" +
                "cancer, but it is the most serious because it often \n" +
                "spreads. Risk factors for melanoma include \n" +
                "overexposure to the sun."));
        arrayList.add(new CancerType(R.drawable.info, "Dermatofibroma", "Melanoma is not the most common type of skin \n" +
                "cancer, but it is the most serious because it often \n" +
                "spreads. Risk factors for melanoma include \n" +
                "overexposure to the sun."));


        // Now create the instance of the NumebrsViewAdapter and pass
        // the context and arrayList created above
        MyListView CancerTypeAdapter = new MyListView(this, arrayList);

        // create the instance of the ListView to set the numbersViewAdapter
        ListView cancerListView = findViewById(R.id.guideList);

        // set the numbersViewAdapter for ListView
        cancerListView.setAdapter(CancerTypeAdapter);
        cancerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                if(position == 0) {


                    //code specific to first list item

                    Intent intent = new Intent(getApplicationContext() ,MelanomaActivity.class);
                    startActivity(intent);




                }

                else if(position == 1) {
                    //code specific to 2nd list item
                    Intent intent = new Intent(getApplicationContext() , NevusActivity.class);
                    startActivity(intent);

                }

                else if(position == 2) {

                    Intent intent = new Intent(getApplicationContext() , BeginKeratosisActivity.class);
                    startActivity(intent);

                }
                else if(position == 3) {

                    Intent intent = new Intent(getApplicationContext() , VascularLesionActivity.class);
                    startActivity(intent);

                }
                else if(position == 4) {


                    Intent intent = new Intent(getApplicationContext() , BasalCellCarcinomaActivity.class);
                    startActivity(intent);

                }
                else if(position == 5) {


                    Intent intent = new Intent(getApplicationContext() , ActininKeratosisActivity.class);
                    startActivity(intent);

                }
                else if(position == 6) {


                    Intent intent = new Intent(getApplicationContext() , DermatofibromaActivity.class);
                    startActivity(intent);

                }


            }
        });



    }
}
package com.example.final_project.nada;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.final_project.R;

import java.util.ArrayList;

public class ActininKeratosisActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_melanoma);



        final ArrayList<ActininKeratosis> arrayList = new ArrayList<ActininKeratosis>();

       // getSupportActionBar().setTitle("Melanoma");
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // add all the values from 1 to 15 to the arrayList
        // the items are of the type NumbersView
        //---------------Melanoma-------------
        arrayList.add(new ActininKeratosis( "", "Melanoma is not the most common type of skin \n" +
                "cancer, but it is the most serious because it often " +
                "spreads. Risk factors for melanoma include " +
                "overexposure to the sun. \n" + "This article explains the symptoms of melanoma, how it is diagnosed, and how it is treated. " +
                "We also explain how best to prevent melanoma."));



                // Now create the instance of the NumebrsViewAdapter and pass
        // the context and arrayList created above

    ActininKeratosisView actininKeratosisView = new ActininKeratosisView(this , arrayList);
        // create the instance of the ListView to set the numbersViewAdapter
        ListView MelanoListView = findViewById(R.id.MelanomaList);

        // set the numbersViewAdapter for ListView
        MelanoListView.setAdapter(actininKeratosisView);

        // calling the action bar
     //   ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
    //    actionBar.setDisplayHomeAsUpEnabled(true);






    }
/*
    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


 */
}
package com.example.final_project.nada;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.final_project.R;

import java.util.ArrayList;

public class NevusActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_melanoma);



        final ArrayList<com.example.final_project.nada.Nevus> arrayList = new ArrayList<com.example.final_project.nada.Nevus>();

       // getSupportActionBar().setTitle("Melanoma");
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // add all the values from 1 to 15 to the arrayList
        // the items are of the type NumbersView
        //---------------Melanoma-------------
        arrayList.add(new com.example.final_project.nada.Nevus( "", "A melanocytic naevus (American spelling ‘nevus’), or mole, is a common benign skin lesion due to a local proliferation of pigment cells (melanocytes). It\n" +
                "is sometimes called a naevocytic naevus or just 'naevus' (but note that there are other types of naevi). A brown or black melanocytic naevus contains\n" +
                "the pigment melanin, so may also be called a pigmented naevus.\n" +
                "A melanocytic naevus can be present at birth (a congenital melanocytic naevus) or appear later (an acquired naevus). There are various kinds of\n" +
                "congenital and acquired melanocytic naevi (American spelling 'nevi')."));



                // Now create the instance of the NumebrsViewAdapter and pass
        // the context and arrayList created above

        NevusView nevusView = new NevusView(this , arrayList);
        // create the instance of the ListView to set the numbersViewAdapter
        ListView MelanoListView = findViewById(R.id.MelanomaList);

        // set the numbersViewAdapter for ListView
        MelanoListView.setAdapter(nevusView);

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
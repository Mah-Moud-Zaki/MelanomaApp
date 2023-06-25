package com.example.final_project.nada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.final_project.R;

import java.util.ArrayList;


public class ActininKeratosisView extends ArrayAdapter<ActininKeratosis> {

    public ActininKeratosisView(@NonNull Context  context, ArrayList<ActininKeratosis> actininKeratoses) {
        super(context, 0, actininKeratoses);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.melanomal_list, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        ActininKeratosis currentNumberPosition = getItem(position);



        // then according to the position of the view assign the desired TextView 1 for the same
        TextView maintitle = currentItemView.findViewById(R.id.txt_title);
        maintitle.setText(currentNumberPosition.getMainTitle());

        // then according to the position of the view assign the desired TextView 2 for the same
        TextView subtitle = currentItemView.findViewById(R.id.txt_description);
        subtitle.setText(currentNumberPosition.getDescribtion_Title());

        // then according to the position of the view assign the desired image for the same
        ImageView MelanomaImage = currentItemView.findViewById(R.id.MelanoImg);
        assert currentNumberPosition != null;
        MelanomaImage.setImageResource(currentNumberPosition.getImg());


        // then return the recyclable view
        return currentItemView;
    }







}

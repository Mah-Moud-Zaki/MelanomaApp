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

public class MyListView extends ArrayAdapter<CancerType> {


    public MyListView(@NonNull Context context,  ArrayList<CancerType> arrayList) {
        super(context, 0, arrayList);
    }





    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.mylist, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        CancerType currentNumberPosition = getItem(position);

        // then according to the position of the view assign the desired image for the same
        ImageView guideImage = currentItemView.findViewById(R.id.imageView);
        assert currentNumberPosition != null;
        guideImage.setImageResource(currentNumberPosition.getImg());

        // then according to the position of the view assign the desired TextView 1 for the same
        TextView maintitle = currentItemView.findViewById(R.id.main_title);
        maintitle.setText(currentNumberPosition.getMainTitle());

        // then according to the position of the view assign the desired TextView 2 for the same
        TextView subtitle = currentItemView.findViewById(R.id.sub_title);
        subtitle.setText(currentNumberPosition.getSubTitle());

        // then return the recyclable view
        return currentItemView;
    }
}








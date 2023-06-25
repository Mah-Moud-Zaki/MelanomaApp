package com.example.final_project.filters;

import android.widget.Filter;

import com.example.final_project.adapter.AdapterSearchDoctor;
import com.example.final_project.model.ModelSearch;

import java.util.ArrayList;

public class FilterDoctors extends Filter {

    ArrayList<ModelSearch> filterList;
    AdapterSearchDoctor adapterSearchDoctor;

    public FilterDoctors(ArrayList<ModelSearch> filterList, AdapterSearchDoctor adapterSearchDoctor) {
        this.filterList = filterList;
        this.adapterSearchDoctor = adapterSearchDoctor;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        if (constraint !=null&& constraint.length()>0){
            constraint=constraint.toString().toUpperCase();
            ArrayList<ModelSearch> filteredModels=new ArrayList<>();

            for(int i=0;i<filterList.size();i++){
                if(filterList.get(i).getName().toUpperCase().contains(constraint) || filterList.get(i).getAddress().toUpperCase().contains(constraint)){
                    filteredModels.add(filterList.get(i));
                }
            }
            results.count=filteredModels.size();
            results.values=filteredModels;
        }
        else{
            results.count=filterList.size();
            results.values=filterList;

        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapterSearchDoctor.doctorArrayList=(ArrayList<ModelSearch>)results.values;
        adapterSearchDoctor.notifyDataSetChanged();
    }
}

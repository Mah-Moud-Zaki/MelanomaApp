package com.example.final_project.ModelAi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class img {

    @SerializedName("highestprobability")
    @Expose
    private Double highestprobability;
    @SerializedName("predictedclass")
    @Expose
    private String predictedclass;


    public Double getHighestprobability() {
        return highestprobability;
    }

    public void setHighestprobability(Double highestprobability) {
        this.highestprobability = highestprobability;
    }

    public String getPredictedclass() {
        return predictedclass;
    }

    public void setPredictedclass(String predictedclass) {
        this.predictedclass = predictedclass;
    }


}

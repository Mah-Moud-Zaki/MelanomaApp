package com.example.final_project.nada;

public class CancerType {
    private int Img;

    // TextView 1
    private String mainTitle;

    // TextView 1
    private String subTitle;

    public CancerType(int img, String mainTitle, String subTitle) {
        Img = img;
        this.mainTitle = mainTitle;
        this.subTitle = subTitle;
    }

    public int getImg() {
        return Img;
    }

    public void setImg(int img) {
        Img = img;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}

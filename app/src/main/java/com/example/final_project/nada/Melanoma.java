package com.example.final_project.nada;

public class Melanoma {
    private int Img ;

    // TextView 1
    private String mainTitle ;


    // TextView 1
    private String describtion_Title;


    public Melanoma( String mainTitle, int img, String describtion_Title ) {

        this.mainTitle = mainTitle;

        Img = img;
        this.describtion_Title = describtion_Title;
    }

    public Melanoma(String mainTitle, String describtion_Title) {
        this.mainTitle = mainTitle;
        this.describtion_Title = describtion_Title;
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

    public String getDescribtion_Title() {
        return describtion_Title;
    }

    public void setDescribtion_Title(String describtion_Title) {
        this.describtion_Title = describtion_Title;
    }


}

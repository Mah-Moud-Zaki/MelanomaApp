package com.example.final_project.model;



public class ModelChats {

    String  name, profileImage, uid;

    public ModelChats() {
    }

    public ModelChats(String name, String profileImage, String uid) {
        this.name = name;
        this.profileImage = profileImage;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

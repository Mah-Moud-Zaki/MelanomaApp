package com.example.final_project.model;

public class ModelSearch {

    String address, name, phone, profileImage, uid;

    public ModelSearch() {
    }

    public ModelSearch(String address, String name, String phone, String profileImage, String uid) {
        this.address = address;
        this.name = name;
        this.phone = phone;
        this.profileImage = profileImage;
        this.uid = uid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

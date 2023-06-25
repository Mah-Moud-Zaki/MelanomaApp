package com.example.final_project.model;

public class ModelComment {

    String id, doctorUid, timestamp, comment, uid;

    public ModelComment() {

    }

    public ModelComment(String id, String doctorUid, String timestamp, String comment, String uid) {
        this.id = id;
        this.doctorUid = doctorUid;
        this.timestamp = timestamp;
        this.comment = comment;
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorUid() {
        return doctorUid;
    }

    public void setDoctorUid(String doctorUid) {
        this.doctorUid = doctorUid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

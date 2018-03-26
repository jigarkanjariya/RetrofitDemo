package com.jk.retrofitdemo.Sqlite_profile_example.model;

/**
 * Created by Abc on 3/6/2018.
 */

public class UserProfile {

    public String id;
    public String name;
    private String password;
    private String phoneno;
    private String emailid;
    private String gender;
    private String picture;

    public UserProfile(String name, String password, String emailid, String phoneno, String gender, String picture) {
        this.name=name;
        this.password=password;
        this.emailid=emailid;
        this.phoneno=phoneno;
        this.gender=gender;
        this.picture=picture;

    }

    public UserProfile() {

    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

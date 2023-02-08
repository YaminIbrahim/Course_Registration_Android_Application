package com.example.courserms;

public class User {

    public String name, staffid, email;

    public User(){}

    public User(String name, String staffid, String email) {
        this.name = name;
        this.staffid = staffid;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

package com.upiita.msvc_cv.models;

import jakarta.persistence.*;

public class User {
    private Long user_id;
    private String username;
    private String institucionalEmail;
    private String name;
    private String lastName;
    private String studentLicense;
    private String phonenumber;
    private String pasword;
    public User() {
    }
    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getInstitucionalEmail() {
        return institucionalEmail;
    }

    public void setInstitucionalEmail(String institucionalEmail) {
        this.institucionalEmail = institucionalEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStudentLicense() {
        return studentLicense;
    }

    public void setStudentLicense(String studentLicense) {
        this.studentLicense = studentLicense;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPasword() {
        return pasword;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }
}

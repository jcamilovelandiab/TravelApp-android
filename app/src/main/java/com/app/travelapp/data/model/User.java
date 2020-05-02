package com.app.travelapp.data.model;

public class User {

    private String userId;
    private String username;
    private String email;
    private String password;
    private String full_name;

    public User(String username, String email, String password, String full_name) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}

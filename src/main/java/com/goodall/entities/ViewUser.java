package com.goodall.entities;

public class ViewUser {
    private String username;
    private String email;
    private String password;

    public ViewUser(String username, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public ViewUser() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}

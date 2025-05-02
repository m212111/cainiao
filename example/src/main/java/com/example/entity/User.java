package com.example.entity;

public class User {
    private String username;
    private String password;
    private String role;
    private String identityCode;
    public User(String username, String password, String role , String identityCode) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.identityCode = identityCode;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
    public void setIdentityCode(String identityCode) { this.identityCode = identityCode; }
    public String getIdentityCode() { return identityCode; }
}

package com.walletwise.domain.entities.models;

import java.util.UUID;

public class Profile {
    private UUID userId;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String image;
    private String theme;

    public Profile(UUID userId, String firstname, String lastname, String username, String email, String image, String theme) {
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.image = image;
        this.theme = theme;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}


package com.example.timetableio.payload.response;

import java.util.List;

public class MessageResponse {
    private String message, username, email;
    private long id;
    private List roles;

    public MessageResponse(String message) {
        this.message = message;
    }

    public MessageResponse(String username, String email, long id, List roles) {
        this.username = username;
        this.email = email;
        this.id = id;
        this.roles = roles;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List getRoles() {
        return roles;
    }

    public void setRoles(List roles) {
        this.roles = roles;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


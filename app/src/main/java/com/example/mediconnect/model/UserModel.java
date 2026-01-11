package com.example.mediconnect.model;

import java.util.List;

public class UserModel {
    public int id;
    public String name;
    public String email;
    public String role;

    public boolean online;

    public boolean isOnline() {
        return online;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
}


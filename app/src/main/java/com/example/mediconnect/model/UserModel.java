package com.example.mediconnect.model;

import java.util.List;

public class UserModel {
    public int id;
    public String name;
    public String email;
    public String role;

    public boolean online;

    // Getter untuk status online
    public boolean isOnline() {
        return online;
    }
}


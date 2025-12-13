package com.example.mediconnect.model;

import com.google.gson.annotations.SerializedName;

public class UpdateUserResponse {

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public User data;

    public static class User {
        @SerializedName("id")
        public int id;

        @SerializedName("name")
        public String name;

        @SerializedName("email")
        public String email;

        @SerializedName("role")
        public String role;
    }
}

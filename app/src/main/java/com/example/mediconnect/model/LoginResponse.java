package com.example.mediconnect.model;

public class LoginResponse {
    public String message;
    public Data data;

    public class Data {
        public String token;
        public User user;
    }

    public class User {
        public int id;
        public String name;
        public String email;
        public String role;
    }
}

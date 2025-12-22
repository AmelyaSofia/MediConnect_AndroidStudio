package com.example.mediconnect.model;

public class DokterModel {

    private int id;
    private String name;
    private String specialization;
    private String phone;
    private String schedule;
    private String description;
    private String photo;

    public DokterModel(
            int id,
            String name,
            String specialization,
            String phone,
            String schedule,
            String description,
            String photo
    ) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.phone = phone;
        this.schedule = schedule;
        this.description = description;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getPhone() {
        return phone;
    }

    public String getSchedule() {
        return schedule;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoto() {
        return photo;
    }
}

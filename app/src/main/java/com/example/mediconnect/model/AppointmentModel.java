package com.example.mediconnect.model;

import com.google.gson.annotations.SerializedName;

public class AppointmentModel {

    private int id;
    @SerializedName("appointment_date")
    private String appointmentDate;
    @SerializedName("appointment_time")
    private String appointmentTime;
    private String status;
    private String note;
    private DokterModel doctor;
    private UserModel user;

    public UserModel getUser() { return user; }


    public int getId() { return id; }
    public String getAppointmentDate() { return appointmentDate; }
    public String getAppointmentTime() { return appointmentTime; }
    public String getStatus() { return status; }
    public String getNote() { return note; }
    public DokterModel getDoctor() { return doctor; }
}

package com.example.mediconnect.model;

import java.util.List;

public class MyAppointmentResponse {

    private boolean success;
    private List<AppointmentModel> data;

    public boolean isSuccess() {
        return success;
    }

    public List<AppointmentModel> getData() {
        return data;
    }
}

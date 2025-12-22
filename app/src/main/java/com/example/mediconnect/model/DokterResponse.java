package com.example.mediconnect.model;

import java.util.List;

public class DokterResponse {

    private boolean success;
    private List<DokterModel> data;

    public boolean isSuccess() {
        return success;
    }

    public List<DokterModel> getData() {
        return data;
    }
}

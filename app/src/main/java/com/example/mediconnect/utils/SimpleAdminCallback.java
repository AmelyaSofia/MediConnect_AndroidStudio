package com.example.mediconnect.utils;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SimpleAdminCallback<T> implements Callback<T> {

    private final Runnable onSuccess;

    public SimpleAdminCallback(Runnable onSuccess) {
        this.onSuccess = onSuccess;
    }

    @Override
    public void onResponse(
            @NonNull Call<T> call,
            @NonNull Response<T> response
    ) {
        if (response.isSuccessful() && onSuccess != null) {
            onSuccess.run();
        }
    }

    @Override
    public void onFailure(
            @NonNull Call<T> call,
            @NonNull Throwable t
    ) {
        t.printStackTrace();
    }
}

package com.example.mediconnect.network;

import com.example.mediconnect.model.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("/api/login")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );
}

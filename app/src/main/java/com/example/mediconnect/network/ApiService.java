package com.example.mediconnect.network;

import com.example.mediconnect.model.LoginResponse;
import com.example.mediconnect.model.UpdateUserResponse;
import com.example.mediconnect.model.UserModel;
import com.example.mediconnect.model.UsersResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("/api/login")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("/api/register")
    Call<LoginResponse> register(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("/api/user/update")
    Call<UpdateUserResponse> updateUser(
            @Header("Authorization") String token,
            @Field("name") String name,
            @Field("email") String email
    );

    @DELETE("/api/user/delete")
    Call<Void> deleteUser(@Header("Authorization") String token);

    @GET("/api/users")
    Call<UsersResponse> getAllUsers(@Header("Authorization") String token);
}

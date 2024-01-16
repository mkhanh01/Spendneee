package com.example.spendnee.service;

import com.example.spendnee.model.BaseResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserService {

    @GET("login.php")
    Call<BaseResponse> login(@Query("username") String username, @Query("password") String password);

    @GET("signup.php")
    Call<BaseResponse> signup(@Query("username") String username, @Query("name") String name, @Query("password") String password,
                              @Query("email") String email, @Query("image") String image);

    @GET("update_password.php")
    Call<BaseResponse> updatePassword(@Query("username") String username, @Query("password") String password);

    @GET("check_email.php")
    Call<BaseResponse> checkEmail(@Query("email") String email);

    @GET("check_username.php")
    Call<BaseResponse> checkUsername(@Query("username") String username);

}

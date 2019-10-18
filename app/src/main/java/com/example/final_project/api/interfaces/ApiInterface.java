package com.example.final_project.api.interfaces;

import com.example.final_project.models.SignInDataModel;
import com.example.final_project.models.SignUpDataModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiInterface {

    @PUT("/api/User")
    Call<SignUpDataModel> registerUser(@Body SignUpDataModel signUpDatamodel);

    @POST("/api/Token/Auth")
    Call<SignInDataModel> login(@Body SignInDataModel signInDataModel);

    //get list of available controllers
//    @GET("/api/Controller")
//    Call<List<>>

    //add new controller
//    @PUT("/api/Controller")
//    Call<>

    //edit controller's settings
//    @POST("/api/Controller")
//    Call<>

    //get controller by his 'id'
//    @GET("api/Controller/{id}")
//    Call<>

}
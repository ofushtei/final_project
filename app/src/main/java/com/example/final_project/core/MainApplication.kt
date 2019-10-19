package com.example.final_project.core

import android.app.Application
import com.example.final_project.api.inteceptors.LoginInterceptor
import com.example.final_project.api.interfaces.ApiInterface
import com.orhanobut.hawk.Hawk
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainApplication :
    Application() {
    private lateinit var retrofit: Retrofit
    private lateinit var api: ApiInterface
    override fun onCreate() {
        super.onCreate()

        Hawk.init(this).build()
        val client = OkHttpClient.Builder().addInterceptor(LoginInterceptor()).build()

        retrofit =
            Retrofit.Builder()
                .client(client)
                .baseUrl(
                    "http://localhost:5000"
                )
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .build()
        api = retrofit.create(ApiInterface::class.java)
    }

    fun getApi(): ApiInterface =
        api

}
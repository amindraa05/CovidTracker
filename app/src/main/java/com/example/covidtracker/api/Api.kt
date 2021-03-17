package com.example.covidtracker.api

import com.example.covidtracker.model.IndonesiaResponse
import com.example.covidtracker.model.ProvinceResponse
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("indonesia")
    fun getIndonesia(): Call<ArrayList<IndonesiaResponse>>

    @GET("indonesia/provinsi")
    fun getProvince(): Call<ArrayList<ProvinceResponse>>
}



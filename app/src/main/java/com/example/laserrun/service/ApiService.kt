package com.example.laserrun.service

import com.example.laserrun.data.Category
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("laserrun.json")
    suspend fun getCategory() : Response<MutableList<Category>>
}
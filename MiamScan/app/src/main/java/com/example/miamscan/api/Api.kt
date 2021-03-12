package com.example.miamscan.api

import com.example.miamscan.model.FoodResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("api/v0/product/{id}")
    fun getFood(@Path("id") codeId: String): Call<FoodResponse>
}
package com.example.finalassignementapp

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET

interface FoodApi {

    @GET("/api/food/random_food")
    fun getRandomFood() : Call<JsonObject>

}
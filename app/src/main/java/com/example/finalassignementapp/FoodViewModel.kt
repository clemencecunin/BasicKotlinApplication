package com.example.finalassignementapp

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class FoodViewModel: ViewModel() {

    var FoodInfoDescription = mutableStateOf("")
    var FoodInfoDish = mutableStateOf("")
    var FoodInfoMeasurement = mutableStateOf("")
    var FoodInfoIngredient = mutableStateOf("")

    val api =
        Retrofit
            .Builder()
            .baseUrl("https://random-data-api.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create<FoodApi>()


    fun getRandomFood() {
        viewModelScope.launch {
            val resp = api.getRandomFood().awaitResponse()
            if (resp.isSuccessful) {
                val data: JsonObject? = resp.body()
                FoodInfoDescription.value = data?.get("description").toString()
                FoodInfoDish.value = data?.get("dish").toString()
                FoodInfoMeasurement.value = data?.get("measurement").toString()
                FoodInfoIngredient.value = data?.get("ingredient").toString()
            } else {
                Log.d("***", "Error while fetching a food API")
            }
        }

    }
}
package com.example.miamscan.data

import androidx.lifecycle.LiveData

class FoodRepo(private val foodDAO: FoodDAO) {

    val readAllData: LiveData<List<FoodData>> = foodDAO.readAllData()

    suspend fun addFood (foodData: FoodData){
        foodDAO.addFood(foodData)
    }
}
package com.example.miamscan.data

import androidx.lifecycle.LiveData
import com.example.miamscan.model.FoodData

class FoodRepo(private val foodDAO: FoodDAO) {

    val readAllData: LiveData<List<FoodData>> = foodDAO.readAllData()

    suspend fun addFood (foodData: FoodData){
        foodDAO.addFood(foodData)
    }

    suspend fun deleteFood(foodData: FoodData){
        foodDAO.deleteFood(foodData)
    }
}
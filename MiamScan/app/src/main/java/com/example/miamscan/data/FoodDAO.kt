package com.example.miamscan.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FoodDAO {

    @Insert
    suspend fun addFood(food: FoodData)

    @Query("SELECT * FROM food_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<FoodData>>
}
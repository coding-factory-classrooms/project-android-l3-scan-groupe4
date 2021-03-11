package com.example.miamscan.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_table")
data class FoodData (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name: String,
    val brand: String,
    val imageURL: String,
    val date: String
)
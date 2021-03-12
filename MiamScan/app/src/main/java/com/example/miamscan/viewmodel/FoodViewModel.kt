package com.example.miamscan.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.miamscan.data.FoodDataBase
import com.example.miamscan.data.FoodRepo
import com.example.miamscan.model.FoodData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoodViewModel(application: Application): AndroidViewModel(application) {

    val readAllData : LiveData<List<FoodData>>
    private val repository : FoodRepo

    init {
        val foodDAO = FoodDataBase.getDatabase(application).foodDAO()
        repository = FoodRepo(foodDAO)
        readAllData = repository.readAllData
    }

    fun addFood(foodData: FoodData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFood(foodData)
        }
    }

    fun deleteFood(foodData: FoodData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFood(foodData)
        }
    }

}
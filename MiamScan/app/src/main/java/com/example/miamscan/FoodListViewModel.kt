package com.example.miamscan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

public val listFood : MutableList<Product> = mutableListOf()

class FoodListViewModel : ViewModel () {

    private val foodsLivedData = MutableLiveData<MutableList<Product>>()

    fun getFoodsLiveData(): LiveData<MutableList<Product>> = foodsLivedData

    fun loadFood() {
        foodsLivedData.value = listFood
    }

}
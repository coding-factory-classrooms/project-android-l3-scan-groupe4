package com.example.miamscan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private val foods = listOf(
    Food("Couscous du soleil", brand = "Ma vie sans glutten", R.drawable.image_couscous),
    Food("Coca-Cola Vanille", brand = "Coca-Cola", R.drawable.image_cocacola),
    Food("Pain de mie", brand = "Harrys", R.drawable.image_pain_harrys),
    Food("Yop Chocolat", brand = "Yoplay", R.drawable.image_yop_chocolat),
    Food("Couscous du soleil", brand = "Ma vie sans glutten", R.drawable.image_couscous),
    Food("Coca-Cola Vanille", brand = "Coca-Cola", R.drawable.image_cocacola),
    Food("Pain de mie", brand = "Harrys", R.drawable.image_pain_harrys),
    Food("Yop Chocolat", brand = "Yoplay", R.drawable.image_yop_chocolat),
    Food("Couscous du soleil", brand = "Ma vie sans glutten", R.drawable.image_couscous),
    Food("Coca-Cola Vanille", brand = "Coca-Cola", R.drawable.image_cocacola),
    Food("Pain de mie", brand = "Harrys", R.drawable.image_pain_harrys),
    Food("Yop Chocolat", brand = "Yoplay", R.drawable.image_yop_chocolat),
    Food("Couscous du soleil", brand = "Ma vie sans glutten", R.drawable.image_couscous),
    Food("Coca-Cola Vanille", brand = "Coca-Cola", R.drawable.image_cocacola),
    Food("Pain de mie", brand = "Harrys", R.drawable.image_pain_harrys),
    Food("Yop Chocolat", brand = "Yoplay", R.drawable.image_yop_chocolat)
    )

class FoodListViewModel : ViewModel () {

    private val  foodsLivedData = MutableLiveData<List<Food>>()

    fun getFoodsLiveData(): LiveData<List<Food>> = foodsLivedData

    fun loadMovies() {
        foodsLivedData.value = foods
    }

}
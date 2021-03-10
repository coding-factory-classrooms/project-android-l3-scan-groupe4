package com.example.miamscan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miamscan.databinding.ActivityFoodListBinding

class FoodListActivity : AppCompatActivity() {

    private val model : FoodListViewModel by viewModels()
    private lateinit var binding: ActivityFoodListBinding
    private lateinit var adapter : FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model.getFoodsLiveData().observe(this, Observer { foods -> updateFoods(foods!!) })

        adapter = FoodAdapter(listOf())

        binding.RecyclerView.adapter = adapter
        binding.RecyclerView.layoutManager = LinearLayoutManager(this)

        model.loadMovies()
    }


    private fun updateFoods(foods: List<Food>) {
        adapter.updateDataSet(foods)
    }
}
package com.example.miamscan

import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.miamscan.data.FoodData
import com.example.miamscan.data.FoodViewModel
import com.example.miamscan.databinding.ActivityFoodDetailsBinding
import com.squareup.picasso.Picasso

class FoodDetailsActivity : AppCompatActivity() {
    private lateinit var mFoodViewModel: FoodViewModel
    lateinit var binding: ActivityFoodDetailsBinding
    lateinit var food: FoodData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mFoodViewModel = ViewModelProvider(this).get(FoodViewModel::class.java)

        binding.textViewNameDetail.text = getIntent().getStringExtra("foodName")
        binding.textViewBrandDetail.text = getIntent().getStringExtra("foodBrand")
        Picasso.get().load(getIntent().getStringExtra("foodImage")).into(binding.imageViewDetails)
        binding.textViewCategoriesDetail.text = getIntent().getStringExtra("foodCategories")
        binding.textViewNutritionDetail.text = getIntent().getStringExtra("foodNutrition")
        food = getIntent().extras!!.get("extra_object") as FoodData
        binding.deleteFloatingButton.setOnClickListener{
            deleteUser(this)
        }
    }

    private fun deleteUser(context: Context) {
        mFoodViewModel.deleteFood(food)
    }
}
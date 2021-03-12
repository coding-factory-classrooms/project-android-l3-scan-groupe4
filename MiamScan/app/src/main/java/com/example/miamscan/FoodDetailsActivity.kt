package com.example.miamscan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.miamscan.databinding.ActivityFoodDetailsBinding
import com.squareup.picasso.Picasso

class FoodDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityFoodDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textViewNameDetail.text = getIntent().getStringExtra("foodName")
        binding.textViewBrandDetail.text = getIntent().getStringExtra("foodBrand")
        Picasso.get().load(getIntent().getStringExtra("foodImage")).into(binding.imageViewDetails)
        binding.textViewCategoriesDetail.text = getIntent().getStringExtra("foodCategories")
        binding.textViewNutritionDetail.text = getIntent().getStringExtra("foodNutrition")
    }
}
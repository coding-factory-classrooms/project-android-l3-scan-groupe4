package com.example.miamscan.foodDetails

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.miamscan.model.FoodData
import com.example.miamscan.databinding.ActivityFoodDetailsBinding
import com.example.miamscan.foodList.FoodListActivity
import com.example.miamscan.viewmodel.FoodViewModel
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
            deleteUser()
        }
    }

    private fun deleteUser() {
        mFoodViewModel.deleteFood(food)
        Toast.makeText(this, "${food.name} à été supprimé", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, FoodListActivity::class.java)
        startActivity(intent)
        finish()
    }
}
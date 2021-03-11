package com.example.miamscan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.miamscan.databinding.ItemFoodBinding
import com.squareup.picasso.Picasso

class FoodAdapter (private var foods : MutableList<Product>)
    : RecyclerView.Adapter<FoodAdapter.ViewHolder>(){
    class ViewHolder (val binding: ItemFoodBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFoodBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foods[position]
        with(holder.binding){
            nameTextView.text = food.productName
            brandTextView.text = food.brands
            Picasso.get().load(food.image_url).into(imageView)
        }
    }

    override fun getItemCount(): Int {
        return foods.size
    }

    fun updateDataSet(foods: MutableList<Product>) {
        this.foods = foods
        notifyDataSetChanged()
    }
}
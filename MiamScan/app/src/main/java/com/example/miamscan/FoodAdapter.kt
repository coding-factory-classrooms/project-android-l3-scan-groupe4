package com.example.miamscan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.miamscan.data.FoodData
import com.example.miamscan.databinding.ItemFoodBinding
import com.squareup.picasso.Picasso


class FoodAdapter (private var foods : List<FoodData>)
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
            nameTextView.text = food.name
            brandTextView.text = food.brand
            dateTextView.text = food.date
            Picasso.get().load(food.imageURL).into(imageView)
        }
    }

    override fun getItemCount(): Int {
        return foods.size
    }

    fun updateDataSet(foods: List<FoodData  >) {
        this.foods = foods
        notifyDataSetChanged()
    }
}
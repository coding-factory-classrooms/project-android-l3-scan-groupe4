package com.example.miamscan.foodList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.miamscan.model.FoodData
import com.example.miamscan.databinding.ItemFoodBinding
import com.squareup.picasso.Picasso


class FoodAdapter (private var foods : List<FoodData>, var clickedListener: onFoodItemClickedListener)
    : RecyclerView.Adapter<FoodAdapter.ViewHolder>(){
    class ViewHolder (val binding: ItemFoodBinding): RecyclerView.ViewHolder(binding.root) {
        fun initialize(foods: FoodData, action: onFoodItemClickedListener){
            binding.nameTextView.text = foods.name
            binding.brandTextView.text = foods.brand
            Picasso.get().load(foods.imageURL).into(binding.imageView)

            itemView.setOnClickListener{
                action.onItemClick(foods, adapterPosition)
            }

        }
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
            holder.initialize(foods.get(position), clickedListener)

        }
    }

    override fun getItemCount(): Int {
        return foods.size
    }

    fun updateDataSet(foods: List<FoodData>) {
        this.foods = foods
        notifyDataSetChanged()
    }
}

interface onFoodItemClickedListener{
    fun onItemClick(foods: FoodData, position: Int)
}
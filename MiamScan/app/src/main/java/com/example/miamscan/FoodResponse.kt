package com.example.miamscan

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoodResponse (
    val product: Product,
    val code : String,
    val status : String,
)

@JsonClass(generateAdapter = true)
data class Product (
    @Json(name = "product_name") val productName : String,
    val brands: String,
    val image_url: String
)
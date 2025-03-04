package com.example.shopfood.data


data class Order(
    val user_id: Int,
    val items: String,
    val total_price: Double
)
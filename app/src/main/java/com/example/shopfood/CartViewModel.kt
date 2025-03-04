package com.example.shopfood

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopfood.Models.Product

class CartViewModel : ViewModel() {
    private val _cartItems = MutableLiveData<MutableList<Product>>(mutableListOf())
    val cartItems: LiveData<MutableList<Product>> get() = _cartItems

    fun addToCart(product: Product) {
        val currentList = _cartItems.value ?: mutableListOf()
        currentList.add(product)
        _cartItems.value = currentList
    }
}
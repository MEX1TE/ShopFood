package com.example.shopfood

import CartViewModel
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shopfood.databinding.ActivityDitailsBinding
import com.example.shopfood.Models.Product

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDitailsBinding
    private lateinit var viewModel: CartViewModel // Объявляем ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDitailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Инициализируем ViewModel
        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)

        val product = intent.getSerializableExtra("product") as Product
        binding.menuDFoodName.text = product.name
        binding.menuDFoodImage.setImageResource(product.image)
        binding.menuDFoodIndegrients.text = "• ${product.name} ingredients"

        binding.addToCartButton.setOnClickListener {
            viewModel.addToCart(product)
            Log.d("DetailsActivity", "Added to cart: ${product.name}, Cart size: ${viewModel.cartItems.value?.size ?: 0}")
            binding.addToCartButton.text = "В корзине (${viewModel.cartItems.value?.size ?: 0})"
        }

        binding.backHome.setOnClickListener {
            finish()
        }
    }
}
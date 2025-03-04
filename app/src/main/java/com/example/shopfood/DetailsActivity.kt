package com.example.shopfood

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shopfood.databinding.ActivityDitailsBinding
import com.example.shopfood.Models.Product

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDitailsBinding
    private lateinit var viewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDitailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)

        val product = intent.getSerializableExtra("product") as Product
        binding.menuDFoodName.text = product.name
        binding.menuDFoodImage.setImageResource(product.image)
        binding.menuDFoodIndegrients.text = "ингридиенты Placeholder"
        binding.shortDescriptionOfFood.text = "дескрипшен Placeholder"

        binding.addToCartButton.setOnClickListener {
            viewModel.addToCart(product)
            Log.d("DetailsActivity", "Added to cart: ${product.name}, Cart size: ${viewModel.cartItems.value?.size ?: 0}")
            binding.addToCartButton.text = "В корзине (${viewModel.cartItems.value?.size ?: 0})"
            // Переход в корзину опционален, можно закомментировать
            // val intent = Intent(this@DetailsActivity, MainActivity::class.java)
            // startActivity(intent)
        }

        binding.backHome.setOnClickListener {
            val intent = Intent(this@DetailsActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
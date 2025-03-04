package com.example.shopfood.ui

import com.example.shopfood.Models.Product
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.shopfood.data.Order
import com.example.shopfood.data.RetrofitClient
import com.example.shopfood.databinding.FragmentCartBinding
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val cartItems = mutableListOf<Product>() // Ваш список товаров

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.proceedBtn.setOnClickListener {
            if (cartItems.isNotEmpty()) {
                saveOrderToServer()
            } else {
                Toast.makeText(context, "Корзина пуста", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveOrderToServer() {
        lifecycleScope.launch {
            try {
                val totalPrice = cartItems.sumOf { it.price }
                val itemsJson = Json.encodeToString(cartItems)
                val order = Order(
                    user_id = 1, // Замените на реальный userId из SharedPreferences
                    items = itemsJson,
                    total_price = totalPrice
                )
                val response = RetrofitClient.apiService.createOrder(order)
                if (response.isSuccessful) {
                    Toast.makeText(context, "Заказ отправлен", Toast.LENGTH_SHORT).show()
                    cartItems.clear()
                } else {
                    Toast.makeText(context, "Ошибка: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Ошибка сети: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
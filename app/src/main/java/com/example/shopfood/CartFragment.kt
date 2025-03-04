package com.example.shopfood.ui

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopfood.CartAdapter
import com.example.shopfood.CartViewModel
import com.example.shopfood.data.Order
import com.example.shopfood.data.RetrofitClient
import com.example.shopfood.databinding.FragmentCartBinding
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CartViewModel by activityViewModels() // Используем activityViewModels для общего ViewModel
    private lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        // Наблюдаем за изменениями cartItems
        viewModel.cartItems.observe(viewLifecycleOwner) { items ->
            Log.d("CartFragment", "Cart size updated: ${items.size}")
            adapter.notifyDataSetChanged()
            updateTotalPrice()
        }

        binding.proceedBtn.setOnClickListener {
            if (viewModel.cartItems.value?.isNotEmpty() == true) {
                saveOrderToServer()
            } else {
                android.widget.Toast.makeText(requireContext(), "Корзина пуста", android.widget.Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = CartAdapter(viewModel.cartItems.value ?: mutableListOf(),
            onQuantityChanged = { updateTotalPrice() },
            onItemRemoved = { position ->
                viewModel.cartItems.value?.removeAt(position)
                adapter.notifyItemRemoved(position)
                updateTotalPrice()
            })
        binding.cartRv.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRv.adapter = adapter
        updateTotalPrice()
    }

    private fun updateTotalPrice() {
        binding.proceedBtn.text = "Заказать (${viewModel.cartItems.value?.size ?: 0})"
    }

    private fun saveOrderToServer() {
        lifecycleScope.launch {
            try {
                val items = viewModel.cartItems.value ?: mutableListOf()
                val totalPrice = items.sumOf { it.price * (adapter.quantities[it.id] ?: 1) }
                val itemsJson = Json.encodeToString(items.map {
                    mapOf("id" to it.id, "name" to it.name, "price" to it.price, "quantity" to (adapter.quantities[it.id] ?: 1))
                })
                val sharedPref = requireActivity().getSharedPreferences("user_prefs", MODE_PRIVATE)
                val userId = sharedPref.getInt("user_id", 1)
                val order = Order(user_id = userId, items = itemsJson, total_price = totalPrice)
                val response = RetrofitClient.apiService.createOrder(order)
                if (response.isSuccessful) {
                    android.widget.Toast.makeText(requireContext(), "Заказ отправлен", android.widget.Toast.LENGTH_SHORT).show()
                    viewModel.cartItems.value?.clear()
                    adapter.notifyDataSetChanged()
                    binding.proceedBtn.text = "Заказать (0)"
                } else {
                    android.widget.Toast.makeText(requireContext(), "Ошибка: ${response.message()}", android.widget.Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("CartFragment", "Network Error: ${e.message}")
                android.widget.Toast.makeText(requireContext(), "Ошибка сети: ${e.message}", android.widget.Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
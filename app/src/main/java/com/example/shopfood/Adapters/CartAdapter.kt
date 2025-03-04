package com.example.shopfood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shopfood.Models.Product

class CartAdapter(
    private val cartItems: MutableList<Product>,
    private val onQuantityChanged: () -> Unit,
    private val onItemRemoved: (Int) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodImage: ImageView = itemView.findViewById(R.id.home_food_image)
        val foodName: TextView = itemView.findViewById(R.id.home_food_name)
        val foodPrice: TextView = itemView.findViewById(R.id.home_food_price)
        val foodCount: TextView = itemView.findViewById(R.id.food_count)
        val plusButton: AppCompatButton = itemView.findViewById(R.id.plus_btn)
        val minusButton: AppCompatButton = itemView.findViewById(R.id.minus_btn)
        val deleteButton: AppCompatButton = itemView.findViewById(R.id.delete_btn)
    }

    val quantities = mutableMapOf<Int, Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_add_item, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = cartItems[position]
        holder.foodImage.setImageResource(product.image)
        holder.foodName.text = product.name
        holder.foodPrice.text = "${product.price} $"

        val currentQuantity = quantities.getOrDefault(product.id, 1)
        quantities[product.id] = currentQuantity
        holder.foodCount.text = currentQuantity.toString()

        holder.plusButton.setOnClickListener {
            quantities[product.id] = currentQuantity + 1
            holder.foodCount.text = quantities[product.id].toString()
            onQuantityChanged()
        }

        holder.minusButton.setOnClickListener {
            if (currentQuantity > 1) {
                quantities[product.id] = currentQuantity - 1
                holder.foodCount.text = quantities[product.id].toString()
                onQuantityChanged()
            }
        }

        holder.deleteButton.setOnClickListener {
            onItemRemoved(position)
        }
    }

    override fun getItemCount(): Int = cartItems.size
}
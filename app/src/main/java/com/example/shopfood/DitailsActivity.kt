package com.example.shopfood

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.shopfood.databinding.ActivityDitailsBinding
import com.example.shopfood.databinding.HomeFootItemBinding

class DitailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDitailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDitailsBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)

        val foodImage = intent.getIntExtra("foodImage", 0)
        val foodName = intent.getStringExtra("foodName")

        binding.menuDFoodImage.setImageResource(foodImage)
        binding.menuDFoodName.text = foodName

        binding.backHome.setOnClickListener {
            finish()
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
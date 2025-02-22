package com.example.shopfood

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.shopfood.databinding.ActivityLoginBinding
import com.example.shopfood.databinding.ActivitySignUpUserBinding

class SignUpUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySignUpUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goLoginUserPage.setOnClickListener {
            val intent = Intent(this@SignUpUserActivity, LoginActivity :: class.java)
            startActivity(intent)
        }




        binding.button3.setOnClickListener {
            val intent = Intent(this@SignUpUserActivity,MainActivity :: class.java)
            startActivity(intent)
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
}
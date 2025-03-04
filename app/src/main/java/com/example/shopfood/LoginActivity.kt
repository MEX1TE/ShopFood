package com.example.shopfood

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.shopfood.data.LoginRequest
import com.example.shopfood.data.RetrofitClient
import com.example.shopfood.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Переход на регистрацию
        binding.goSignUpUser.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpUserActivity::class.java)
            startActivity(intent)
        }

        // Кнопка входа
        binding.button3.setOnClickListener {
            val email = binding.signInEmail.text.toString() // Используем sign_in_email
            val password = binding.signInPass.text.toString() // Используем sign_in_pass

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val loginRequest = LoginRequest(email, password)
                loginUser(loginRequest)
            } else {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun loginUser(loginRequest: LoginRequest) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.login(loginRequest)
                if (response.isSuccessful) {
                    val userId = response.body()?.get("user_id") as? Double
                    // Сохраняем user_id в SharedPreferences
                    val sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putInt("user_id", userId?.toInt() ?: 1)
                        apply()
                    }
                    Toast.makeText(this@LoginActivity, "Вход успешен, ID: $userId", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Неверный email или пароль", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@LoginActivity, "Ошибка сети: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
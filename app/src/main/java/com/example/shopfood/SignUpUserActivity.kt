package com.example.shopfood

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.shopfood.data.RetrofitClient
import com.example.shopfood.data.User
import com.example.shopfood.databinding.ActivitySignUpUserBinding
import kotlinx.coroutines.launch

class SignUpUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Переход на страницу логина
        binding.goLoginUserPage.setOnClickListener {
            val intent = Intent(this@SignUpUserActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        // Кнопка регистрации
        binding.button3.setOnClickListener {
            val name = binding.signUpUserName.text.toString() // Поле имени
            val email = binding.signInEmail.text.toString() // Поле email
            val password = binding.signInPass.text.toString() // Поле пароля

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                val user = User(email, password, name)
                registerUser(user)
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

    private fun registerUser(user: User) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.register(user)
                if (response.isSuccessful) {
                    Toast.makeText(this@SignUpUserActivity, "Регистрация успешна", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@SignUpUserActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@SignUpUserActivity, "Ошибка: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@SignUpUserActivity, "Ошибка сети: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
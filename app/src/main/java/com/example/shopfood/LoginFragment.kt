package com.example.shopfood.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.shopfood.data.LoginRequest
import com.example.shopfood.data.RetrofitClient
import com.example.shopfood.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val loginRequest = LoginRequest(email, password)
                loginUser(loginRequest)
            } else {
                Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(loginRequest: LoginRequest) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.login(loginRequest)
                if (response.isSuccessful) {
                    val userId = response.body()?.get("user_id") as? Double
                    Toast.makeText(context, "Вход успешен, ID: $userId", Toast.LENGTH_SHORT).show()
                    // Сохраните userId (например, в SharedPreferences)
                    // findNavController().navigate(R.id.action_login_to_main)
                } else {
                    Toast.makeText(context, "Неверный email или пароль", Toast.LENGTH_SHORT).show()
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
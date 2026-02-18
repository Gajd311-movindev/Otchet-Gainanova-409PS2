package com.krepko.bonuswallet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.krepko.bonuswallet.data.DataManager
import com.krepko.bonuswallet.data.UserRole

class MainActivity : AppCompatActivity() {

    private lateinit var editLogin: EditText
    private lateinit var editPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvError: TextView
    private lateinit var dataManager: DataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация
        editLogin = findViewById(R.id.editLogin)
        editPassword = findViewById(R.id.editPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvError = findViewById(R.id.tvError)

        dataManager = DataManager(this)

        // Обработка нажатия кнопки входа
        btnLogin.setOnClickListener {
            val login = editLogin.text.toString()
            val password = editPassword.text.toString()

            if (login.isBlank() || password.isBlank()) {
                showError("Введите логин и пароль")
                return@setOnClickListener
            }

            val user = dataManager.findUser(login, password)

            if (user != null) {
                tvError.visibility = TextView.GONE
                Toast.makeText(this, "Успешный вход!", Toast.LENGTH_SHORT).show()

                // Переход в зависимости от роли
                when (user.role) {
                    UserRole.USER -> {
                        val intent = Intent(this, UserMainActivity::class.java)
                        intent.putExtra("user_id", user.id)
                        startActivity(intent)
                        finish()
                    }
                    UserRole.ADMIN -> {
                        val intent = Intent(this, AdminMainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            } else {
                showError("Неверный логин или пароль")
            }
        }
    }

    private fun showError(message: String) {
        tvError.text = message
        tvError.visibility = TextView.VISIBLE
    }
}
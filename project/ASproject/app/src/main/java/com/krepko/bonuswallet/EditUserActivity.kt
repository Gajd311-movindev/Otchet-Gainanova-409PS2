package com.krepko.bonuswallet

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.krepko.bonuswallet.data.DataManager
import com.krepko.bonuswallet.data.User
import com.krepko.bonuswallet.data.UserRole

class EditUserActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var editFullName: EditText
    private lateinit var editPhone: EditText
    private lateinit var editEmail: EditText
    private lateinit var editLogin: EditText
    private lateinit var editPassword: EditText
    private lateinit var editBonus: EditText
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    private lateinit var dataManager: DataManager
    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user)

        tvTitle = findViewById(R.id.tvTitle)
        editFullName = findViewById(R.id.editFullName)
        editPhone = findViewById(R.id.editPhone)
        editEmail = findViewById(R.id.editEmail)
        editLogin = findViewById(R.id.editLogin)
        editPassword = findViewById(R.id.editPassword)
        editBonus = findViewById(R.id.editBonus)
        btnSave = findViewById(R.id.btnSave)
        btnCancel = findViewById(R.id.btnCancel)

        dataManager = DataManager(this)

        userId = intent.getIntExtra("user_id", -1)

        if (userId != -1) {
            // Редактирование существующего пользователя
            tvTitle.text = "Редактирование пользователя"
            val user = dataManager.getUsers().find { it.id == userId }
            if (user != null) {
                editFullName.setText(user.fullName)
                editPhone.setText(user.phone)
                editEmail.setText(user.email)
                editLogin.setText(user.login)
                editPassword.setText(user.password)
                editBonus.setText(user.bonusBalance.toString())
            }
        } else {
            // Добавление нового пользователя
            tvTitle.text = "Добавление пользователя"
        }

        btnSave.setOnClickListener {
            if (validateFields()) {
                saveUser()
            }
        }

        btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun validateFields(): Boolean {
        return when {
            editFullName.text.isBlank() -> {
                Toast.makeText(this, "Введите ФИО", Toast.LENGTH_SHORT).show()
                false
            }
            editPhone.text.isBlank() -> {
                Toast.makeText(this, "Введите телефон", Toast.LENGTH_SHORT).show()
                false
            }
            editEmail.text.isBlank() -> {
                Toast.makeText(this, "Введите email", Toast.LENGTH_SHORT).show()
                false
            }
            editLogin.text.isBlank() -> {
                Toast.makeText(this, "Введите логин", Toast.LENGTH_SHORT).show()
                false
            }
            editPassword.text.isBlank() -> {
                Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show()
                false
            }
            editBonus.text.isBlank() -> {
                Toast.makeText(this, "Введите бонусы", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    private fun saveUser() {
        val fullName = editFullName.text.toString()
        val phone = editPhone.text.toString()
        val email = editEmail.text.toString()
        val login = editLogin.text.toString()
        val password = editPassword.text.toString()
        val bonus = editBonus.text.toString().toInt()

        if (userId != -1) {
            // Обновление существующего
            val existingUser = dataManager.getUsers().find { it.id == userId }
            if (existingUser != null) {
                val updatedUser = existingUser.copy(
                    fullName = fullName,
                    phone = phone,
                    email = email,
                    login = login,
                    password = password,
                    bonusBalance = bonus
                )
                if (dataManager.updateUser(updatedUser)) {
                    Toast.makeText(this, "Пользователь обновлен", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Ошибка обновления", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            // Добавление нового
            val users = dataManager.getUsers()
            val newId = (users.maxOfOrNull { it.id } ?: 0) + 1
            val newUser = User(
                id = newId,
                login = login,
                password = password,
                fullName = fullName,
                phone = phone,
                email = email,
                bonusBalance = bonus,
                role = UserRole.USER
            )
            if (dataManager.addUser(newUser)) {
                Toast.makeText(this, "Пользователь добавлен", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Логин уже существует", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
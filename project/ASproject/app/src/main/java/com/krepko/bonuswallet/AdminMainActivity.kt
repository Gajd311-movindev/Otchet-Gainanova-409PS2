package com.krepko.bonuswallet

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.krepko.bonuswallet.data.DataManager
import com.krepko.bonuswallet.data.User

class AdminMainActivity : AppCompatActivity() {

    private lateinit var listViewUsers: ListView
    private lateinit var btnAddUser: Button
    private lateinit var dataManager: DataManager
    private lateinit var users: List<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)

        listViewUsers = findViewById(R.id.listViewUsers)
        btnAddUser = findViewById(R.id.btnAddUser)

        dataManager = DataManager(this)

        loadUsers()

        // Обработка клика на пользователя - редактирование
        listViewUsers.setOnItemClickListener { _, _, position, _ ->
            val selectedUser = users[position]
            val intent = Intent(this, EditUserActivity::class.java)
            intent.putExtra("user_id", selectedUser.id)
            startActivity(intent)
        }

        // Добавление нового пользователя
        btnAddUser.setOnClickListener {
            val intent = Intent(this, EditUserActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Обновляем список при возврате на экран
        loadUsers()
    }

    private fun loadUsers() {
        users = dataManager.getUsers()
        val userStrings = users.map { "${it.fullName} | ${it.phone} | ${it.bonusBalance} б." }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, userStrings)
        listViewUsers.adapter = adapter
    }
}
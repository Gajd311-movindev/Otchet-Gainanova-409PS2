package com.krepko.bonuswallet.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class DataManager(private val context: Context) {

    private val fileName = "users.json"
    private val gson = Gson()

    fun getUsers(): MutableList<User> {
        val file = File(context.filesDir, fileName)
        return if (file.exists()) {
            val jsonString = file.readText()
            val type = object : TypeToken<MutableList<User>>() {}.type
            gson.fromJson(jsonString, type)
        } else {

            createDefaultUsers()
        }
    }

    fun saveUsers(users: List<User>) {
        val file = File(context.filesDir, fileName)
        val jsonString = gson.toJson(users)
        file.writeText(jsonString)
    }

    private fun createDefaultUsers(): MutableList<User> {
        val defaultUsers = mutableListOf(
            User(
                id = 1,
                login = "ivan",
                password = "123",
                fullName = "Иванов Иван",
                phone = "+79991234567",
                email = "ivan@mail.ru",
                bonusBalance = 245,
                role = UserRole.USER
            ),
            User(
                id = 2,
                login = "petr",
                password = "123",
                fullName = "Петров Петр",
                phone = "+79992345678",
                email = "petr@mail.ru",
                bonusBalance = 120,
                role = UserRole.USER
            ),
            User(
                id = 3,
                login = "admin",
                password = "admin",
                fullName = "Администратор",
                phone = "+79993456789",
                email = "admin@krepko.ru",
                bonusBalance = 0,
                role = UserRole.ADMIN
            )
        )
        saveUsers(defaultUsers)
        return defaultUsers
    }


    fun findUser(login: String, password: String): User? {
        return getUsers().find { it.login == login && it.password == password }
    }


    fun updateUser(updatedUser: User): Boolean {
        val users = getUsers().toMutableList()
        val index = users.indexOfFirst { it.id == updatedUser.id }
        return if (index != -1) {
            users[index] = updatedUser
            saveUsers(users)
            true
        } else {
            false
        }
    }


    fun addUser(newUser: User): Boolean {
        val users = getUsers().toMutableList()

        if (users.any { it.login == newUser.login }) {
            return false
        }
        users.add(newUser)
        saveUsers(users)
        return true
    }
}
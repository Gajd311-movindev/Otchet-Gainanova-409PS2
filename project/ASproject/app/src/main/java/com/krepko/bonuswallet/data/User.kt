package com.krepko.bonuswallet.data

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val login: String,
    val password: String,
    val fullName: String,
    val phone: String,
    val email: String,
    val bonusBalance: Int,
    val role: UserRole
)

enum class UserRole {
    USER, ADMIN
}
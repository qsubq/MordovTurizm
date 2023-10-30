package com.example.mordovturizm.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val email: String,
    val password: String,
    val phoneNumber: Int,
    val fullName: String,
)

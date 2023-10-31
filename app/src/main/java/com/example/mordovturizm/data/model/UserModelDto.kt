package com.example.mordovturizm.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserModelDto(
    val email: String,
    val password: String,
    val fullName: String,
)

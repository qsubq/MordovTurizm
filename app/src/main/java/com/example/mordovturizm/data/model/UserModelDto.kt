package com.example.mordovturizm.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserModelDto(
    val email: String,
    val password: String,
    @SerialName("full_name")
    val fullName: String,
)

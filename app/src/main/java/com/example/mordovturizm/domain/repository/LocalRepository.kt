package com.example.mordovturizm.domain.repository

interface LocalRepository {
    fun saveUserEmail(email: String)
    fun getUserEmail(): String
}

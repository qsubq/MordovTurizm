package com.example.mordovturizm.domain.repository

interface RemoteRepository {
    suspend fun signIn(email: String, password: String)
    suspend fun signUp(email: String, password: String, phoneNumber: Int, fullName: String)
}

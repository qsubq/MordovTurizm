package com.example.mordovturizm.data.local_data_source.repository

import android.content.Context
import com.example.mordovturizm.domain.repository.LocalRepository

class LocalRepositoryImpl(context: Context) : LocalRepository {
    private val sharedPref = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    override fun saveUserEmail(email: String) {
        sharedPref.edit().putString("Email", email).apply()
    }

    override fun getUserEmail(): String {
        return sharedPref.getString("Email", "") ?: ""
    }

    override fun logOutUser() {
        sharedPref.edit().putString("Email", "").apply()
    }
}

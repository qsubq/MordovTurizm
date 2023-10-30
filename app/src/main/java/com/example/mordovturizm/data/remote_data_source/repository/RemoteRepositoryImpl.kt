package com.example.mordovturizm.data.remote_data_source.repository

import android.util.Log
import com.example.mordovturizm.data.model.UserModel
import com.example.mordovturizm.domain.repository.RemoteRepository
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.realtime.Realtime

class RemoteRepositoryImpl : RemoteRepository {
    private val client = createSupabaseClient(
        supabaseUrl = "https://kepofsjhmtkutvieibrs.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImtlcG9mc2pobXRrdXR2aWVpYnJzIiwicm9sZSI6ImFub24iLCJpYXQiOjE2ODg0NjQwMzQsImV4cCI6MjAwNDA0MDAzNH0.3Q1--FQgCgDb3naCRbNO1ZbkueAAP571VGJR36M5AkQ",
    ) {
        install(GoTrue)
        install(Postgrest)
        install(Realtime)
    }

    override suspend fun signIn(email: String, password: String) {
        client.gotrue.loginWith(Email) {
            this.email = email
            this.password = password
        }
    }

    override suspend fun signUp(
        email: String,
        password: String,
        phoneNumber: Int,
        fullName: String,
    ) {
        val user = client.gotrue.signUpWith(Email) {
            this.email = email
            this.password = password
        }

        Log.e("RemoteRepository", user.toString())
        createNewUser(email, password, phoneNumber, fullName)
    }

    private suspend fun createNewUser(
        email: String,
        password: String,
        phoneNumber: Int,
        fullName: String,
    ) {
        val user = UserModel(email, password, phoneNumber, fullName)
        client.postgrest["User"].insert(
            user,
        )
    }
}

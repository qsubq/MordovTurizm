package com.example.mordovturizm.data.remote_data_source.repository

import android.util.Log
import com.example.mordovturizm.data.model.UserModelDto
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
        supabaseUrl = "https://ciruthjxlrnikgduqarl.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImNpcnV0aGp4bHJuaWtnZHVxYXJsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MDExOTQ4OTAsImV4cCI6MjAxNjc3MDg5MH0.4LqN1c3yWUypk1Lusf86gwaB_f3FoqMP_d38epc5b08",
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
        fullName: String,
    ) {
        val user = client.gotrue.signUpWith(Email) {
            this.email = email
            this.password = password
        }

        Log.e("RemoteRepository", user.toString())
        createNewUser(email, password, fullName)
    }

    override suspend fun logOutUser() {
        client.gotrue.logout()
    }

    private suspend fun createNewUser(
        email: String,
        password: String,
        fullName: String,
    ) {
        val user = UserModelDto(email, password, fullName)
        client.postgrest["User"].insert(
            user,
        )
    }
}

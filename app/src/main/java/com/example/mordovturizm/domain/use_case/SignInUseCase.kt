package com.example.mordovturizm.domain.use_case

import com.example.mordovturizm.data.remote_data_source.repository.RemoteRepositoryImpl
import com.example.mordovturizm.domain.repository.RemoteRepository

class SignInUseCase {
    private val repository: RemoteRepository = RemoteRepositoryImpl()

    suspend fun execute(email: String, password: String) {
        repository.signIn(email, password)
    }
}

package com.example.mordovturizm.domain.use_case

import android.content.Context
import com.example.mordovturizm.data.local_data_source.repository.LocalRepositoryImpl
import com.example.mordovturizm.data.remote_data_source.repository.RemoteRepositoryImpl
import com.example.mordovturizm.domain.repository.LocalRepository
import com.example.mordovturizm.domain.repository.RemoteRepository

class LogoutUserUseCase(context: Context) {
    private val remoteRepository: RemoteRepository = RemoteRepositoryImpl()
    private val localRepository: LocalRepository = LocalRepositoryImpl(context)
    suspend fun execute() {
        remoteRepository.logOutUser()
        localRepository.logOutUser()
    }
}

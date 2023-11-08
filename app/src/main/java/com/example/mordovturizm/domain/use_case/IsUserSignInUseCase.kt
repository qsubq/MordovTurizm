package com.example.mordovturizm.domain.use_case

import android.content.Context
import com.example.mordovturizm.data.local_data_source.repository.LocalRepositoryImpl
import com.example.mordovturizm.domain.repository.LocalRepository

class IsUserSignInUseCase(context: Context) {
    private val localRepository: LocalRepository = LocalRepositoryImpl(context)
    fun execute(): String {
        return localRepository.getUserEmail()
    }
}

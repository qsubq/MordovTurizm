package com.example.mordovturizm.presentation.screen.map

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mordovturizm.domain.use_case.LogoutUserUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class MapViewModel(private val context: Application) : AndroidViewModel(context) {
    private val logoutUserUseCase = LogoutUserUseCase(context)

    var logOutLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var errorLiveData: MutableLiveData<String> = MutableLiveData()
    var networkLiveData: MutableLiveData<Boolean> = MutableLiveData()

    private val handler = CoroutineExceptionHandler { _, throwable ->
        Log.e("SignInViewModel", "${throwable.message}")
        errorLiveData.value = throwable.message
    }

    fun logOut() {
        viewModelScope.launch(handler) {
            logoutUserUseCase.execute()
            logOutLiveData.value = true
        }
    }

    private fun isOnline(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        return networkCapabilities != null
    }
}

package com.example.mordovturizm.presentation.screen.sign_up

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mordovturizm.domain.use_case.SignUpUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class SignUpViewModel(private val context: Application) : AndroidViewModel(context) {
    private val signUpUseCase = SignUpUseCase()

    var signUpLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var errorLiveData: MutableLiveData<String> = MutableLiveData()
    var networkLiveData: MutableLiveData<Boolean> = MutableLiveData()

    private val handler = CoroutineExceptionHandler { _, throwable ->
        Log.e("SignUpViewModel", "${throwable.message}")
        errorLiveData.value = throwable.message
    }

    fun signUp(email: String, password: String, fullName: String) {
        if (isOnline()) {
            viewModelScope.launch(handler) {
                signUpUseCase.execute(email, password, fullName)
                signUpLiveData.value = true
            }
        } else {
            networkLiveData.value = false
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

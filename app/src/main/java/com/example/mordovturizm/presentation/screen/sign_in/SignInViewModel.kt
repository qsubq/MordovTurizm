package com.example.mordovturizm.presentation.screen.sign_in

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mordovturizm.domain.use_case.IsUserSignInUseCase
import com.example.mordovturizm.domain.use_case.SignInUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class SignInViewModel(private val context: Application) : AndroidViewModel(context) {
    private val signInUseCase = SignInUseCase(context)
    private val isSignedInUseCase = IsUserSignInUseCase(context)

    var signInLiveData: MutableLiveData<String> = MutableLiveData()
    var errorLiveData: MutableLiveData<String> = MutableLiveData()
    var networkLiveData: MutableLiveData<Boolean> = MutableLiveData()

    private val handler = CoroutineExceptionHandler { _, throwable ->
        Log.e("SignInViewModel", "${throwable.message}")
        errorLiveData.value = throwable.message
    }

    fun signIn(email: String, password: String) {
        if (isOnline()) {
            viewModelScope.launch(handler) {
                signInUseCase.execute(email, password)
                signInLiveData.value = email
            }
        } else {
            networkLiveData.value = false
        }
    }

    fun isSignedIn() {
        isSignedInUseCase.execute().let {
            if (it.isNotEmpty()) {
                signInLiveData.value = it
            }
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

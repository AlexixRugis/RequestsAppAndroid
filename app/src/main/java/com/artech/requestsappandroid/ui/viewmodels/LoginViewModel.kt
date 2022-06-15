package com.artech.requestsappandroid.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.artech.requestsappandroid.data.remote.api.ApiRepository
import com.artech.requestsappandroid.data.remote.models.AuthenticationData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: ApiRepository) : ViewModel() {
    suspend fun login(email: String, password: String): Boolean {
        val response = repository.login(AuthenticationData(email, password))

        if (response.isSuccessful) {
            return true
        }

        return false;
    }
}
package com.artech.requestsappandroid

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artech.requestsappandroid.data.remote.api.ApiRepository
import com.artech.requestsappandroid.data.remote.models.AuthenticationStatus
import com.artech.requestsappandroid.data.remote.models.RepairRequests
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: ApiRepository) : ViewModel() {
    private val requestsModel = MutableLiveData<RepairRequests>()
    val requests: LiveData<RepairRequests>
        get() = requestsModel

    suspend fun getAuthenticationStatus(): AuthenticationStatus? {
        val isAuthenticated = repository.isAuthenticated()
        if (isAuthenticated.isSuccessful) {
            return isAuthenticated.body()
        } else {
            Log.e("TAG", "Failed to load authentication status ${isAuthenticated.errorBody()}")
            return null
        }
    }

    fun getRepairRequests() {
        viewModelScope.launch {
            repository.getAllAvailableRequests().let {
                if (it.isSuccessful) {
                    requestsModel.postValue(it.body())
                } else {
                    Log.e("TAG", "Failed to load requests ${it.errorBody()}")
                }
            }
        }
    }
}
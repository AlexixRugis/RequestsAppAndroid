package com.artech.requestsappandroid.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artech.requestsappandroid.data.remote.api.ApiRepository
import com.artech.requestsappandroid.data.remote.models.Employee
import com.artech.requestsappandroid.data.remote.models.RepairRequest
import com.artech.requestsappandroid.data.remote.models.RepairRequests
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestDetailViewModel @Inject constructor(private val repository: ApiRepository) : ViewModel()  {
    var mutableRepairRequest = mutableStateOf<RepairRequest?>(null)
    var isRefreshing by mutableStateOf(false)
    var id: Int = 1

    fun refresh() {
        isRefreshing = true
        viewModelScope.launch {
            mutableRepairRequest.value = getRepairRequest()
            isRefreshing = false
        }
    }

    suspend fun logout(): Boolean {
        if (repository.logout().isSuccessful) {
            return true
        }
        return false
    }

    suspend fun getRepairRequest(): RepairRequest? {
        val response = repository.getRequest(id)
        if (response.isSuccessful) return response.body()!!
        return null
    }
}
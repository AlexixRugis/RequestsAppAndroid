package com.artech.requestsappandroid.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artech.requestsappandroid.data.remote.api.ApiRepository
import com.artech.requestsappandroid.data.remote.models.RepairRequest
import com.artech.requestsappandroid.data.remote.models.RepairRequests
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(private val repository: ApiRepository) : ViewModel() {
    private var list by mutableStateOf(emptyList<RepairRequest>())
    var mutableList = mutableStateListOf<RepairRequest>()
    var isRefreshing by mutableStateOf(false)

    init {
        refresh()
    }

    fun refresh() {
        isRefreshing = true
        viewModelScope.launch {
            list = getRepairRequests()
            mutableList.clear()
            mutableList.addAll(list)
            isRefreshing = false
        }
    }


    private suspend fun getRepairRequests(): RepairRequests {
        val response = repository.getAllAvailableRequests()

        if (response.isSuccessful) {
            return response.body()!!
        }

        return RepairRequests()
    }
}
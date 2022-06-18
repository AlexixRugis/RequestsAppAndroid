package com.artech.requestsappandroid.presentation.ui.screens.requests

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.artech.requestsappandroid.common.DataLoadingState
import com.artech.requestsappandroid.common.EventHandler
import com.artech.requestsappandroid.common.Resource
import com.artech.requestsappandroid.data.remote.api.ApiRepository
import com.artech.requestsappandroid.data.remote.dto.RepairRequests
import com.artech.requestsappandroid.domain.use_case.get_request.GetRequestsUseCase
import com.artech.requestsappandroid.presentation.ui.screens.main.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestsViewModel @Inject constructor(
    private val getRequestsUseCase: GetRequestsUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(DataLoadingState<RepairRequests>())
    val state : StateFlow<DataLoadingState<RepairRequests>> = _state

    fun initialize() {
        getRequests()
    }

    private fun getRequests() {
        viewModelScope.launch {
            getRequestsUseCase.invoke().collect {
                when (it) {
                    is Resource.Loading -> _state.value = DataLoadingState(isLoading = true)
                    is Resource.Success -> _state.value = DataLoadingState(data = it.data)
                    is Resource.Error -> _state.value = DataLoadingState(error = it.message!!)
                }
            }
        }
    }
}
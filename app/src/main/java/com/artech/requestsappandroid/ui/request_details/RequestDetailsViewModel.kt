package com.artech.requestsappandroid.ui.request_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.artech.requestsappandroid.common.EventHandler
import com.artech.requestsappandroid.data.remote.api.ApiRepository
import com.artech.requestsappandroid.ui.request_details.models.RequestDetailsEvent
import com.artech.requestsappandroid.ui.request_details.models.RequestDetailsState
import com.artech.requestsappandroid.ui.screens.main.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestDetailsViewModel @Inject constructor(
    private val repository: ApiRepository
) : ViewModel(), EventHandler<RequestDetailsEvent> {
    lateinit var navController: NavController
    var id: Int = 1

    private val _state = MutableStateFlow(RequestDetailsState())
    val state: StateFlow<RequestDetailsState> = _state

    override fun obtainEvent(event: RequestDetailsEvent) {
        when (event) {
            RequestDetailsEvent.EnterScreen -> loadData()
            RequestDetailsEvent.AcceptRequest -> acceptRequest()
        }
    }

    private fun acceptRequest() {
        viewModelScope.launch {
            val response = repository.acceptRequest(id)
            if (response.isSuccessful) {
                navController.backQueue.removeLastOrNull()
                navController.navigate(Screens.Account.route)
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val response = repository.getRequest(id)
            if (response.isSuccessful) {
                _state.value = _state.value.copy(
                    info = response.body()!!,
                    isLoading = false
                )
            }
        }
    }
}
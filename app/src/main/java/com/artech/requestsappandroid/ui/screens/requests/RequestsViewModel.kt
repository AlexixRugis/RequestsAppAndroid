package com.artech.requestsappandroid.ui.screens.requests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.artech.requestsappandroid.common.EventHandler
import com.artech.requestsappandroid.data.remote.api.ApiRepository
import com.artech.requestsappandroid.ui.screens.main.Screens
import com.artech.requestsappandroid.ui.screens.requests.models.RequestsViewEvent
import com.artech.requestsappandroid.ui.screens.requests.models.RequestsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestsViewModel @Inject constructor(
    private val repository: ApiRepository
) : ViewModel(), EventHandler<RequestsViewEvent> {
    lateinit var navController: NavController

    private val _state = MutableStateFlow(RequestsViewState())
    val state : StateFlow<RequestsViewState> = _state

    override fun obtainEvent(event: RequestsViewEvent) {
        when (event) {
            RequestsViewEvent.EnterScreen -> loadData()
            RequestsViewEvent.ReloadRequests -> loadData()
            is RequestsViewEvent.ClickRequestItem -> showItemDetails(event.id)
        }
    }

    private fun showItemDetails(id: Int) {
        navController.navigate(Screens.RequestDetails.route + "/${id}")
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val response = repository.getAllAvailableRequests()
            if (response.isSuccessful) {
                _state.value = _state.value.copy(
                    requests = response.body()!!,
                    isLoading = false
                )
            }
        }
    }
}
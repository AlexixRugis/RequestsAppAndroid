package com.artech.requestsappandroid.presentation.ui.screens.request_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artech.requestsappandroid.common.ActionStatus
import com.artech.requestsappandroid.common.Resource
import com.artech.requestsappandroid.domain.use_case.accept_request.AcceptRequestUseCase
import com.artech.requestsappandroid.domain.use_case.get_request.GetRequestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestDetailsViewModel @Inject constructor(
    private val getRequestUseCase: GetRequestUseCase,
    private val acceptRequestUseCase: AcceptRequestUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var _id : Int = -1

    private val _state = MutableStateFlow(RequestDetailsState())
    val state: StateFlow<RequestDetailsState> = _state

    init {
        savedStateHandle.get<String>("requestId")?.let {
            _id = it.toInt()
            getRequestDetails()
        }
    }

    private fun getRequestDetails() {
        viewModelScope.launch {
            getRequestUseCase.invoke(_id).collect {
                when (it) {
                    is Resource.Loading -> _state.value = RequestDetailsState(isLoading = true)
                    is Resource.Success -> _state.value = RequestDetailsState(request = it.data)
                    is Resource.Error -> _state.value = RequestDetailsState(error = it.message!!)
                }
            }
        }
    }

    fun acceptRequest() {
        viewModelScope.launch {
            acceptRequestUseCase.invoke(_id).collect {
                if (it == ActionStatus.Success) {
                    _state.value = _state.value.copy(isAccepted = true)
                }
            }
        }
    }
}
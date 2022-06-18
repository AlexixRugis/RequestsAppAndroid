package com.artech.requestsappandroid.presentation.ui.screens.select_parts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artech.requestsappandroid.common.DataLoadingState
import com.artech.requestsappandroid.common.Resource
import com.artech.requestsappandroid.data.remote.dto.Part
import com.artech.requestsappandroid.domain.use_case.get_repair_parts.GetRepairPartsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectPartsViewModel @Inject constructor(
    private val getRepairPartsUseCase: GetRepairPartsUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(DataLoadingState<List<Part>>())
    val state : StateFlow<DataLoadingState<List<Part>>> = _state

    fun initialize() {
        loadParts()
    }

    private fun loadParts() {
        viewModelScope.launch {
            getRepairPartsUseCase.invoke().collect {
                when (it) {
                    is Resource.Loading -> _state.value = DataLoadingState(isLoading = true)
                    is Resource.Success -> _state.value = DataLoadingState(data = it.data)
                    is Resource.Error -> _state.value = DataLoadingState(error = it.message!!)
                }
            }
        }
    }

}
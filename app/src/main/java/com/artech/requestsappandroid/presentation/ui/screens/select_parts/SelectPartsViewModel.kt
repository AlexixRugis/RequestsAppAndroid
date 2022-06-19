package com.artech.requestsappandroid.presentation.ui.screens.select_parts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artech.requestsappandroid.common.DataLoadingState
import com.artech.requestsappandroid.common.Resource
import com.artech.requestsappandroid.data.remote.dto.RepairPart
import com.artech.requestsappandroid.domain.use_case.get_repair_parts.GetRepairPartsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectPartsViewModel @Inject constructor(
    private val getRepairPartsUseCase: GetRepairPartsUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(DataLoadingState<List<RepairPart>>())
    val state : StateFlow<DataLoadingState<List<RepairPart>>> = _state

    fun initialize() {
        loadParts(searchQuery = "")
    }

    fun updateParts(searchQuery: String) = loadParts(searchQuery)

    private fun loadParts(searchQuery: String) {
        viewModelScope.launch {
            getRepairPartsUseCase.invoke(searchQuery).collect {
                when (it) {
                    is Resource.Loading -> _state.value = DataLoadingState(isLoading = true)
                    is Resource.Success -> _state.value = DataLoadingState(data = it.data)
                    is Resource.Error -> _state.value = DataLoadingState(error = it.message!!)
                }
            }
        }
    }

}
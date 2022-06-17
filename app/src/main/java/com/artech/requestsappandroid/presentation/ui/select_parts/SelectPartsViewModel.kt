package com.artech.requestsappandroid.presentation.ui.select_parts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artech.requestsappandroid.common.Resource
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
    private val _state = MutableStateFlow(SelectRepairPartsViewState())
    val state : StateFlow<SelectRepairPartsViewState> = _state

    init {
        loadParts()
    }

    private fun loadParts() {
        viewModelScope.launch {
            getRepairPartsUseCase.invoke().collect {
                when (it) {
                    is Resource.Loading -> _state.value = SelectRepairPartsViewState(isLoading = true)
                    is Resource.Success -> _state.value = SelectRepairPartsViewState(parts = it.data)
                    is Resource.Error -> _state.value = SelectRepairPartsViewState(error = it.message!!)
                }
            }
        }
    }

}